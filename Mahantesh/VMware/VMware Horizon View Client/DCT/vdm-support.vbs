'
' (c) VMware 2013
'
Option Explicit

' Magic namespace constants
Const HKCU = &H80000001
Const HKLM = &H80000002
Const DESKTOP = &H10&
Const APPDATA = &H1A&
Const COMMON_APPDATA = &H23&
Const WINDOWS = &H24&
Const SYSTEM32 = &H25&
Const PROGRAM_FILES = &H26&
Const LOCALAPPDATA = &H1C&
Const ForReading = 1
Const ForWriting = 2
Const ForAppending = 8
Const TriStateUnicode = -1

' Log level constants
Const LOG_DISPLAY=0
Const LOG_TRACE = 1
Const LOG_DEBUG = 2
Const LOG_INFO  = 3
Const LOG_WARN  = 4
Const LOG_ERROR = 5
Const LOG_FATAL = 6
' What to save and what to display
Dim LOG_SHOW : LOG_SHOW = LOG_INFO
Dim LOG_FILE : LOG_FILE = LOG_INFO

' Other constants
Const MIN_PORT   = 1
Const MAX_PORT   = 65535
Const HTTP_PORT  = 80
Const HTTPS_PORT = 443
Const LDAP_PORT  = 389
Const JMS_PORT   = 4001

' use this only with cscript
If InStr(LCase(WScript.FullName), "cscript") = 0 Then
    WScript.Echo "You must run this script using the cscript engine. Please " _
        & "run support.bat included in this directory."
    WScript.Quit 1
End If

' Parse command line options
Dim StrUsrZipFile, BoolSilentMode, StrDCTDir
Call GetOpts()


' Objects we use throughout the script
Dim WShell : Set wshell = wscript.CreateObject("WScript.Shell")
Dim FSO : Set fso = CreateObject("Scripting.FileSystemObject")
Dim AppShell : Set AppShell = CreateObject("Shell.Application")
On Error Resume Next ' Some XPe devices may not support using WMI
Dim reg : Set reg = GetObject("winmgmts:{impersonationLevel=impersonate}!\\" _
                    & ".\root\default:StdRegProv")
On Error GoTo 0

Const strShortName = "vdm"               ' The string used when space is at a premium
Const companyName = "VMware, Inc."       ' What company name in the registry?
Const prodRegKey = "VMware VDM"          ' What string does the product appear under in registry
Const prodName = "VMware Horizon Client"   ' Product name for display purposes
Const prodFolderName = "vdm-sdct"        ' What string shall we prepend to folder, instance names and log files? Name of this tool
Const strEventCategory = "VMware View"   ' Windows Event Viewer category for filtering

' We'll use these to see what VDM products are installed
Const strClientMSICaption = "VMware Horizon Client"
Const strServerMSICaption = "VMware View Connection Server"    ' standard/replica/security all the same
Const strSSAltCaption     = "VMware View Security Server"      ' alternate display text in script
Const strTSAltCaption     = "VMware View Transfer Server"      ' alternate display text in script
Const strAgentMSICaption = "VMware View Agent"

Dim ScriptDir : ScriptDir = Left(WScript.ScriptFullName, InStrRev(WScript.ScriptFullName, "\"))
' tools used as part of the DCT
Dim zipOpts : zipOpts = "a -tzip -bd -mx3" ' add, zip, no % indicator, fast
Dim zipCmd : zipCmd = ScriptDir & "7za.exe"
Dim jmsTestJar : jmsTestJar = ScriptDir & "JMSPublishTest.jar"
Dim ws_diagCmd : ws_diagCmd = ScriptDir & "ws_diag.exe"
Dim ws_diag64Cmd : ws_diag64Cmd = ScriptDir & "x64\ws_diag.exe"
Dim dxDiagCmd : dxDiagCmd = "dxdiag /whql:off /t "

' Client install path
Dim ClientDir : ClientDir = fso.GetFolder(".").ParentFolder
Dim clientExe : clientExe = ClientDir & "\vmware-view.exe"

' Global vars needed everywhere
Dim LogFile : LogFile = Null
Dim LogFileFull : LogFileFull = Null
Dim LogOk : LogOk = True
Dim installpath
Dim boolIsClient, boolIsServer, boolIsAgent, boolIsCServer, boolIsTServer
Dim boolIsW2KS, boolIs32On64, boolIsW2K, boolIsXP, boolIsVista, boolIsW2K3, boolIsW2K8, boolIsWin7, boolIsWES7, boolIsWin8, boolIsVistaOrHigher
Dim Version

' everything gets saved under a folder on the desktop or specified on command line
Dim SAVEDIR, WORKDIR, INSTANCE
CreateWorkingFolder

' Get version
GetVersion

Log LOG_DEBUG, "Main", prodName & " Support script " & Version & ". Log started for " & instance & "."

' Warn if user doesn't have admin rights
If Not CheckAdmin() Then
    Log LOG_WARN, "Main", "WARNING: This script needs to run with administrator privileges to complete"
    Log LOG_WARN, "Main", "         successfully. Please run again as a local administrator."
End If

' Save support tool version to a file.
Call WriteFile(workdir & "\" & prodFolderName & "-ver.txt", Version)

If IsObject(reg) Then
    ' Determine if we are client, server, or agent. Error if more than one
    CheckRoles
    ' Determine the OS from WMI, making sure it's allowed, and setting some vars
    GetOS
    ' Now we know what product is here, we can found out where it is installed if it's written in the registry
    installpath = GetInstallPath()
    ' Now do the work
    GatherLogs
    GatherPCoIPLogs
    GatherEventLogs
    GatherProductConfig
    GatherSystemConfig
    ' Export USB registry value for client
    ExportUSBRegistry
    ' Additional features
    If boolIsAgent Then
       GatherVPData
       GatherSviData
    End If
    Diagnose
Else
    Log LOG_INFO, "Main", "Unable to use WMI. Collecting basic information."
    ' The following should still be able to run safely
    GatherLogs
    GatherSystemConfig
End If

Dim logMsg
Log LOG_DEBUG, "Main", "All done at " & Now
logMsg = "Support information for " & prodName & " was gathered under " & workdir & "."

Dim zipFile : zipFile = ZipIt(savedir, instance)
If Len(zipFile) <> 0 Then
   logMsg = logMsg & " Zipped as " & savedir & "\" & zipFile & "."
   wscript.stdout.writeline "Zipped as: " & savedir & "\" & zipFile & "."
   ' Log file handles should be closed now, which allows full deletion
   DeleteFolder savedir & "\" & instance
Else
    wscript.stderr.writeline "Error creating zip archive using " & zipCmd & "."
End If
' set access permissions to the zip file, catch error
Dim caclsCmd : caclsCmd = appshell.Namespace(SYSTEM32).Self.Path & "\cacls.exe"
On Error Resume Next
wshell.Exec(caclsCmd & " /G Administrators:R " & workdir & " " & zipFile)
On Error GoTo 0

' Log the completion if possible
On Error Resume Next
wshell.LogEvent 4, logMsg
On Error GoTo 0


'
' main() ends.
'

Sub GetOpts
   Dim args, i
   Set args = WScript.Arguments

   ' defaults
   StrUsrZipFile = Null
   BoolSilentMode = False
   StrDCTDir = Null

   For i = 0 To (WScript.Arguments.Count - 1)
      Select Case LCase(WScript.Arguments(i))
         Case "-f"
            StrUsrZipFile =  WScript.Arguments(i + 1)
            WScript.StdOut.WriteLine "Saving to zip file: " & SafeStr(StrUsrZipFile)
         Case "-s"
            BoolSilentMode = True
         Case "-d"
            StrDCTDir = WScript.Arguments(i + 1)
            WScript.StdOut.WriteLine "Redirecting DCT output to directory: " & SafeStr(StrDCTDir)
      End Select
   Next
End Sub

Sub CreateWorkingFolder
    Dim strTime, strDay, strMonth, strHour, strMins
    ' Set the location to save everything to, and make sure the folder exists
    ' If command line DCT directory is specified, it must exist, else use desktop.
    If Not IsNull(strDCTDir) Then
        savedir = strDCTDir
        If Not fso.FolderExists(savedir) Then
            WScript.StdErr.WriteLine "Requested DCT output directory " & Quote(savedir) & " doesn't exist."
            WScript.Quit 6
        End If
    Else
    savedir = appshell.Namespace(DESKTOP).Self.Path
    If Not fso.FolderExists(savedir) Then
            ' fall back to temporary directory
        WScript.StdErr.WriteLine "WARNING: Unable to save to desktop!"
        savedir = ProcessTempDir()
    End If
    End If

    savedir = savedir & "\" & prodFolderName & "\"
    If Not fso.FolderExists(savedir) Then fso.CreateFolder(savedir)
    ' allow it to be overridden if needed, not supported in older scripting hosts, so protect
    On Error Resume Next
    If WScript.Arguments.Named.Exists("s") Then
        savedir = WScript.Arguments.Named.Item("s")
    End If
    On Error GoTo 0

    savedir = fso.GetAbsolutePathName(savedir) ' absolute

    ' Get ready..
    If Not fso.FolderExists(savedir) Then
        wscript.stderr.WriteLine "Staging directory " & Quote(savedir) & " doesn't exist. " _
            & "Please collect logs manually."
        wscript.quit 2
    End If
    ' append datetime in format YYYYMMDD-HHMM
    strDay = Day(Date()) : If Len(strDay) = 1 Then strDay = "0" & strDay
    strMonth = Month(Date()) : If Len(strMonth) = 1 Then strMonth = "0" & strMonth
    strHour = Hour(Now()) : If Len(strHour) = 1 Then strHour = "0" & strHour
    strMins = Minute(Now()) : If Len(strMins) = 1 Then strMins = "0" & strMins
    strTime = Year(Date) & strMonth & strDay & "-" & strHour & strMins

    instance = prodFolderName & "-" & strTime
    workdir = savedir & "\" & instance
    If fso.FolderExists(workdir) Then fso.DeleteFolder(workdir)
    fso.CreateFolder(workdir)
End Sub

' According to role, perform any diagnostic tests we might be able to do
Sub Diagnose
    Dim strDiagnosticsDir, strAns, strDumpDir, strDump64Dir, boolDump, boolValid, strWinDumpFile

    strDiagnosticsDir = workdir  & "\diagnostics\"
    strDumpDir = strDiagnosticsDir & "dumps"
    strDump64Dir = strDumpDir & "\x64"
    If Not FSO.FolderExists(strDiagnosticsDir) Then FSO.CreateFolder(strDiagnosticsDir)

    Select Case True
        ' Case boolIsClient : DiagnoseClient strDiagnosticsDir
        Case boolIsServer : DiagnoseServer strDiagnosticsDir
        Case boolIsAgent : DiagnoseAgent strDiagnosticsDir
    End Select

    GatherVMwareDumps

    WScript.StdOut.WriteLine "** You can choose to generate diagnostic dumps of the " & prodName & " processes"
    WScript.StdOut.WriteLine "** running on this machine, please note these files can be very large."
    If PromptYesNo("Dump processes? ", False) Then
        Log LOG_INFO, "Diagnose", "Gathering diagnostic dumps..."
        If Not FSO.FolderExists(strDumpDir) Then FSO.CreateFolder(strDumpDir)
        RunCmd ws_diagCmd & " -dump -outdir " & Q(strDumpDir), strDumpDir & "\ws_diag-dump.txt"
        If boolIsClient And FSO.FileExists(ws_diag64Cmd) Then
            RunCmd ws_diag64Cmd & " -dumpproc vmware-usbarbitrator64 -outdir " & Q(strDump64Dir), strDump64Dir & "\ws_diag-dump.txt"
        End If
    End If

   strWinDumpFile = ReadRegString("HKLM\SYSTEM\CurrentControlSet\Control\CrashControl\DumpFile")
   If Not IsNull(strWinDumpFile) Then
      strWinDumpFile = WShell.ExpandEnvironmentStrings(strWinDumpFile)
      Log LOG_DEBUG, "Diagnose", "Windows dump file would be saved to " & strWinDumpFile
      If FSO.FileExists(strWinDumpFile) Then
         WScript.StdOut.WriteLine "** Windows dump file found. It is not recommended to include this large file"
         WScript.StdOut.WriteLine "** unless you believe " & prodName & " may be causing Windows to crash."
         If PromptYesNo("Include Windows dumpfile? ", False) Then
            Log LOG_INFO, "Diagnose", "Copying dump file..."
            If Not FSO.FolderExists(strDumpDir) Then FSO.CreateFolder(strDumpDir)
            CopyFile strWinDumpFile, strDumpDir
         End If
      End If
   End If
End Sub


Sub DiagnoseAgent(strDiagnosticsDir)
    ' Test JMS connectivity back to broker
    Log LOG_INFO, "DiagnoseAgent", "Performing agent diagnostics"

    Log LOG_INFO, "DiagnoseAgent", "  Collecting diagnostics from ws_diag"
    RunCmd ws_diagCmd, strDiagnosticsDir & "\ws_diag.txt"

    ' First retrieve the list of brokers
    Dim strBrokerList, strBroker
    strBrokerList = ReadRegString(strRegProduct("HKLM\") & "Agent\Configuration\Broker")
    If IsNull(strBrokerList) Or Len(strBrokerList) < 4 Then
        Log LOG_ERROR, "DiagnoseAgent", "Could not read configured brokers from registry. This VM may not have been successfully configured"
        Log LOG_DEBUG, "DiagnoseAgent", "Configured brokers in registry are '" & strBrokerList & "'. Must be at least 4 chars long"
        Exit Sub
    End If
    ' So we have retrieved something from the reg. What is it? Brokers are space-separated, so split them out.
    For Each strBroker in Split(Trim(strBrokerList))
        Log LOG_INFO, "DiagnoseAgent", "Broker " & strBroker & " is configured. Testing..."
        JMSTest strBroker, strDiagnosticsDir
    Next
End Sub

Sub DiagnoseClient(strDiagnosticsDir)
    Log LOG_INFO, "DiagnoseClient", "Performing client diagnostics"
    Log LOG_INFO, "DiagnoseClient", "Testing listed View servers..."
    Dim strHost, aBrokerHistory, strPortTest, strHttpRequest, thisURL, logLevel

    ' BrokerHistory contains a good list to work with, which can be passed in as URLs
    On Error Resume Next
    aBrokerHistory = wshell.RegRead(strRegProduct("HKCU\") & "Client\BrokerHistory")
    On Error Goto 0

    If Not IsArray(aBrokerHistory) Then aBrokerHistory = Array()

    For Each strHost in aBrokerHistory
        Log LOG_TRACE, "DiagnoseClient", "  Broker History: " & strHost
        strHttpRequest = TestBrokerURL(strHost, strDiagnosticsDir)
        If strHttpRequest = "INVALID_FORMAT" Then ' registry value contains non-valid data, we can ignore it
            Log LOG_TRACE, "DiagnoseClient", "Ignoring result for " & Quote(strHost)
        Else
            Log LOG_INFO, "DiagnoseClient", "  " & strHost & " - " & strHttpRequest
        End If
    Next

    Log LOG_DEBUG, "DiagnoseClient", "Getting manual list of servers from user..."
    WScript.StdOut.WriteLine "** Please enter the URLs of any additional connection servers you would like"
    WScript.StdOut.WriteLine "** to test against. The tool will attempt to connect to each in turn and will"
    WScript.StdOut.WriteLine "** log the results."
    WScript.StdOut.WriteLine "  Example URL : https://my-broker.mycompany.com/"

    Do
        strHost = PromptUser("  URL [blank to finish]: ", "")
        If Len(strHost) = 0 Then Exit Do

        Log LOG_TRACE, "DiagnoseClient", "User entered '" & strHost & "'"
        If ValidIPOrDNS(strHost) Then
            thisURL = "https://" & strHost
            strHttpRequest = TestBrokerURL(thisURL, strDiagnosticsDir)
            If strHttpRequest <> "OK" And strHttpRequest <> "DNS_FAILURE" Then
                Log LOG_ERROR, "DiagnoseClient", "    " & thisURL & " - " & strHttpRequest
                thisURL = "http://" & strHost
                strHttpRequest = TestBrokerURL(thisURL, strDiagnosticsDir)
            End If
            logLevel = TrueFalse(strHttpRequest = "OK", LOG_INFO, LOG_ERROR)
            Log logLevel, "DiagnoseClient", "    " & thisURL & " - " & strHttpRequest
        Else
            strHttpRequest = TestBrokerURL(strHost, strDiagnosticsDir)
            logLevel = TrueFalse(strHttpRequest = "OK", LOG_INFO, LOG_ERROR)
            Log logLevel, "DiagnoseClient", "    " & strHost & " - " & strHttpRequest
        End If
    Loop
End Sub

Function TestBrokerURL(strInURL, strResultsDir)
    Dim thisURL, strPortTest
    Log LOG_TRACE, "TestBrokerURL", "Test broker string '" & strInURL & "'"
    Set thisURL = New URL
    If thisURL.SetFromString(strInURL) Then
        TestBrokerURL = HTTPTest(thisURL, strResultsDir)
        If TestBrokerURL = "OK" Then
            Log LOG_DEBUG, "TestBrokerURL", "  Connection to " & thisURL.ToString & " succeeded"
            Log LOG_TRACE, "TestBrokerURL", "  HTTP(S) test passed, no need to do TCP connects"
        Else
            Log LOG_DEBUG, "TestBrokerURL", "  Failed: " & thisURL.ToString & " returned " & TestBrokerURL
            strPortTest = TcpTest(thisURL.Server, thisURL.Port)
            If strPortTest = "OK" Then
                Log LOG_DEBUG, "TestBrokerURL", "  TCP port " & thisURL.Server & ":" & thisURL.Port & " is accepting connections"
            Else
                Log LOG_DEBUG, "TestBrokerURL", "  Connect failed to " & thisURL.Server & ":" & thisURL.Port _
                        & " - " & strPortTest
                TestBrokerURL = strPortTest
            End If
        End If
    Else
        TestBrokerURL = thisURL.Err
    End If
End Function


Sub DiagnoseServer(strDiagnosticsDir)
   If boolIsCServer Then
      DiagnoseCS strDiagnosticsDir
   ElseIf boolIsTServer Then
      DiagnoseTS strDiagnosticsDir
   Else
      DiagnoseSS strDiagnosticsDir
   End If
End Sub


Sub DiagnoseCS(strDiagnosticsDir)
    Log LOG_INFO, "DiagnoseCS", "Performing connection server diagnostics"
    ' Get a list of all CSs from LDAP - I should be able to connect to them all
    Dim strValuePair, strGlobalProto, strBroker, bindObject, filter, oConn, oComm, rs, strQuery, obj
    Dim boolGotOne, intSplitPos, strClientProto, strClientHost, strClientPort, strURL, strURLTest

    ' Run ws_diag
    Log LOG_INFO, "DiagnoseCS", "  Collecting diagnostics from ws_diag"
    RunCmd ws_diagCmd, strDiagnosticsDir & "\ws_diag.txt"

    ' Obtain LDAP replica status
    Log LOG_INFO, "DiagnoseCS", "  Getting LDAP replica status"
    RunCmd "cmd /c ""cd %SYSTEMROOT%\ADAM && repadmin /showrepl /all localhost:389""", strDiagnosticsDir & "\ldap_replica_status.txt"

    Set oConn = CreateObject("ADODB.Connection") 'Create an ADO Connection
    oConn.Provider = "ADsDSOOBJECT"              ' ADSI OLE-DB provider
    oConn.Open "ADs Provider"
    Set oComm = CreateObject("ADODB.Command") ' Create an ADO Command
    oComm.ActiveConnection = oConn
    oComm.Properties("Page Size") = 1000

    ' we need to look up the global protocol to see whether ssl is being used, we should exit if we have any errors connecting as gateway should always be present
    strGlobalProto = "https"
    oComm.CommandText = "<LDAP://localhost/cn=gateway,ou=global,ou=properties,dc=vdi,dc=vmware,dc=int>;(objectClass=*);adspath;subtree"
    On Error Resume Next
    Set rs = oComm.Execute
    If Err.Number <> 0 Then
        Log LOG_ERROR, "DiagnoseCS", "An error was encountered when trying to retrieve information about brokers from LDAP."
        ' "table does not exist error" is received when service is not running, although there could be other causes
        If Err.Number = -2147217865 Then
            Log LOG_ERROR, "DiagnoseCS", "  The LDAP object could not be retrieved. The VMware VDMDS service might be stopped, or you may not have View Administrator permissions."
        Else
            CatchErr LOG_ERROR, "DiagnoseCS", "  The error has been saved to the support bundle."
        End If
        Exit Sub
    End If
    rs.MoveFirst : Set obj = GetObject(rs(0)) ' should be only one result returned
    strValuePair = obj.Get("pae-NameValuePair")
    On Error Goto 0

    If Not IsArray(strValuePair) Then
        Log LOG_DEBUG, "DiagnoseCS", "Unable to get global settings for connection brokers"
        strValuePair = Array()
    End If

    For Each obj in strValuePair
        intSplitPos = InStr(obj, "=")
        If Not intSplitPos = 0 Then
            If LCase(Left(obj, intSplitPos - 1)) = "gw-serverprotocol" Then
                strGlobalProto = LCase(Mid(obj, intSplitPos + 1))
                Log LOG_TRACE, "DiagnoseCS", "Global protocol set to " & strGlobalProto
            End If
        End If
    Next

    bindObject = "<LDAP://localhost/ou=server,ou=properties,dc=vdi,dc=vmware,dc=int>;"
    filter =  "(&(objectClass=pae-VDMProperties))"
    strQuery = bindObject & filter & ";adspath;subtree"
    oComm.CommandText = strQuery

    On Error Resume Next
    Set rs = oComm.Execute
    If CatchErr(LOG_ERROR, "DiagnoseCS", "Error while getting list of brokers") Then Exit Sub
    On Error Goto 0

    boolGotOne = False
    rs.MoveFirst
    While Not rs.EOF
        boolGotOne = True
        Set obj = GetObject(rs(0))
        ' get the details for this broker
        On Error Resume Next
        strBroker = obj.Get("pae-AgentConnectToFQHN")
        If Err.Number Then strBroker = Null
        On Error GoTo 0

        If Not IsNull(strBroker) Then
            Log LOG_TRACE, "DiagnoseCS", "Got Connection Server " & strBroker & " from LDAP"
            strClientHost = obj.Get("pae-FQHN")
            strClientProto = strGlobalProto
            strClientPort = TrueFalse(strClientProto = "https", HTTPS_PORT, HTTP_PORT)
            On Error Resume Next ' may not be there
            strValuePair = obj.Get("pae-NameValuePair")
            If Err.Number Then strValuePair = Null
            On Error GoTo 0

            If Not IsArray(strValuePair) Then
                Log LOG_DEBUG, "DiagnoseCS", "Unable to get settings for connection broker " & strBroker
                strValuePair = Array()
            End If

            For Each obj in strValuePair
                intSplitPos = InStr(obj, "=")
                If Not intSplitPos = 0 Then
                    Select Case LCase(Left(obj, intSplitPos - 1))
                        Case "gw-clienthost" : strClientHost = Mid(obj, intSplitPos + 1)
                        Case "gw-clientprotocol" : strClientProto = Mid(obj, intSplitPos + 1)
                        Case "gw-clientport" : strClientPort = Mid(obj, intSplitPos + 1)
                    End Select
                End If
            Next

            ' We can now run our tests
            Log LOG_INFO, "DiagnoseCS", "Broker " & strBroker & " is configured. Testing..."
            Log LOG_INFO, "DiagnoseCS" , "Testing message bus connection"
            JMSTest strBroker, strDiagnosticsDir
            Log LOG_INFO, "DiagnoseCS", "Testing directory service connection"
            LDAPTest strBroker, strDiagnosticsDir
            strURL = strClientProto & "://" & strClientHost & ":" & strClientPort
            Log LOG_INFO, "DiagnoseCS", "Testing broker service"
            strURLTest = TestBrokerURL(strURL, strDiagnosticsDir)
            If strURLTest = "OK" Then
                Log LOG_INFO, "DiagnoseCS", "  Broker listening at " & strURL
            Else
                Log LOG_ERROR, "DiagnoseCS", "Failed to connect to broker " & strURL & " - " & strURLTest
            End If
        Else
            On Error Resume Next
            strBroker = obj.Get("Name")
            If Err.Number Then strBroker = "UnknownSS"
            On Error GoTo 0
            Log LOG_TRACE, "DiagnoseCS", "Got Security Server " & strBroker & " from LDAP"
        End If
        rs.MoveNext
    Wend

    If Not boolGotOne Then
        Log LOG_ERROR, "DiagnoseCS", "  Local ADAM instance has no brokers defined. Is View Connection Server installed?"
    End If
End Sub

Sub DiagnoseTS(strDiagnosticsDir)
    Log LOG_INFO, "DiagnoseTS", "Performing transfer server diagnostics"

    ' Run ws_diag
    Log LOG_INFO, "DiagnoseTS", "  Collecting diagnostics from ws_diag"
    RunCmd ws_diagCmd, strDiagnosticsDir & "\ws_diag.txt"
End Sub

Sub DiagnoseSS(strDiagnosticsDir)
    Dim strPortTest, a, strSettingsFile, strSettings, fSettings, strChopped, strPairedHost, strPairedPort, intPairedPort

    Log LOG_INFO, "DiagnoseSS", "Performing security server diagnostics"

    ' Run ws_diag
    Log LOG_INFO, "DiagnoseSS", "  Collecting diagnostics from ws_diag"
    RunCmd ws_diagCmd, strDiagnosticsDir & "\ws_diag.txt"

    ' Find the CS I am paired with
    ' Line in settings.properties that looks like frontMapping=10:*:ajp:cs1.example.com:8009
    strSettingsFile = installpath & "sslgateway\conf\settings.properties"
    If Not fso.FileExists(strSettingsFile) Then
        Log LOG_ERROR, "DiagnoseSS", "File " & strSettingsFile & " does not exist"
        Exit Sub
    End If

    If Not fso.GetFile(strSettingsFile).Size > 0 Then
        Log LOG_ERROR, "DiagnoseSS", "File " & strSettingsFile & " is empty"
        Exit Sub
    End If

    ' Get the whole file contents
    Set fSettings = fso.OpenTextFile(strSettingsfile, 1)
    Log LOG_TRACE, "DiagnoseSS", "Reading " & strSettingsFile & " to get AJP pairing info"
    strSettings = fSettings.ReadAll
    strSettings = Replace(strSettings,"\:",":")
    fSettings.Close

    ' try getting hostname and port pairing info
    On Error Resume Next
    strChopped = Right(strSettings, Len(strSettings) - InStr(strSettings, "ajp:") - 3) ' got rid of everything up to ajp:
    strChopped = Trim(Left(strChopped, InStr(strChopped, vbNewLine))) ' throw away everything after that line
    a = Split(strChopped, ":")
    strPairedHost = a(0)
    strPairedPort = a(1)
    intPairedPort = CLng(strPairedPort)
    Log LOG_TRACE, "DiagnoseSS", "AJP pairing info - HOST=" & strPairedHost & ";PORT=" & strPairedPort
    CatchErr LOG_DEBUG, "DiagnoseSS", "Error while parsing settings.properties"
    On Error GoTo 0

    If Not ValidIPOrDNS(strPairedHost) Then
        Log LOG_ERROR, "DiagnoseSS", "Paired connection server hostname " & Quote(strPairedHost) & " is not valid"
        Exit Sub
    End If

    If Not IsNumeric(intPairedPort) Or intPairedPort < MIN_PORT Or intPairedPort > MAX_PORT Then
        Log LOG_ERROR, "DiagnoseSS", "Paired connection server port " & Quote(strPairedPort) & " is not valid"
        Exit Sub
    End If

    Log LOG_INFO, "DiagnoseSS", "This connection server is paired with " & Quote(strPairedHost)
    Log LOG_INFO, "DiagnoseSS", "Testing message bus connection to " & strPairedHost
    JMSTest strPairedHost, strDiagnosticsDir
    Log LOG_INFO, "DiagnoseSS", "Testing proxy service connection to " & strPairedHost & ":" & strPairedPort
    strPortTest = TcpTest(strPairedHost, intPairedPort)
    DisplayError strPortTest
    If strPortTest = "OK" Then Log LOG_INFO, "DiagnoseSS", "Paired connection server's proxy service is running OK"
End Sub

Sub LDAPTest(strHost, strOutputDir)
    Dim strTcpPortResult, oConn, oComm, rs, bindObject, filter, strQuery
    bindObject = "<LDAP://" & strHost & ":" & LDAP_PORT & "/ou=applications,dc=vdi,dc=vmware,dc=int>;"
    filter =  "(&(objectClass=top))"
    strQuery = bindObject & filter & ";adspath;subtree"

    If Not ValidIPOrDNS(strHost) Then
        Log LOG_ERROR, "LDAPTest", "Broker name " & strHost & " is not valid"
        Exit Sub
    End If

    strTcpPortResult = TcpTest(strHost, LDAP_PORT)
    If strTcpPortResult <> "OK" Then
        Log LOG_DEBUG, "LDAPTest", "TcpTest returned " & strTcpPortResult
        Log LOG_ERROR, "LDAPTest", "Unable to connect to " & strHost & ":" & LDAP_PORT
        Exit Sub
    End If

    Set oConn = CreateObject("ADODB.Connection") 'Create an ADO Connection
    oConn.Provider = "ADsDSOOBJECT"              ' ADSI OLE-DB provider
    oConn.Open "ADs Provider"
    Set oComm = CreateObject("ADODB.Command") ' Create an ADO Command
    oComm.ActiveConnection = oConn
    oComm.Properties("Page Size") = 1000
    oComm.CommandText = strQuery

    On Error Resume Next
    Set rs = oComm.Execute
    If Err.Number <> 0 Then
        Log LOG_ERROR, "LDAPTest", "An error was encountered when trying to talk to the directory service on " & strHost
        If Err.Number = -2147217865 Then    ' "table does not exist error" is received when service is not running
            Log LOG_ERROR, "LDAPTest", "The LDAP object could not be retrieved. Is the VMware VDMDS service running?"
            ' although there could be other causes
            Exit Sub
        End If
        CatchErr LOG_DEBUG, "LDAPTest", "  Unrecognised error, details below."
        Exit Sub
    End If
    On Error Goto 0

    If Not rs.recordcount > 0 Then
        Log LOG_DEBUG, "LDAPTest", "Expected >0 entries under ou=Applications"
        Log LOG_ERROR, "LDAPTest", "Connected to the directory service successfully, but did not find the data expected"
    Else
        Log LOG_INFO, "LDAPTest", "Connected to the directory service successfully"
    End If
End Sub

Sub JMSTest(strHost, strOutputDir)
    Const strTopic = "topic/IceTunnelTopic"
    Dim strTcpPortResult, strJMSPublishTestResult

    If Not ValidIPOrDNS(strHost) Then
        Log LOG_ERROR, "JMSTest", "Broker name " & strHost & " is not valid"
        Exit Sub
    End If

    ' Test the JMS connection. If it fails, try to do a simple TCP connection to identify the problem
    strJMSPublishTestResult = JMSPublishTest(strHost, strTopic, strOutputDir)
    If strJMSPublishTestResult = "OK" Then
        Log LOG_INFO, "JMSTest", "Published to message bus successfully"
        Log LOG_TRACE, "JMSTest", "JMS test passed, no need to test a port connection"
    Else
        Log LOG_ERROR, "JMSTest", "Error when attempting to publish message, testing TCP port"
        strTcpPortResult = TcpTest(strHost, JMS_PORT)
        If strTcpPortResult = "OK" Then
            Log LOG_INFO, "JMSTest",  "Connected successfully to TCP port " & strHost & ":" & JMS_PORT
        Else
            Log LOG_ERROR, "JMSTest", "Unable to connect to " & strHost & ":" & JMS_PORT & " - " & strTcpPortResult
            Exit Sub
        End If
    End If
End Sub

Function JMSPublishTest(strHost, strTopic, strOutputDir)
    ' construct the command to run
    Dim strJavaExe, strClassPath, strJMSPublishTestCmd
    strJavaExe = installpath & "jre\bin\java.exe"
    strClassPath = installpath & "lib\swiftmq-8.1.0.jar;" & installpath & "lib\jms-8.1.0.jar"
    strClassPath = Quote(jmsTestJar & ";" & strClassPath)
    strJMSPublishTestCmd = Quote(strJavaExe) & " -classpath " & strClassPath & " JMSPublishTest"

    Dim strResult, strOutfile
    strOutfile = strOutputDir & "jmspublishtest-" & strHost & ".txt"
    strResult = RunCmd(strJMSPublishTestCmd & " " & strHost & " " & strTopic, strOutfile)    ' TODO: Read stderr as well
    If InStr(strResult, "OK") Then
        JMSPublishTest = "OK"
        Exit Function
    Else
        JMSPublishTest = "UNKNOWN_ERROR"
        Log LOG_DEBUG, "JMSPublishTest", "Output not recognised. Output was: " & strResult
        Exit Function
    End If
End Function


Sub DisplayError(strIn)
    Select Case strIn
        Case "HOST_NOT_FOUND"
            Log LOG_ERROR, "DisplayError", "This machine cannot resolve the hostname. Check your DNS settings."
        Case "CONNECT_TIMEOUT"
            Log LOG_ERROR, "DisplayError", "Connection timeout for the remote port. Please check that the " _
                & "server is powered on and there is no firewall blocking the connection."
        Case "CONNECT_REFUSED"
            Log LOG_ERROR, "DisplayError", "The TCP connection was actively denied. Please check that the " _
                & "service is running and there is no firewall blocking the connection."
        Case "OK"
            ' Don't log anything
        Case Else
            Log LOG_ERROR, "DisplayError", "Unknown error - " & strIn
    End Select
End Sub


Function TestExecSupport
    Dim aVer
    aVer = Split(WScript.Version, ".")
    If CInt(aVer(0)) >= 5 And CInt(aVer(1)) >= 6 Then
        TestExecSupport = True
    Else
        TestExecSupport = False
    End If
End Function

Function HTTPTest(objURL, strOutputDir)
    Dim WinHTTP, strURL, strOutFile, fOutput, errNum, errDesc, strResponse
    ' The xml API info, if we get a positive result on this, we can assume that this is a broker.
    ' TODO: Actually analyse the response
    Const HTTPREQUEST_PROXYSETTING_DIRECT = 1
    Const strApiReqPath = "/broker/xml"
    Const strApiReqType = "POST"
    Const strApiReqData = "<?xml version=""1.0""?><broker version=""4.0""><get-configuration/></broker>"
    ' timeouts to 30s for each stage
    Const intTimeouts = 30000
    ' present on 2000 SP3, XP SP1, Server 2003 SP1
    Set WinHTTP = CreateObject("WinHttp.WinHttprequest.5.1")
    ' defaults to using the system's  proxy settings, which may not be the same as an individual user's
    ' we should override this for now and only test direct connections
    ' TODO: give the user the option of specifying a proxy server to test through
    WinHTTP.SetProxy HTTPREQUEST_PROXYSETTING_DIRECT

    ' Set the timeouts - resolve, connect, send, receive
    WinHTTP.SetTimeouts intTimeouts, intTimeouts, intTimeouts, intTimeouts

    ' Construct the URL and the filename to save the result to
    objUrl.setPath(strApiReqPath)
    strUrl = objUrl.ToString
    strOutFile = strOutputDir & objUrl.Proto & "-" _
            & objUrl.Server & "-" & objUrl.Port & ".txt"

    ' ignore SSL cert problems
    const SslErrorIgnoreFlagsOption = 4
    const SslIgnoreAll = &H3300
    WinHTTP.Option(SslErrorIgnoreFlagsOption) = SslIgnoreAll

    ' set up the request
    On Error Resume Next
    WinHTTP.Open strApiReqType, strURL, False
    CatchErr LOG_DEBUG, "HTTPTest", "Could not open URL " & strURL
    On Error GoTo 0

    ' Open up the output file for writing
    OutFile strOutFile, fOutput

    ' Don't die on failure, we're testing for that!
    On Error Resume Next
    WinHTTP.Send strApiReqData
    errNum = Err.Number
    errDesc = Err.Description
    strResponse = WinHTTP.ResponseText
    On Error GoTo 0
    ' Catch error info
    If errNum Then ' Not able to do an HTTP(s) request
        HTTPTest = "NOT_HTTP"
        Log LOG_DEBUG, "HTTPTest", "Unable to make a request to " & objURL.ToString & ". [" & errNum & "] " & errDesc
        fOutput.Write "Unable to make a request to " & objURL.ToString & ". [" & errNum & "] " & errDesc & vbCrLf & vbCrLf
        fOutput.Write strResponse
    Else ' Could do an HTTP(S) request
        fOutput.Write "Status: " & WinHttp.Status & " - " & WinHttp.StatusText _
                & vbCrLf & vbCrLf & strResponse
        If WinHTTP.Status = 200 Then
            HTTPTest = "OK"
            Log LOG_DEBUG, "HTTPTest", "Connected to View server " & objURL.ToString & " successfully."
        Else
            HTTPTest = "HTTP_ERROR"
            Log LOG_DEBUG, "HTTPTest", "Connected to host " & objURL.ToString & " successfully, but received return code " & WinHTTP.Status
        End If
    End If

    ' close the file
    fOutput.Close
    Set fOutput = Nothing
End Function

Function ValidIPOrDNS(strHost)
    ValidIPOrDNS = isValidIP(strHost) Or isValidDomain(strHost) Or isValidHost(strHost)
End Function

Function isValidIP(strIn)
    ' represents any number between 0 and 255
    Const strIPSegment = "25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?"
    isValidIP = RegExpMatch(strIn, "^(" & strIPSegment & ")(\.(" & strIPSegment & ")){3}$", FALSE)
End Function

Function isValidDomain(strIn)
    isValidDomain = RegExpMatch(strIn, "^([a-z0-9]([a-z0-9\-]{0,61}[a-z0-9])?\.)+[a-z]{2,6}$", TRUE)
End Function

Function isValidHost(strIn)
    isValidHost = RegExpMatch(strIn, "^[a-z0-9]([a-z0-9\-]{0,61}[a-z0-9])?$", TRUE)
End Function

Function RegExpMatch(strOriginalString, strPattern, boolIgnoreCase)
    If IsNull(strOriginalString) Or IsNull(strPattern) Then
        RegExpMatch = False
        Exit Function
    End If

    ' Function matches pattern, returns true or false
    dim objRegExp : set objRegExp = new RegExp
    With objRegExp
        .Pattern = strPattern
        .IgnoreCase = boolIgnoreCase
        .Global = True
    End With
    RegExpMatch = objRegExp.test(strOriginalString)
    set objRegExp = nothing
End Function

Function GetOS()
    'WMI is required for this script to function
    Dim strWMIOS

    Dim objWmiService : Set objWMIService = GetObject("winmgmts:{impersonationLevel=impersonate}!\\.\root\cimv2")
    Dim strOsQuery : strOsQuery = "Select * from Win32_OperatingSystem"
    Dim colOperatingSystems : Set colOperatingSystems = objWMIService.ExecQuery(strOsQuery)
    Dim objOs
    Dim strOsVer
    Dim firstDelimPos, osVer

    For Each objOs in colOperatingSystems
        strWmios = objOs.Caption & " " & objOs.Version
        firstDelimPos = instr(objOs.Version, ".")

        if firstDelimPos > 0 then
            firstDelimPos = firstDelimPos - 1
            strOsVer = Left(objOs.Version, firstDelimPos)
        end if
    Next

    'http://msdn.microsoft.com/en-us/library/windows/desktop/ms724832%28v=vs.85%29.aspx
    ' Vista is version 6.0
    If CInt(strOsVer) >= 6 then
        boolIsVistaOrHigher = True
    else
        boolIsVistaOrHigher = False
    end if

    Log LOG_DEBUG, "GetOS", "OS string: " & Quote(strWmiOS)

    Select Case True
        Case InStr(strWmiOS, "2000 Server") > 1 : boolIsW2KS = True
        Case InStr(strWmiOS, "2003") > 1 : boolIsW2K3 = True
        Case InStr(strWmiOS, "2000 Advanced Server") > 1 : boolIsW2KS = True
        Case InStr(strWmiOS, "Windows 2000") > 1 : boolIsW2K = True
        Case InStr(strWmiOS, "Windows XP") > 1 : boolIsXP = True
        Case InStr(strWmiOS, "Windows(R) XP") > 1 : boolIsXP = True
        Case InStr(strWmiOS, "Windows Vista") > 1 : boolIsVista = True
        Case InStr(strWmiOS, "Server® 2008") > 1 : boolIsW2K8 = True
        Case InStr(strWmiOS, "Server 2008 R2") > 1 : boolIsW2K8 = True
        Case InStr(strWmiOS, "Windows 7 ") > 1 : boolIsWin7 = True
        Case InStr(strWmiOS, "Windows 8") > 1 : boolIsWin8 = True
        Case InStr(strWmiOS, "Windows Embedded Standard ") > 1 : boolIsWES7 = True
        Case Else
            Log LOG_ERROR, "GetOS", "OS " & Quote(strWmiOS) & " is not supported!"
    End Select
End Function

'  ### Determine the log directory for a specific user if overridden, otherwise accept default provided ###
Function GetUserLogDirectory(User,DefaultDir)
	GetUserLogDirectory = ReadRegString(strRegPolicy("HKLM\") & "Log\LogFileDirectory")

	If IsNull(GetUserLogDirectory) Then
        GetUserLogDirectory = ReadRegString(strRegProduct("HKLM\") & "Log\LogFileDirectory")
    End If

    If IsNull(GetUserLogDirectory) Then
		GetUserLogDirectory = DefaultDir
	Else
		GetUserLogDirectory = GetUserLogDirectory & "\" & User
	End If
End Function

Function UsingDefaultLogDirectory()
    If IsNull(ReadRegString(strRegPolicy("HKLM\") & "Log\LogFileDirectory")) And IsNull(ReadRegString(strRegProduct("HKLM\") & "Log\LogFileDirectory")) Then
        UsingDefaultLogDirectory = True
    Else
        UsingDefaultLogDirectory = False
    End If
End Function

'  ### Determine the base log directory (either overriden in registry or default) ###
Function GetLogDirectory()
	GetLogDirectory = ReadRegString(strRegPolicy("HKLM\") & "Log\LogFileDirectory")

	If IsNull(GetLogDirectory) Then
        GetLogDirectory = ReadRegString(strRegProduct("HKLM\") & "Log\LogFileDirectory")
    End If

    If IsNull(GetLogDirectory) Then
		GetLogDirectory = AppShell.Namespace(COMMON_APPDATA).Self.Path & "\VMware\VDM\logs\"
	End If
End Function

Function GetInstallPath()
    Dim strInstallPathRegValue
    ' TODO: Rethink if we allow multiple components to be installed on one computer
    Select Case True
        Case boolIsClient : strInstallPathRegValue = "ClientInstallPath"
        Case boolIsServer : strInstallPathRegValue = "ServerInstallPath"
        Case boolIsAgent : strInstallPathRegValue = "AgentInstallPath"
    End Select
    GetInstallPath = ReadRegString(strRegProduct("HKLM\") & strInstallPathRegValue)

   ' The 4.5 client is actually 32-bit, but is now installed from a 64-bit
   ' installer, so also fall back to checking the WoW key.
   If IsNull(GetInstallPath) Then
      boolIs32On64 = True
      GetInstallPath = ReadRegString(strRegProduct("HKLM\") & strInstallPathRegValue)
   End If

    If IsNull(GetInstallPath) Then
        Log LOG_ERROR, "GetInstallPath", "Install path " & strInstallPathRegValue _
                & " does not exist."
        WScript.Quit 5
    ElseIf Not FSO.FolderExists(GetInstallPath) Then
        Log LOG_ERROR, "GetInstallPath", "Install path from " & strInstallPathRegValue _
                & " " & Quote(GetInstallPath) & " is not valid."
        WScript.Quit 5
    Else
        Log LOG_DEBUG, "GetInstallPath", "View component installed in " & Quote(GetInstallPath)
    End If
End Function


Sub GatherSystemConfig
    Dim strDrvEtc, FirewallConfigFile, strSysConfigDir
    Log LOG_INFO, "GatherSytemConfig", "Gathering system information..."
    strSysConfigDir = workdir  & "\computer-config\"
    If Not fso.FolderExists(strSysConfigDir) Then
        fso.CreateFolder(strSysConfigDir)
    End If

   If boolIsAgent Then
      Log LOG_INFO, "GatherSystemConfig", "  Gathering sysprep logs..."
      Call GatherSysprepLogs(strSysConfigDir)
   End If

   If boolIsAgent Or boolIsClient Then
      Log LOG_INFO, "GatherSystemConfig", "  Retrieving display configuration..."
      RunCmd dxDiagCmd & strSysConfigDir & "\dxdiag.txt", ""
      RunCmd ws_diagCmd & " -monitor", strSysConfigDir & "\monitor-info.txt"
   End If

   If boolIsAgent Or boolIsClient Then
      Log LOG_INFO, "GatherSystemConfig", "  Retrieving desktop theme information..."
      RunCmd ws_diagCmd & " -theme", strSysConfigDir & "\theme-info.txt"
   End If

    Log LOG_INFO, "GatherSystemConfig", "  Retrieving information on your environment variables..."
    RunCmd "%comspec% /c ver", strSysConfigDir & "\ver.txt"
    RunCmd "%comspec% /c set", strSysConfigDir & "\set.txt"

    Log LOG_INFO, "GatherSystemConfig", "  Retrieving network information (could take minutes)..."
    RunCmd "ipconfig /all", strSysConfigDir & "\ipconfig-all.txt"
    RunCmd "netstat -an",   strSysConfigDir & "\netstat-an.txt"
    RunCmd "netstat -abov", strSysConfigDir & "\netstat-abov.txt"
    RunCmd "netstat -es", strSysConfigDir & "\netstat-es.txt"
    RunCmd "route print",   strSysConfigDir & "\route-print.txt"
    RunCmd "arp -a",        strSysConfigDir & "\arp-a.txt"
    RunCmd "netsh diag show test /v", strSysConfigDir & "\netsh-diag-show-test-v.txt"

    strDrvEtc = appshell.Namespace(SYSTEM32).Self.Path & "\drivers\etc"
    CopyFile strDrvEtc & "\hosts", strSysConfigDir & "\etc-hosts.txt"
    CopyFile strDrvEtc & "\lmhosts", strSysConfigDir & "\etc-lmhosts.txt"

    Log LOG_INFO, "GatherSystemConfig", "  Retrieving information on running processes and services..."
    RunCmd "net start",     strSysConfigDir & "\net-start.txt"
    RunCmd "tasklist /V",   strSysConfigDir & "\tasklist-v.txt"
    RunCmd "tasklist /SVC", strSysConfigDir & "\tasklist-svc.txt"

    Log LOG_INFO, "GatherSystemConfig", "  Retrieving Windows configuration information..."
    RunCmd "systeminfo",    strSysConfigDir & "\systeminfo.txt"

    Log LOG_INFO, "GatherSystemConfig", "  Retrieving firewall configuration..."
    FirewallConfigFile = strSysConfigDir & "\firewall.txt"
    RunCmd "netsh firewall show state", FirewallConfigFile
    RunCmd "netsh firewall show service", FirewallConfigFile
    RunCmd "netsh firewall show config", FirewallConfigFile
    RunCmd "netsh firewall show currentprofile", FirewallConfigFile
    RunCmd "netsh firewall show opmode", FirewallConfigFile
    RunCmd "netsh firewall show allowedprogram", FirewallConfigFile
    RunCmd "netsh firewall show portopening", FirewallConfigFile
End Sub


Sub GatherSysprepLogs(strSysConfigDir)
   Dim strSysprepDir, strImcDir, strSys32Dir
   strSysprepDir = strSysConfigDir & "\sysprep"
   strSys32Dir = appshell.Namespace(SYSTEM32).Self.Path

   If Not FSO.FolderExists(strSysprepDir) Then FSO.CreateFolder(strSysprepDir)

   StageFiles SysTempDir() & "\vmware-imc", ".*", strSysprepDir & "\vmware-imc"
   StageFiles SysTempDir() & "\imc", ".*", strSysprepDir & "\imc"

   ' Varies on XP vs Vista.
   If boolIsVistaOrHigher Then
      strSys32Dir = strSys32Dir & "\sysprep"
      copyFolderRecursively strSys32Dir, strSysprepDir
   Else
      StageFiles strSys32Dir, "^setup.*log$", strSysprepDir & "\system32"
      StageFiles strSys32Dir & "\inf", "^setup.*log$", strSysprepDir & "\system32-inf"
   End If
End Sub


Sub GatherSviData
   Dim strSviService, strSviFolder

   strSviService = ReadRegString("HKLM\SYSTEM\CurrentControlSet\Services\vmware-viewcomposer-ga\ImagePath")

   If IsNull(strSviService) Then
      Log LOG_DEBUG, "GatherSviData", "Composer service not found, skipping."
      Exit Sub
   End If

   strSviService = Replace(strSviService, """", "")
   Log LOG_TRACE, "GatherSviData", "Composer service has ImagePath " & Quote(strSviService)

   If IsNull(strSviService) Or Not FSO.FileExists(strSviService) Then
      Log LOG_DEBUG, "GatherSviData", "Did not find Composer at path " & Quote(strSviService)
      Exit Sub
   End If

   Log LOG_INFO, "GatherSviData", "Gathering SVI information..."
   strSviFolder = FSO.GetFile(strSviService).ParentFolder.Path
   RunCmd "cscript " & Quote(strSviFolder & "\svi-ga-support.wsf") & " /destdir:" & Quote(WorkDir), WorkDir & "\svi-ga-support.txt"
End Sub


Sub GatherVPData
   Dim strVPDir, strLogPath, strUserModeDir
   strVPDir = WorkDir & "\vp-data"

   Const vpfsdService = "SYSTEM\CurrentControlSet\Services\vmwvvpfsd"
   ' Quick check for installed VP file system driver service
   If IsNull(ReadRegString("HKLM\" & vpfsdService & "\ImagePath")) Then
      Log LOG_DEBUG, "GatherVPData", "VP module not installed"
      Exit Sub
   End If

   Log LOG_INFO, "GatherVPData", "Gathering persona management information..."
   If Not FSO.FolderExists(strVPDir) Then FSO.CreateFolder(strVPDir)

   DumpKey HKLM, vpfsdService, strVPDir & "\vmwvvpfsd-reg.txt"
   DumpKey HKLM, "SOFTWARE\Microsoft\Windows NT\CurrentVersion\ProfileList", strVPDir & "\profilelist-reg.txt"

   strLogPath = ReadRegString("HKLM\System\CurrentControlSet\Services\vmwvvpfsd\Service\Log Path")
   If Not IsNull(strLogPath) Then
      CopyFile strLogPath, strVPDir
      CopyFile strLogPath & ".bak", strVPDir
   End If

   strUserModeDir = WShell.ExpandEnvironmentStrings("%SYSTEMROOT%\Debug\UserMode")
   StageFiles strUserModeDir, "^userenv", strVPDir

End Sub


Sub GatherMvdiData
    Dim strMvdiDir, strMvdiList, strVMX, strVMFolder, strDestFolder, aConfigFiles
    Log LOG_INFO, "GatherMvdiData", "Gathering MVDI information..."
    strMvdiDir = workdir  & "\mvdi-data"
    If Not fso.FolderExists(strMvdiDir) Then fso.CreateFolder(strMvdiDir)

    Log LOG_INFO, "GatherMvdiData", "  Checking for the list of checked out desktops..."
    strMvdiList = AppShell.Namespace(LOCALAPPDATA).Self.Path & "\VMware\VDM\mvdi.lst"
    If Not CopyFile(strMvdiList, strMvdiDir) Then
        Log LOG_INFO, "GatherMvdiData", "    Could not find the list of checked out desktops for this user"
        Exit Sub
    End If

    ' Find all config files referenced in HKCU\Software\VMware, Inc.\VMware VDM\Client\OfflineConfigFiles
    On Error Resume Next
    Err.Clear
    aConfigFiles = wshell.RegRead("HKCU\Software\" & companyName & "\" & prodRegKey & "\Client\OfflineConfigFiles")
    If Err.Number Then
        Log LOG_TRACE, "GatherMvdiData", "Unable to read offline config files from registry."
		Exit Sub
	End If

    If Not IsArray(aConfigFiles) Then aConfigFiles = Array()

    For Each strVMX in aConfigFiles
				Log LOG_TRACE, "GatherMvdiData", "Found match " & Quote(strVMX)
                GatherMvdiVM strVMX, strMvdiDir
	Next

End Sub


Sub GatherMvdiVM(strVMX, strDestBase)
   Dim objVMX, strVMFolder, strDestFolder

   If FSO.FileExists(strVMX) Then
      Set objVMX = FSO.GetFile(strVMX)
      Log LOG_INFO, "GatherMvdiVM", "    Found VM " & Quote(objVMX.Name)
      strDestFolder = strDestBase & "\" & objVMX.Name
      If Not FSO.FolderExists(strDestFolder) Then
         strVMFolder = objVMX.ParentFolder.Path
         FSO.CreateFolder(strDestFolder)
         CopyFile strVMX, strDestFolder
         CopyFile strVMFolder & "\mvdi.session", strDestFolder
         CopyFile strVMFolder & "\ace.dat", strDestFolder
         StageFiles strVMFolder, "\.vmpl$", strDestFolder
         StageFiles strVMFolder, "\.vmsd$", strDestFolder
         StageFiles strVMFolder, "\.log$", strDestFolder
         StageFiles strVMFolder, "\.dmp$", strDestFolder
      Else
         Log LOG_ERROR, "GatherMvdiVM", "    Folder " & Quote(objVMX.Name) & " already exists"
      End If
   Else
      Log LOG_DEBUG, "GatherMvdiVM", "VM does not exist: " & Quote(strVMX)
   End If
End Sub

' Sanitize a secret key.
' Replace all but the first 5 characters of a key with "X"
Function StripKey(FilePath, KeyKey)
    On Error Resume Next
    Err.Clear

    Dim inFile, tempFile, curLine

    If Not FSO.FileExists(FilePath) Then
        Log LOG_TRACE, "StripKey", "Cannot sanitize " & Quote(FilePath) & " as file does not exist."
        StripKey = False
    Else

        Set inFile = FSO.OpenTextFile(FilePath, ForReading, False, TriStateUnicode)
        Set tempFile = FSO.OpenTextFile(FilePath & ".tmp", ForWriting, True)
        Do While Not inFile.AtEndofStream
            curLine = inFile.ReadLine
            If StrComp(Left(curLine,Len(KeyKey)),KeyKey) = 0 Then
                Dim wsCount, obfStart
                wsCount = Len(curLine) - Len(Replace(Replace(curLine,vbTab,"")," ",""))
                ' Key length + ":" + whitespace + first 5 chars of the key
                obfStart = Len(KeyKey) + 1 + wsCount + 5
                curLine = Left(curLine,obfStart)&"..."
            End If
            tempFile.WriteLine(curLine)
        Loop
        inFile.Close
        tempFile.Close
        FSO.DeleteFile(filePath)
        FSO.MoveFile filePath&".tmp", filePath

        StripKey = True
        If CatchErr(LOG_TRACE, "StripKey", "Cannot sanitize " & Quote(FilePath)) Then
            StripKey = False
        End If

    End If

End Function

Sub GatherProductConfig
    Dim strConfigDir, strLocalArbDataDir, strArbConfigPattern
    Log LOG_INFO, "GatherProductConfig", "Gathering " & prodName & " configuration details..."
    strConfigDir = workdir  & "\" & strShortName & "-config\"
    If Not fso.FolderExists(strConfigDir) Then
        fso.CreateFolder(strConfigDir)
    End If
    strLocalArbDataDir = AppShell.Namespace(COMMON_APPDATA).Self.Path & "\VMware\VMware USB Arbitration Service\"
    strArbConfigPattern = ".*"

   ' Some properties files, LDIF data, and ws_diag output from the server
   If boolIsServer Then
      CopyFile installpath & "sslgateway\conf\locked.properties", strConfigDir   ' If created only
      CopyFile installpath & "sslgateway\conf\settings.properties", strConfigDir ' Security server only
      CopyFile installpath & "sslgateway\conf\config.properties", strConfigDir ' SS info from generated file on CS
      CopyFile installpath & "sslgateway\conf\pae_jms.properties", strConfigDir  ' Security server only
	' Show IPsec status for all server types (Connection Server, Security Server and Transfer Server)
      RunCmd "netsh advfirewall consec show rule name=all", strConfigDir & "\ipsec-consecrules.txt"
      StripKey strConfigDir & "\ipsec-consecrules.txt", "Auth1PSK"
      RunCmd "netsh advfirewall mainmode show rule name=all", strConfigDir & "\ipsec-mmrules.txt"
      StripKey strConfigDir & "\ipsec-mmrules.txt", "Auth1PSK"
      RunCmd "netsh advfirewall monitor show qmsa", strConfigDir & "\ipsec-qmsa.txt"
      RunCmd "netsh advfirewall monitor show mmsa", strConfigDir & "\ipsec-mmsa.txt"
	' Show Windows certificate store contents for all server types (Connection Server, Security Server and Transfer Server)
      RunCmd "certutil -v -store My", strConfigDir & "\cert-store.txt"
      If boolIsCServer Then
         CopyFile installpath & "messagebus\config\routerconfig.xml", strConfigDir
         RunCmd "ldifde -f " & Quote(strConfigDir & "\adam.ldif") & " -s 127.0.0.1 -d dc=vdi,dc=vmware,dc=int -o pae-SVIUserPassword,pae-VCUserPassword,pae-DatabasePassword,pae-ImageRepositoryPassword,pae-LicenseKey,pae-LicenseKey2,pae-BackupProtection,pae-SecurIDConf ", strConfigDir & "\ldifde.txt"
         RunCmd installpath & "tools\bin\vdmexport -c ", strConfigDir & "\vdmexport.ldf"
      ElseIf boolIsTServer Then
         CopyFile installpath & "httpd\conf\httpd.conf", strConfigDir         ' If created only
         CopyFile installpath & "httpd\conf\mod_vprov.conf", strConfigDir     ' If created only
      End If
   End If

    ' Contents of the registry
    DumpKey HKLM, strRegSoft("") & companyName, strConfigDir & "\vmware-reg.txt"
    DumpKey HKLM, "SOFTWARE\Policies", strConfigDir & "\policies-reg.txt"
    ' TODO: Iterate through all local users and pull registry settings
    If boolIsClient Then
        DumpKey HKCU, strRegSoft("") & companyName, strConfigDir & "\vmware-userreg.txt"
        DumpKey HKCU, "SOFTWARE\Policies", strConfigDir & "\policies-userreg.txt"
        StageFiles strLocalArbDataDir, strArbConfigPattern, strConfigDir & "Arbitrator\"
    End If
    ' Interested in winlogon settings too now, for both client and agent
    If boolIsClient Or boolIsAgent Then
        DumpKey HKLM, "SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon", strConfigDir & "\win-winlogon-reg.txt"
        DumpKey HKLM, "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication", strConfigDir & "\win-authentication-reg.txt"
        DumpKey HKLM, "SOFTWARE\Microsoft\Windows\CurrentVersion\Policies", strConfigDir & "\win-policies-reg.txt"
    End If
End Sub

Sub ExportUSBRegistry
    Dim strExportRegDir
    Log LOG_INFO, "ExportUSBRegistry", "Exporting USB Registry..."
    strExportRegDir = workdir & "\" & strShortName & "-USB-Registry-Data\"
    If Not fso.FolderExists(strExportRegDir) Then
        fso.CreateFolder(strExportRegDir)
    End If
    strExportRegDir = chr(34) & strExportRegDir  & "USB-Reg.txt" & chr(34)
    WShell.run "reg export HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Enum\USB " & strExportRegDir
End Sub

Function strRegPath(KeyType, Path)
    Select Case KeyType
        Case HKLM : strRegPath = "HKEY_LOCAL_MACHINE\" & Path
        Case HKCU : strRegPath = "HKEY_CURRENT_USER\" & Path
        Case Else : strRegPath = "REG:" & KeyType & "\" & Path
    End Select
End Function

Sub DumpKey(DefKey, Path, filename)
    Dim f1, Keys
    reg.EnumKey DefKey, Path, Keys
    If IsNull(Keys) Then
        WriteFile filename, "Registry key " & Quote(strRegPath(DefKey,Path)) & " does not exist."
    Else
        OutFile filename, f1
        EnumerateKey DefKey, Path, f1
        f1.Close
    End If
End Sub

Sub EnumerateKey(DefKey, Path, ByRef objOutFile)
    Dim Keys, Names, types, i, j, sFullPath, value
    objOutFile.WriteLine("[" & strRegPath(DefKey, Path) & "]")
    reg.EnumValues DefKey, Path, Names, Types
    If Not IsNull(Names) And Not IsNull(Types) Then
        On Error Resume Next
        For i = lbound(types) To ubound(types)
            sFullPath = strRegPath(DefKey, Path) & "\" & names(i)
            select case types(i)
                case 1 ' REG_SZ
                    reg.GetStringValue defkey, path, names(i), value
                    If Not IsNull(names(i)) Or Not IsNull(value) Then
                        If Len(names(i)) > 0 Then
                            objOutFile.WriteLine Quote(names(i)) & "=" & Quote(value)
                        Else
                            objOutFile.WriteLine "@=" & Quote(value)
                        End If
                    End If
                case 2 ' REG_EXPAND_SZ
                    reg.GetExpandedStringValue defkey, path, names(i), value
                    if not isnull(names(i)) or not isnull(value) then
                        objOutFile.WriteLine Quote(names(i)) & "=expand:" & Quote(value)
                    end if
                case 3 ' REG_BINARY
                    reg.GetBinaryValue defkey, path, names(i), value
                    for j = lbound(value) to ubound(value)
                        value(j) = hex(cint(value(j)))
                    next
                    if not isnull(names(i)) or not isnull(value) then
                        objOutFile.WriteLine Quote(names(i)) &"=hex:"& _
                                          join(value, ",")
                    end if
                case 4 '  REG_DWORD
                    reg.GetDWordValue defkey, path, names(i), value
                    if not isnull(names(i)) or value then
                        objOutFile.WriteLine Quote(names(i)) & "=dword:" & _
                                          hex(value)
                    end if
                case 7 ' REG_MULTI_SZ
                    reg.GetMultiStringValue defkey, path, names(i), value
                    If Not IsNull(names(i)) And IsArray(value) Then
                        Dim inValue
                        objOutFile.WriteLine Quote(names(i)) & "=list:"
                        For Each inValue In value
                            objOutFile.WriteLine "    " & Quote(inValue)
                        Next
                    End If
                case else
                    Log LOG_TRACE, "EnumerateKey", "Registry value " & Quote(sFullPath) _
                        & " has type " & types(i)
            end select
        CatchErr LOG_DEBUG, "EnumerateKey", "Could not read " & Quote(sFullPath)
        Next
        On Error GoTo 0
    end if

    objOutFile.WriteLine
    reg.EnumKey DefKey, Path, Keys

    Dim SubKey, NewPath
    If Not IsNull(Keys) Then
        For Each SubKey In Keys
            NewPath = Path & "\" & SubKey
            EnumerateKey DefKey, NewPath, objOutFile
        Next
    End if
End Sub

Sub GatherEventLogs
    Dim strEvLogDir
    Log LOG_INFO, "GatherEventLogs", "Gathering event logs..."
    strEvLogDir = workdir & "\" & strShortName & "-event-logs\"
    If Not fso.FolderExists(strEvLogDir) Then
        fso.CreateFolder(strEvLogDir)
    End If
    CopyEventLog "Application", strEvLogDir
    CopyEventLog "System", strEvLogDir
    CopyEventLog "Security", strEvLogDir
    If boolIsCServer Then CopyEventLog "ADAM (VMwareVDMDS)", strEvLogDir
End Sub

Sub CopyEventLog(logname, directory)
    Dim logfileset, logfileobj, objWMIServ, strFileBase
    Log LOG_INFO, "CopyEventLogs", "  Gathering " & Quote(logname) & "..."
    Set objWMIServ = GetObject("winmgmts:{impersonationLevel=impersonate,(Backup,Security)}!\\.\root\cimv2")
    On Error Resume Next
    Set logfileset = objWMIServ.ExecQuery("select * from Win32_NTEventLogFile where LogfileName='" & logname & "'")
    If CatchErr(LOG_DEBUG, "CopyEventLog", "Unable to access event log") Then Exit Sub
    On Error GoTo 0

    If logfileset.Count <> 1 Then
        Log LOG_DEBUG, "CopyEventLog", "Unable to get a single log object for " & Quote(logname)
        Exit Sub
    End If

    For Each logfileobj in logfileset
        On Error Resume Next
        logfileobj.BackupEventLog(directory & "\" & logname & "-log.evt")
        CatchErr LOG_DEBUG, "CopyEventLog", "Unable to backup event log"
        On Error GoTo 0
    Next

    Set logfileset = Nothing
    strFileBase = directory & "\" & logname & "-log"
    ExportEventLog objWMIServ, logname, strFileBase & ".csv"
    Exit Sub
End Sub

Sub ExportEventLog(ByRef objWMIServ, strLogName, strFile)
    Dim intNumRecords, colEvts, objEvt, objFile, strQuery, strLine, strTime

    Log LOG_TRACE, "ExportEventLog", "Starting for log " & Quote(strLogName)

    ' Get the collection of event log entries
    strQuery = "select * from Win32_NTLogEvent where Logfile='" & strLogName & "'"
    On Error Resume Next
    Set colEvts = objWMIServ.ExecQuery(strQuery)
    CatchErr LOG_DEBUG, "ExportEventLog", "Unable to access events in log " & Quote(logname)
    On Error GoTo 0

    ' open output files
    If Not OutFile(strFile, objFile) Then
        Log LOG_DEBUG, "ExportEventLog", "Cannot create output file(s) for " _
            & Quote(strLogName) & ", skipping."
        Exit Sub
    End If

    ' header
    strLine = Q("Type") & "," & Q("Event") & "," _
        & Q("Date Time") & "," & Q("Source") & "," & Q("Category") _
        & "," & Q("User") & "," & Q("Description")

    objFile.WriteLine strLine

    On Error Resume Next
    For Each objEvt In colEvts
        strQuery = objEvt.TimeWritten
        If Not IsNull(strQuery) Then
            strTime = Mid(strQuery,5,2) & "/"  & Mid(strQuery,7,2) & "/" & Mid(strQuery,1,4) _
                & " " & Mid(strQuery,9,2) & ":" & Mid(strQuery,11,2) & ":" & Mid(strQuery,13,2)
        Else
            strTime = ""
        End If

        ' clean up the message, put into a single line and trim
        strQuery = objEvt.Message
        If Not IsNull(strQuery) Then strQuery = Trim(Replace(strQuery, VbCrLf, " "))

        strLine = Q(objEvt.Type) & "," _
                & Q(objEvt.EventCode) & "," _
                & Q(strTime) & "," _
                & Q(objEvt.SourceName) & "," _
                & Q(objEvt.Category) & "," _
                & Q(objEvt.User) & "," _
                & Q(strQuery)
        CatchErr LOG_DEBUG, "ExportEventLog", "Error constructing strLine"

        objFile.WriteLine strLine
        CatchErr LOG_DEBUG, "ExportEventLog", "Unable to write event line to file"

    Next
    On Error GoTo 0

    Set colEvts = Nothing
    objFile.Close
End Sub

Sub GatherLogs
    Dim strInstLogLoc, strLogDir, strLogDirCU, strInstLogDir, strVDMLogs
    Dim intNumLogs, strWinDir
    Const strLogPattern = "^(?!Copy of)"
    Const strInstPattern = "^vm(inst|msi).*log"
    Const strSysTempPattern = "^vmware-.*"

    Log LOG_INFO, "GatherLogs", "Gathering " & prodName & " logs..."

    strLogDir = workdir & "\" & strShortName & "-logs\"
    strInstLogDir = workdir & "\" & strShortName & "-inst-logs\"
    strVDMLogs = GetLogDirectory()
    strWinDir = AppShell.Namespace(WINDOWS).Self.Path
    Log LOG_TRACE, "GatherLogs", "Log file location is " & Quote(strVDMLogs)

    If Not fso.FolderExists(strLogDir) Then fso.CreateFolder(strLogDir)
    If Not fso.FolderExists(strInstLogDir) Then fso.CreateFolder(strInstLogDir)

    ' Get the common logs
    Log LOG_INFO, "GatherLogs", "  Retrieving runtime logs..."
    intNumLogs = StageFiles(strVDMLogs, strLogPattern, strLogDir)
    intNumLogs = intNumLogs + StageFiles(ProcessTempDir(), "^vmware-view.exe.*", strLogDir)
    intNumLogs = intNumLogs + StageFiles(strVDMLogs, "^vmware-horizon-viewclient-.*", strLogDir)

    ' Get logs from system temp (includes Arbitrator)
    intNumLogs = intNumLogs + MultiDirStageFiles(SysTempDir(), "^vmware-.*", strSysTempPattern, strLogDir)

    ' We also want to get all the client logs from any other users if at all possible
    ' On the Agent side, VMwareViewClipboard.exe logs into the current user's directory.
    If boolIsClient Or boolIsAgent Then intNumLogs = intNumLogs + GatherUserLogs(strLogDir, strLogPattern)

    If boolIsServer Then Call GatherServerLogs(strLogDir)

   If boolIsAgent Then Call GatherSysprepLogs(strLogDir)

    If intNumLogs = 0 Then
        Log LOG_WARN, "GatherLogs", "    Could not find any runtime logs, has " & prodName & " been run yet?"
    Else
        Log LOG_DEBUG, "GatherLogs", "    Copied a total of " & intNumLogs & " log files"
    End If

    ' Get the installation logs.
    ' Inst logs written to %TEMP% as set when installing. Installer saves that in the reg for us
    Log LOG_INFO, "GatherLogs", "  Retrieving installation logs..."
    strInstLogLoc = ProcessTempDir()
    If IsNull(strInstLogLoc) Then
        Log LOG_DEBUG, "GatherLogs", "Installation log directory not specified"
    ElseIf Not FSO.FolderExists(strInstLogLoc) Then
        Log LOG_DEBUG, "GatherLogs", "Installation log directory " & Quote(strInstLogLoc) & " is not valid"
    Else
        If StageFiles(strInstLogLoc, strInstPattern, strInstLogDir) = 0 Then
            Log LOG_WARN, "GatherLogs", "    Could not find any installation logs, these may have been cleared"
        End If
    End If
   StageFiles strWinDir, "^setupapi.*\.log", strInstLogDir
   StageFiles strWinDir & "\inf", "^setupapi.*\.log", strInstLogDir
End Sub


Sub GatherServerLogs(strLogDir)
   ' Include Java HotSpot error logs, if any
   Dim strVdmCommonDump
   strVdmCommonDump = AppShell.Namespace(COMMON_APPDATA).Self.Path & "\VMware\VDM\Dumps\"
   StageFiles strVdmCommonDump, "^hs_err_pid", strLogDir & "hotspot\"

   ' If PSG component is installed, grab logs from location specified in reg
   Dim strVdmLogsPSG
   strVdmLogsPSG = ReadRegString("HKLM\SOFTWARE\Teradici\SecurityGateway\LogPath")
   If IsNull(strVdmLogsPSG) Then
      Log LOG_DEBUG, "GatherServerLogs", "No PSG log path specified"
   Else
      StageFiles strVdmLogsPSG, "\.log$", strLogDir & "psg\"
   End If

   ' We want additional logs on the CS
   If boolIsCServer Then Call GatherCSLogs(strLogDir)

   ' We want additional logs on the TS
   If boolIsTServer Then Call GatherTSLogs(strLogDir)
End Sub


Sub GatherCSLogs(strLogDir)
   ' Get the JMS logs (*.log, rotated to *.log.X)
   StageFiles installpath & "messagebus\log\", "\.log", strLogDir & "messagebus\"
   ' Get the tomcat logs
   StageFiles installpath & "broker\logs\", "\.log$", strLogDir & "broker\"
End Sub


Sub GatherTSLogs(strLogDir)
   ' Get the apache logs
   StageFiles installpath & "httpd\logs\", "\.log$", strLogDir & "httpd\"
End Sub


Function GatherUserLogs(strLogDir, strLogPattern)
    Dim strUser, strResult, strCULogs, strCULogsDest
    Dim strAllUsersFolder, objProfilesFolder, objSubfolder, colUserFolders

    ' count the log files copied
    Dim intNumLogs : intNumLogs = 0

    ' Check to see if current user has any log files (may end up with dups)
    strCULogs = AppShell.Namespace(LOCALAPPDATA).Self.Path & "\VMware\VDM\logs\"
    If FSO.FolderExists(strCULogs) And UsingDefaultLogDirectory() Then
        strCULogsDest = strLogDir & "CurrentUser\"
        If Not fso.FolderExists(strCULogsDest) Then fso.CreateFolder(strCULogsDest)
        Log LOG_TRACE, "GatherLogs", "  Retrieving current user logs from " & strCULogs & "..."
        intNumLogs = intNumLogs + StageFiles(strCULogs, strLogPattern, strCULogsDest)
    End If

    ' Get all subfolders of the documents and settings folder, or the custom log folder
    If(UsingDefaultLogDirectory()) Then
        'since Windows Vista and later have different directory structures for all users profile
        If(boolIsVistaOrHigher) Then
            strAllUsersFolder = WShell.ExpandEnvironmentStrings("%USERPROFILE%")
        Else
            strAllUsersFolder = WShell.ExpandEnvironmentStrings("%ALLUSERSPROFILE%")
        End If
        Set objProfilesFolder = FSO.GetFolder(strAllUsersFolder).ParentFolder
        Set colUserFolders = objProfilesFolder.Subfolders
    Else
        Set objProfilesFolder = FSO.GetFolder(GetLogDirectory())
        Set colUserFolders = objProfilesFolder.Subfolders
    End If
    ' iterate through any users
    For Each objSubfolder in colUserFolders
        strUser = objSubfolder.Name
        Log LOG_TRACE, "GatherUserLogs", "Looking for View logs for user " & strUser
        strResult = ""
        ' We may not have permissions, allow this to fail
        On Error Resume Next
        strResult = GetUserLogDirectory(strUser,FindFolder(objSubFolder, "VMware\VDM\logs"))
        On Error GoTo 0
        If Len(strResult) > 0 Then
            Log LOG_DEBUG, "GatherUserLogs", "User " & strUser & " has logs at " & strResult
            intNumLogs = intNumLogs + StageFiles(strResult, strLogPattern, strLogDir & strUser)
        End If
    Next
    GatherUserLogs = intNumLogs
End Function


Sub GatherVMwareDumps()
   Dim strStageDir : strStageDir = WorkDir & "\vmware-dumps"

   ' Collect WinCDK dumps
   Const strWinCDKDump = "^VMwareHorizonViewClient.*\.dmp$"
   Dim strAppPath, strLocalAppPath, count
   strAppPath = AppShell.Namespace(APPDATA).Self.Path & "\VMware\VMware Horizon View Client"
   strLocalAppPath = AppShell.Namespace(LOCALAPPDATA).Self.Path & "\VMware"

   count = CountMatchingFiles(strAppPath, strWinCDKDump)
   count = count + CountMatchingFiles(strLocalAppPath, strWinCDKDump)

   ' Collect dumps from anything writing to VMware\VDM\Dumps (hprof files from
   ' Java processes, dumps triggered by the View framework)
   Const strVdmDumps = "\.(dmp|hprof)$"
   Dim strVdmCommonDump, strVdmUsrLocalDump
   strVdmCommonDump = AppShell.Namespace(COMMON_APPDATA).Self.Path & "\VMware\VDM\Dumps"
   strVdmUsrLocalDump = AppShell.Namespace(LOCALAPPDATA).Self.Path & "\VMware\VDM\Dumps"

   count = count + CountMatchingFiles(strVdmCommonDump, strVdmDumps)
   count = count + CountMatchingFiles(strVdmUsrLocalDump, strVdmDumps)

   If count = 0 Then
      Log LOG_DEBUG, "GatherVMwareDumps", "No VMware dump files found"
      Exit Sub
   End If

   Log LOG_DEBUG, "GatherVMwareDumps", "One or more VMware dump found, prompt for copy"
   WScript.StdOut.WriteLine "** Found one or more VMware dump files. These can be used to diagnose crashes,"
   WScript.StdOut.WriteLine "** but can be very large."
   If Not PromptYesNo("Copy files? ", True) Then Exit Sub

   If Not FSO.FolderExists(strStageDir) Then FSO.CreateFolder(strStageDir)

   StageFiles strAppPath, strWinCDKDump, strStageDir
   StageFiles strLocalAppPath, strWinCDKDump, strStageDir
   StageFiles strVdmCommonDump, strVdmDumps, strStageDir
   StageFiles strVdmUsrLocalDump, strVdmDumps, strStageDir
End Sub


Function CountMatchingFiles(strDir, strPattern)
   Dim oFile, oFiles, count
   CountMatchingFiles = 0

   If Not FSO.FolderExists(strDir) Then
      Exit Function
   End If

   On Error Resume Next
   Set oFiles = FSO.GetFolder(strDir).Files
   If CatchErr(LOG_DEBUG, "CountMatchingFiles", "Unable to get file list from " & Quote(strDir)) Then
      Exit Function
   End If
   On Error GoTo 0

   If oFiles.Count = 0 Then
      Log LOG_DEBUG, "CountMatchingFiles", "No files in " & Quote(strDir)
      Exit Function
    End If

   count = 0
   For Each oFile In oFiles
      If RegExpMatch(oFile.Name, strPattern, True) Then
         count = count + 1
      End If
   Next
   CountMatchingFiles = count
End Function


Function GatherPCoIPLogs()
   Dim strLogDir : strLogDir = workdir & "\pcoip-logs"
   Const ProfileListReg = "SOFTWARE\Microsoft\Windows NT\CurrentVersion\ProfileList"
   Const SrvPattern = "^pcoip_.*\.txt$"
   Const CliPattern = "^pcoip_client.*\.txt$"
   Const DmpPattern = "^(pcoip_client|tera_miniDump)_.*\.dmp$"

   ' Vmw logs can occur on both the client and the agent but are in a location
   ' normally used by the client in both cases.  For this reason the agent has
   ' to run through the code that checks the user folders for these logs.
   ' These logs currently consist of:
   '   vmware-mks-*.log
   '   vmware-crtbora-*.log
   '   vmware-vvaClient-*.log
   '   vmware-rdeSvc-*.log
   '   vmware-ViewMP-Client-*.log
   Const VmwPattern = "^vmware-.*\.log$"
   Const VmMksPattern = "^vmware-([0-9]*(-)?mks|crtbora|vvaClient|rdeSvc|vmrc-[0-9]+)-.*\.log$"
   Const ViewMPClientPattern = "^ViewMP-Client.*\.log$"

   If Not FSO.FolderExists(strLogDir) Then FSO.CreateFolder(strLogDir)

   Log LOG_INFO, "GatherPCoIPLogs", "Gathering PCoIP dump files..."
   StageFiles PcoipLogDir(), DmpPattern, strLogDir

   If boolIsAgent Then
      Log LOG_INFO, "GatherPCoIPLogs", "Gathering PCoIP server logs..."
      Dim strVDMLogs
      strVDMLogs = AppShell.Namespace(COMMON_APPDATA).Self.Path & "\VMware\VDM\logs\"
      StageFiles SysTempDir(), SrvPattern, strLogDir
      StageFiles strVDMLogs, SrvPattern, strLogDir
   End If

   ' Do the current user first, as this is simple
   Dim userName, vmwareTmpDir, aKeys, regKey, regPath, strProfile, pcoipDir
   userName = WShell.Environment("PROCESS").Item("USERNAME")
   vmwareTmpDir = ProcessTempDir() & "\vmware-" & userName
   pcoipDir = PcoipLogDir()

   If boolIsClient Then
       Log LOG_INFO, "GatherPCoIPLogs", "Gathering PCoIP client logs..."
	   StageFiles pcoipDir, CliPattern, strLogDir & "\CurrentUser"
   End If

   ' VMware logs files exist for both the agent and client
   StageFiles vmwareTmpDir, VmwPattern, strLogDir & "\CurrentUser"

   ' Collect Flash URL Redirection logs
   StageFiles vmwareTmpDir, ViewMPClientPattern, strLogDir & "\CurrentUser"

   ' Collect VMware mks logs
   Dim folder, subFolders, subFolder
   Set folder = fso.GetFolder(ProcessTempDir())
   Set subFolders = folder.SubFolders
   For Each subFolder In subFolders
      If RegExpMatch(subFolder.Name, "^vmware-" & username & "-[0-9]+", True) Then
         StageFiles subFolder.Path, VmMksPattern, strLogDir & "\CurrentUser"
         StageFiles subFolder.Path, VmMksPattern, strLogDir & "\" & userName
      End If
   Next

      ' now try and iterate through all profiles, best effort only
      Reg.EnumKey HKLM, ProfileListReg, aKeys

      For Each regKey In aKeys
         regPath = ProfileListReg & "\" & regKey
         Reg.GetExpandedStringValue HKLM, regPath, "ProfileImagePath", strProfile

         If VarType(strProfile) <> vbString Then
            ' Unable to get a valid string value
            Log LOG_DEBUG, "GatherPCoIPLogs", "Unable to read ProfileImagePath from " & regPath
            strProfile = ""
         End If

         If Len(strProfile) > 0 And FSO.FolderExists(strProfile) Then

        Log LOG_TRACE, "GatherPCoIPLogs", "Finding PCoIP logs under " & strProfile & "..."
            ' We don't know the temp folder location, but we can guess at the
            ' folder name.
            userName = FSO.GetFileName(strProfile)
            vmwareTmpDir = strProfile & "\Local Settings\Temp\vmware-" & userName
            If Not FSO.FolderExists(vmwareTmpDir) Then
               vmwareTmpDir = ""
               ' likely to hit permission problems
               On Error Resume Next
               vmwareTmpDir = FindFolder(FSO.GetFolder(strProfile), "vmware-" & userName)
               On Error GoTo 0
            End If

            If Len(vmwareTmpDir) > 0 And FSO.FolderExists(vmwareTmpDir) Then
               Log LOG_DEBUG, "GatherPCoIPLogs", "Found folder at " & vmwareTmpDir & "..."
           If boolIsClient Then StageFiles pcoipDir, CliPattern, strLogDir & "\" & userName

           ' VMware logs files exist for both the agent and client
           StageFiles vmwareTmpDir, VmwPattern, strLogDir & "\" & userName

           ' Collect Flash URL Redirection logs
           StageFiles vmwareTmpDir, ViewMPClientPattern, strLogDir & "\" & userName
            End If
         End If
      Next
End Function

Function FindFolder(objSearchFolder, strFolderLookFor)
    Dim strResult, objSubfolder
    strResult = objSearchFolder.Path & "\" & strFolderLookFor
    If FSO.FolderExists(strResult) Then
        FindFolder = strResult
        Exit Function
    Else
        FindFolder = ""
    End If
    For Each objSubfolder In objSearchFolder.Subfolders
        strResult = FindFolder(objSubfolder, strFolderLookFor)
        If Len(strResult) > 0 Then
            FindFolder = strResult
            Exit Function
        End If
    Next
End Function

' Copy all matching files in matching sub directories below srcdir
Function MultiDirStageFiles(srcdir, dirMatch, strMatch, destdir)
   Dim folder, subFolders, subFolder, testCount
   MultiDirStageFiles = 0

   If Not fso.FolderExists(srcdir) Then
      Log LOG_INFO, "MultiDirStageFiles", "Directory " & Quote(srcdir) & " does not exist. Skipping"
      Exit Function
   End If

   ' StageFiles() only creates one missing level folder so create the parent folder here where missing
   If Not fso.FolderExists(destdir) Then fso.CreateFolder(destdir)

   On Error Resume Next
   Set folder = fso.GetFolder(srcdir)
   If CatchErr(LOG_DEBUG, "MultiDirStageFiles", "Unable to get folder " & Quote(srcdir)) Then
      Exit Function
   End If

   Set subFolders = folder.SubFolders
   If CatchErr(LOG_DEBUG, "MultiDirStageFiles", "Unable to get subfolders " & Quote(srcdir)) Then
      Exit Function
   End If

   testCount = subFolders.Count
   If CatchErr(LOG_DEBUG, "MultiDirStageFiles", "Unable to get folder count from " & Quote(srcdir)) Then
      Exit Function
   End If

   If testCount = 0 Then
      Log LOG_DEBUG, "MultiDirStageFiles", "No subfolders found in " & Quote(srcdir) &". Skipping"
      Exit Function
   End If
   On Error GoTo 0

   For Each subFolder In subFolders
      If RegExpMatch(subFolder.Name,dirMatch,True) Then
         MultiDirStageFiles = MultiDirStageFiles + StageFiles(subFolder.Path,strMatch,destdir & "\" & subFolder.Name)
      End If
   Next
End Function

' Copy all matching files in srcdir to destdir
Function StageFiles(srcdir, strMatch, destdir)
    Dim count, file, files, folder, testCount
    StageFiles = 0

    If Not fso.FolderExists(srcdir) Then
        Log LOG_DEBUG, "StageFiles", "Directory " & Quote(srcdir) & " does not exist. Skipping"
        Exit Function
    End If

   On Error Resume Next
   Set folder = fso.GetFolder(srcdir)
   If CatchErr(LOG_DEBUG, "StageFiles", "Unable to get folder from " & Quote(srcdir)) Then
      Exit Function
   End If

   Set files = folder.Files
   If CatchErr(LOG_DEBUG, "StageFiles", "Unable to get file list from " & Quote(srcdir)) Then
      Exit Function
   End If

   testCount = files.Count
   If CatchErr(LOG_DEBUG, "StageFiles", "Unable to get file count from " & Quote(srcdir)) Then
      Exit Function
   End If

   If testCount = 0 Then
      Log LOG_DEBUG, "StageFiles", "Nothing found in " & Quote(srcdir) &". Skipping"
      Exit Function
   End If
   On Error GoTo 0

    If Not fso.FolderExists(destdir) Then fso.CreateFolder(destdir)

    count = 0
    For Each file In files
        If RegExpMatch(file.Name,strMatch,True) Then
            If CopyFile(file.Path, destdir) Then count = count + 1
        End If
    Next
    Log LOG_DEBUG, "StageFiles", "Copied from " & Quote(srcdir) & ", pattern " & Quote(strMatch) & ": " & count
    StageFiles = count
End Function


Sub copyFolderRecursively( strSrcPath, strDestPath )
    Dim objCurFolder
    Dim curFolder
    Dim curFile
    Dim fileExists

    Dim strCutDest, strCurDest, strCurSrc

    '// Get Source Folder | Create the Root Folder
    If Not FSO.FolderExists(strSrcPath) Then
        Log LOG_DEBUG, "copyFolderRecursively", "Directory " & Quote(strSrcPath) & " does not exist. Skipping"
        Exit sub
    End If

    If FSO.FolderExists( strDestPath ) = False Then FSO.CreateFolder strDestPath

    '// Copy folders
    On Error Resume Next
    Set objCurFolder = FSO.GetFolder( strSrcPath )
    If CatchErr(LOG_DEBUG, "copyFolderRecursively", "Unable to get folder list from " & Quote(strSrcPath)) Then
      Exit sub
    End If

    If objCurFolder.Subfolders.Count = 0 Then
        Log LOG_DEBUG, "copyFolderRecursively", "Nothing found in " & Quote(strSrcPath) &". Skipping"
        Exit sub
    End If

    For Each curFolder in objCurFolder.Subfolders
        strCurSrc    = strSrcPath & "\" & curFolder.Name
        strCutDest    = strDestPath & "\" & curFolder.Name
        CopyFolder strCurSrc, strCutDest
    Next

    '// Copy files
    If objCurFolder.Files.Count <= 0 Then
        Log LOG_DEBUG, "copyFolderRecursively", "Nothing found in " & Quote(strSrcPath) &". Skipping"
        Exit sub
    End If

    For Each curFile in objCurFolder.Files
        strCurSrc    = strSrcPath & "/" & curFile.Name
        strCurDest    = strDestPath & "/" & curFile.Name
        CopyFile strCurSrc, strCurDest
    Next

End Sub

Sub Log(intLogLevel, strCallingFunc, msg)
    Dim strLevel
    Select Case intLogLevel
        Case LOG_TRACE : strLevel = "TRACE"
        Case LOG_DEBUG : strLevel = "DEBUG"
        Case LOG_INFO  : strLevel = "INFO "
        Case LOG_WARN  : strLevel = "WARN "
        Case LOG_ERROR : strLevel = "ERROR"
        Case Else : strLevel = "UNKNOWN"
    End Select

   If Not BoolSilentMode And (intLogLevel >= LOG_SHOW Or intLogLevel = LOG_DISPLAY) Then
      wscript.stdout.WriteLine msg
   End If

   If Not LogOk Then
      ' 7-zip 9.20 doesn't always correctly archive the log files while open
      Exit Sub
   End If

    If IsNull(LogFile) Then Set LogFile = fso.CreateTextFile(workdir & "\" & prodFolderName & ".log", True, True)
    If IsNull(LogFileFull) Then Set LogFileFull = fso.CreateTextFile(workdir & "\" & prodFolderName & "-full.log", True, True)

    LogFileFull.WriteLine Now & " " & strLevel & " [" & strCallingFunc & "] " & msg
    If intLogLevel >= LOG_FILE Then LogFile.WriteLine Now & " " & strLevel & " [" & strCallingFunc & "] " & msg
End Sub

' Determine if we are client, server, or agent. Error if more than one
Sub CheckRoles
    Dim intNumRoles : intNumRoles = 0
    Log LOG_INFO, "CheckRoles", "Checking the " & prodName & " role this computer plays by examining installed MSIs..."
    CheckRole2
    If Not (boolIsClient Or boolIsServer Or boolIsAgent) Then CheckRole32On64

    If boolIsClient Then intNumRoles = intNumRoles + 1
    If boolIsServer Then intNumRoles = intNumRoles + 1
    If boolIsAgent Then intNumRoles = intNumRoles + 1
    If intNumRoles < 1 Then
        Log LOG_ERROR, "CheckRoles", "This computer does not have a " & prodName & " role. Exiting."
        wscript.quit 3
    ElseIf intNumRoles > 1 Then
        Log LOG_ERROR, "CheckRoles", "This computer has more than one " & prodName & " role, which is unsupported."
    End If
End Sub

Sub CheckRole2
    Dim sBaseKey, arSubKeys, iRC, sKey, sValue
    sBaseKey = strRegSoft("") & "Microsoft\Windows\CurrentVersion\Uninstall\"
    Log LOG_TRACE, "CheckRole2", "Checking under registry key " & Quote(sBaseKey)
    iRC = reg.EnumKey(HKLM, sBaseKey, arSubKeys)
    If Not IsArray(arSubKeys) Then Exit Sub
    For Each sKey In arSubKeys
        sValue = ReadRegString("HKLM\" & sBaseKey & sKey & "\DisplayName")
        If IsNull(sValue) Then
            sValue = ReadRegString("HKLM\" & sBaseKey & sKey & "\QuietDisplayName")
        End If

        If sValue = strClientMSICaption Then
            boolIsClient = True
            Log LOG_INFO, "CheckRole2", "This computer has '" & strClientMSICaption & "' installed"
            Exit For
        End If
    Next

    boolIsCServer = False
    boolIsTServer = False
    ' Do an additional check to differentiate connection server and security server
    If boolIsServer Then
        iRC = reg.GetStringValue(HKLM, strRegProduct("") & "Plugins\wsnm\tomcatService", "Filename", sValue)
        If iRC = 0 And Len(sValue) > 0 Then
            Log LOG_INFO, "CheckRole2", "This computer has '" & strServerMSICaption & "' installed"
            boolIsCServer = True
        Else
            iRC = reg.GetStringValue(HKLM, strRegProduct("") & "Plugins\wsnm\TransferControlService", "Filename", sValue)
            If iRC = 0 And Len(sValue) > 0 Then
                Log LOG_INFO, "CheckRole2", "This computer has '" & strTSAltCaption & "' installed"
                boolIsTServer = True
            Else
                Log LOG_INFO, "CheckRole2", "This computer has '" & strSSAltCaption & "' installed"
            End If
        End If
    End If
End Sub

Sub CheckRole32On64
    Log LOG_DEBUG, "CheckRole32On64", "Looking for 32-bit software on 64-bit OS"
    boolIs32On64 = True
    CheckRole2
End Sub

' Get client version
Sub GetVersion
    Dim objShell, objFolder, objFolderItem, i
    Set objShell = CreateObject("Shell.Application")
    Set objFolder = objShell.Namespace(ClientDir)
    Set objFolderItem = objFolder.ParseName("vmware-view.exe")

    ' File system folders can support a number of additional fields.
    ' The column indexes assigned to these fields might vary
    Dim arrHeaders(300)
    For i = 0 To 300
        arrHeaders(i) = objFolder.GetDetailsOf(objFolder.Items, i)
        If lcase(arrHeaders(i))= "product version" Then
            Version = objFolder.GetDetailsOf(objFolderItem, i)
            Exit For
        End If
    Next
    Log LOG_TRACE, "GetVersion", "Get client version " & Version
End Sub

' Appends all files under the relative path to the zipFile specified
' The files retain their relative locations in the zip file
Function Zip(location, relpath, zipFile)
   Dim file, sf, folderobj, fileList, zipExec, intZip, strOut
   Set folderobj = fso.GetFolder(location & "\" & relpath)
   Zip = 0

   Set zipExec = WShell.Exec(zipCmd & " " & zipOpts & " " & Quote(zipFile) & " " & relpath)
   strOut = ""
   Do While zipExec.Status = 0
      ' sometimes zip hangs if we don't collect its stdout
      Do Until zipExec.stdout.AtEndOfStream
         strOut = strOut & zipExec.stdout.ReadLine
      Loop
      wscript.stdout.Write "."
      wscript.sleep 2000 ' sleep for 2 seconds before checking again
   Loop
   wscript.stdout.WriteLine "."
   If zipExec.ExitCode <> 0 Then
      wscript.stderr.WriteLine strOut
      Do Until zipExec.stderr.AtEndOfStream
         wscript.stderr.WriteLine zipExec.stderr.ReadLine
      Loop
      wscript.stderr.WriteLine "zip.exe exited with status " & zipExec.ExitCode
      Zip = 1
   End If
End Function


Function ZipIt(tdir, folder)
   Dim filelist, zipFile, oldcwd, zipReturnCode, strHostName
   RunCmd "%comspec% /c tree /F /A " & Quote(workdir), workdir & "\" & prodFolderName & "-contents.txt"
   Log LOG_INFO, "ZipIt", "Creating zip archive..."
   'getting host name
   strHostName = LCase(WShell.ExpandEnvironmentStrings("%COMPUTERNAME%"))

   If Not CheckAdmin() Then
      Log LOG_WARN, "Main", "Incomplete support bundle. Please run again as a local administrator."
   End If
   LogOk = False
   LogFile.Close
   LogFileFull.Close

   If IsNull(StrUsrZipFile) Then
      zipFile = folder
      'prepend host-name to the filename of DCT output
      zipFile = strHostName & "-" & zipFile
      Select Case True
            case boolIsClient : zipFile = zipFile & "-client"
         case boolIsServer : zipFile = zipFile & "-server"
         case boolIsAgent : zipFile = zipFile & "-agent"
      End Select
      zipFile = zipFile & ".zip"
   Else
      zipFile = StrUsrZipFile
   End If

    ' Zip function relies on Exec, run a simple command instead on older wsh
    If Not TestExecSupport() Then
        OldRunCmd "pushd " & Quote(tdir) & " && " & Quote(zipCmd) & " " & zipOpts & " " & Quote(zipFile) & " " _
                & Quote(folder) & " && popd", ""
        If FSO.FileExists(tdir & "\" & zipFile) Then ZipIt = zipFile
        Exit Function
    Else
        oldcwd = wshell.CurrentDirectory
        wshell.CurrentDirectory = tdir & "\"

        zipReturnCode = Zip(tdir, folder, zipFile)
        If zipReturnCode <> 0 Then
            wscript.stdout.WriteLine "Zip() returned " & zipReturnCode
            ZipIt = ""
        Else
            ZipIt = zipFile
        End If
    End If

    wshell.CurrentDirectory = oldcwd & "\"
End Function

Function Quote(strIn)
    Quote = Q(SafeStr(strIn))
End Function

Function SafeStr(strIn)
   Dim i, s
   For i=1 to Len(strIn)
      s = s & Chr(Asc(Mid(strIn, i, 1)))
   Next
   SafeStr = s
End Function

Function Q(strIn)
    If IsNull(strIn) Or IsObject(strIn) Or IsEmpty(strIn) Then
        Q = Chr(34) & Chr(34)
    Else
        Q = Chr(34) & strIn & Chr(34)
    End If
End Function

' Run a command and save the output to a file
Function RunCmd(cmd, strOutFile)
    Dim f1, run, output, FileName
    run = Null
    Log LOG_TRACE, "RunCmd", "Run command: " & cmd
    Log LOG_TRACE, "RunCmd", "  Output to " & strOutFile

    If Not TestExecSupport() Then
        RunCmd = OldRunCmd(cmd,strOutFile)
        Exit Function
    End If

    on error resume next
    set run = wshell.Exec(cmd)
    if IsNull(run) then
       Log LOG_DEBUG, "RunCmd", "Failed to execute: " & cmd & ": " & Err.Description
       on error goto 0
       Exit Function
    end if
    on error goto 0

    output = ""
    do while run.status = 0
        output = output & run.stdout.readall
    loop
    output = output & run.stdout.readall
    If Len(output) = 0 Then
        Log LOG_DEBUG, "RunCmd", "No output from command, reading STDERR as result."
        output = "STDERR: " & run.stderr.readall
    End If

    ' save contents to file and return
    if strOutFile <> "" Then
        WriteFile strOutFile, output
    End If

    RunCmd = output
End Function

' Win2K does not support WShell.Exec, so do an old WShell.Run
' note that we can't detect much, we should wait for the command then load the contents of the output file and return it
Function OldRunCmd(strCmd, strOutFile)
    Dim intRun, f1
    ' Do the old run
    On Error Resume Next
    ' redirection only works within cmd
    intRun = WShell.Run("%COMSPEC% /c " & strCmd & " >" & Quote(strOutFile), , True)
    CatchErr LOG_DEBUG, "OldRunCmd", "Command " & Quote(strCmd) & " returned " & intRun & "."
    On Error GoTo 0

    If Not FSO.FileExists(strOutFile) Then
        Log LOG_DEBUG, "OldRunCmd", "Output file " & Quote(strOutFile) & " not created, returning nothing."
        OldRunCmd = ""
        Exit Function
    End If

    Set f1 = fso.OpenTextFile(strOutFile, ForReading)
    On Error Resume Next
    OldRunCmd = f1.ReadAll
    On Error GoTo 0
    f1.Close
End Function

Class URL
    Private strServer, intPort, isSSL, isValid, strErr, strPath

    Function SetFromString(inStr)
        Dim objRegExp, objMatches, strProto, a, b

        Set objRegExp = New RegExp
        With objRegExp
            .Pattern = "^([a-z0-9]+)://([^:/]+)(:([0-9]{1,5}))?(/[^ ]*)?"
            .IgnoreCase = True
            Set objMatches = objRegExp.Execute(Trim(inStr))
        End With

        If objMatches.Count = 0 Then
            strErr = "INVALID_FORMAT"
            isValid = False
            Exit Function
        End If

        ' Can't use submatches as not supported in 5.1
        a = Split(inStr, "://", 2)
        strProto = a(0)
        b = Split(a(1), "/", 2)
        If UBound(b) = 1 Then strPath = "/" & b(1)
        a = Split(b(0), ":" , 2)
        strServer = a(0)
        If UBound(a) = 1 Then
            If IsNumeric(a(1)) Then
                intPort = CLng(a(1))
                If intPort < MIN_PORT Or intPort > MAX_PORT Then
                    strErr = "INVALID_FORMAT"
                    isValid = False
                    Exit Function
                End If
            Else
                strErr = "INVALID_FORMAT"
                isValid = False
                Exit Function
            End If
        End If

        Select Case Lcase(strProto)
            Case "http" : isSSL = False
            Case "https" : isSSL = True
            Case Else
                strErr = "INVALID_PROTOCOL"
                isValid = False
                Exit Function
        End Select

        If Not ValidIPOrDNS(strServer) Then
            ' may be unicode hostname, try to resolve to IP
            a = ResolveHost(strServer)
            If IsNull(a) Then
                strErr = "INVALID_HOSTNAME"
                isValid = False
                Exit Function
            Else
                strServer = a
            End If
        End If

        If intPort = 0 Then intPort = TrueFalse(isSSL, HTTPS_PORT, HTTP_PORT)
        ' TODO: Validate URI portion too
        If strPath = "" Then strPath = "/"

        Log LOG_TRACE, "URL", "URL created - " & ToString()
        isValid = True
        SetFromString = True
    End Function

    Function Port
        Port = intPort
    End Function

    Function Proto
        Proto = TrueFalse(isSSL,"https","http")
    End Function

    Function Server
        Server = strServer
    End Function

    Function Err
        Err = strErr
    End Function

    Function setPath(strIn)
        strPath = TrueFalse(Left(strIn,1) = "/", strIn, "/" & strIn)
    End Function

    Function ToString
        ToString = TrueFalse(isSSL,"https","http") & "://" & strServer & ":" & intPort & strPath
    End Function
End Class

Function TrueFalse(boolIn,varTrue,varFalse)
    If boolIn Then
        TrueFalse = varTrue
    Else
        TrueFalse = varFalse
    End If
End Function

Function strRegSoft(strStart)
    strRegSoft = strStart & "SOFTWARE\" & TrueFalse(boolIs32On64,"Wow6432Node\","")
End Function

Function strRegPolicy(strStart)
    strRegPolicy = strRegSoft(strStart) & "Policies\" & companyName & "\" & prodRegKey & "\"
End Function

Function strRegProduct(strStart)
    strRegProduct = strRegSoft(strStart) & companyName & "\" & prodRegKey & "\"
End Function

Function CopyFile(strFileIn, strFileOut)
    On Error Resume Next
    Err.Clear

    If Not FSO.FileExists(strFileIn) Then
        Log LOG_TRACE, "CopyFile", "Cannot copy " & Quote(strFileIn) & " as file does not exist."
        CopyFile = False
    Else
        If FSO.FolderExists(strFileOut) Then
            If Right(strFileOut, 1) <> "\" Then strFileOut = strFileOut & "\"
        End If
        FSO.CopyFile strFileIn, strFileOut
        CopyFile = True
        If CatchErr(LOG_TRACE, "CopyFile", "Cannot copy " & Quote(strFileIn) & " to " & Quote(strfileOut)) Then
            CopyFile = False
        End If
    End If
End Function

Function CopyFolder(strFileIn, strFileOut)
    On Error Resume Next
    Err.Clear

    If Not FSO.FolderExists(strFileIn) Then
        Log LOG_TRACE, "CopyFolder", "Cannot copy " & Quote(strFileIn) & " as folder does not exist."
        CopyFolder = False
    Else
        If Not FSO.FolderExists(strFileOut) Then
            FSO.CopyFolder strFileIn, strFileOut, TRUE
            CopyFolder = True
        End If

        If CatchErr(LOG_TRACE, "CopyFolder", "Cannot copy " & Quote(strFileIn) & " to " & Quote(strfileOut)) Then
            CopyFolder = False
        End If
    End If
End Function

Function OutFile(strFileName, ByRef objStream)
    Dim bResult, objFile
    On Error Resume Next
    If FSO.FileExists(strFileName) Then
        Log LOG_TRACE, "OutFile", "File " & Quote(strFileName) & " exists, appending."
        Set objFile = FSO.GetFile(strFileName)
        Set objStream = objFile.OpenAsTextStream(ForAppending, TristateUnicode)
    Else
        Log LOG_TRACE, "OutFile", "File " & Quote(strFileName) & " does not exist, creating."
        Set objStream = FSO.CreateTextFile(strFileName, False, True)
    End If
    If CatchErr(LOG_DEBUG, "OutFile", "Cannot create output file " & Quote(strFileName)) Then
        OutFile = False
    Else
        OutFile = True
    End If
    On Error GoTo 0
End Function

Sub WriteFile(strFileName, strContents)
    Dim objFile
    If OutFile(strFileName, objFile) Then
        objFile.Write strContents
        objFile.Close
    End If
End Sub

Function CatchErr(intLogLevel, strFunc, strMsg)
    If Err.Number <> 0 Then
        If Not IsNull(strMsg) Then Log intLogLevel, strFunc, strMsg
        Log LOG_TRACE, "CatchErr", "Caught an error, details below:" & VbCrLf _
                & "    Error number: " & Err.Number & VbCrLf _
                & "    Error source: " & Err.Source & VbCrLf _
                & "    Error description: " & Err.Description
        Err.Clear
        CatchErr = True
    Else
        CatchErr = False
    End If
End Function


Function PromptUser(strPrompt, strDefault)
   If BoolSilentMode Then
      PromptUser = strDefault
   Else
      Dim strAns
      ' Let this fail, if we can't get input then we will have to make do
      On Error Resume Next
      WScript.StdOut.Write strPrompt
      strAns = Trim(WScript.StdIn.ReadLine)
      CatchErr LOG_DEBUG, "PromptUser", "Unable to read user input for: " & strPrompt
      On Error GoTo 0
      If IsNull(strAns) Or Len(strAns) = 0 Then strAns = strDefault
      PromptUser = strAns
   End If
   Log LOG_DEBUG, "PromptUser", strPrompt & " " & PromptUser
End Function


Function PromptYesNo(strPrompt, boolDefault)
   Dim strAns, boolValid
   Do
      If boolDefault Then
         strAns = PromptUser(strPrompt & "[Y/n] ", "y")
      Else
         strAns = PromptUser(strPrompt & "[y/N] ", "n")
      End If
      boolValid = True
      Select Case lcase(strAns)
         Case "yes" : PromptYesNo = True
         Case "y"   : PromptYesNo = True
         Case "no"  : PromptYesNo = False
         Case "n"   : PromptYesNo = False
         Case Else
            Log LOG_WARN, "PromptYesNo", "Invalid selection: " & strAns
            boolValid = False
      End Select
      If boolValid Then Exit Do
   Loop
End Function


Function ReadRegString(strRegKey)
   On Error Resume Next
   Err.Clear
   ReadRegString = wshell.RegRead(strRegKey)
   If Err.Number = -2147024894 Then
      ReadRegString = Null
      Log LOG_TRACE, "ReadRegString", "Unable to read " & strRegKey
   ElseIf Err.Number Then
      ReadRegString = Null
      CatchErr LOG_TRACE, "ReadRegString", "Unable to read " & strRegKey
   End If
End Function


' typically c:\windows\temp
Function SysTempDir()
   SysTempDir = WShell.ExpandEnvironmentStrings(WShell.Environment.Item("TEMP"))
End Function


' typically the user's temp folder
Function ProcessTempDir()
   ProcessTempDir = WShell.ExpandEnvironmentStrings(WShell.Environment("Process").Item("Temp"))
End Function


' Get Pcoip log folder
Function PcoipLogDir()
   ' Pcoip calls the API GetTempPath to get the log path.
   ' The GetTempPath function checks for the existence of environment variables
   ' in the following order and uses the first path found:
   ' - The path specified by the TMP environment variable.
   ' - The path specified by the TEMP environment variable.
   ' - The path specified by the USERPROFILE environment variable.
   ' - The Windows directory.
   ' So we can't call ProcessTempDir to get the Pcoip log path directly.
   Log LOG_DEBUG, "PcoipLogDir", "Get Pcoip log path from the environment variable TMP."
   PcoipLogDir = WShell.ExpandEnvironmentStrings(WShell.Environment("Process").Item("TMP"))
   If PcoipLogDir = "" Or Not fso.FolderExists(PcoipLogDir) Then
      Log LOG_DEBUG, "PcoipLogDir", "Get Pcoip log path from the environment variable TEMP."
      PcoipLogDir = ProcessTempDir()
      If PcoipLogDir = ""  Or Not fso.FolderExists(PcoipLogDir) Then
         Log LOG_DEBUG, "PcoipLogDir", "Get Pcoip log path from the environment variable USERPROFILE."
         PcoipLogDir = WShell.ExpandEnvironmentStrings(WShell.Environment("Process").Item("USERPROFILE"))
         If PcoipLogDir = "" Or Not fso.FolderExists(PcoipLogDir) Then
            Log LOG_DEBUG, "PcoipLogDir", "Get Pcoip log path from the environment variable WINDIR."
            PcoipLogDir = WShell.ExpandEnvironmentStrings(WShell.Environment("Process").Item("WINDIR"))
         End If
      End If
   End If
   Log LOG_DEBUG, "PcoipLogDir", "Pcoip log path is " & PcoipLogDir & "."
End Function


' Best effort delete, continue on failure
Function DeleteFolder(strPath)
   On Error Resume Next
   Err.Clear
   DeleteFolder = False
   If FSO.FolderExists(strPath) Then
      FSO.DeleteFolder(strPath)
      DeleteFolder = True
   End If
   CatchErr LOG_DEBUG, "DeleteFolder", "Unable to remove " & Quote(strPath)
End Function


'
' ws_diag wrappers, begin moving away from the script into native ws_diag
'


' ws_diag command wrapper, returns output within start and end markers
Function DiagCmd(command, aArgs, strStart, strEnd)
    Dim wsCmd, i, strOut, posA, posB
    DiagCmd = Null

    wsCmd = Q(ws_diagCmd) & " -" & command
    If Not IsNull(aArgs) Then
    For i = LBound(aArgs) To UBound(aArgs)
        wsCmd = wsCmd & " " & Q(aArgs(i))
    Next
    End If

    strOut = RunCmd(wsCmd, workdir & "\ws_diag.pad")

    ' may want the full output
    If IsNull(strStart) Or IsNull(strEnd) Then
        DiagCmd = strOut
        Exit Function
    End If

    posA = InStr(strOut, strStart)
    If IsNull(posA) Or posA = 0 Then Exit Function

    posA = posA + Len(strStart)
    posB = InStr(posA, strOut, strEnd)
    If posB > 0 Then DiagCmd = Mid(strOut, posA, posB - posA)
End Function


' resolve a hostname to IP address, null on failure
Function ResolveHost(hostname)
    Dim output, aArgs(0), strIP
    aArgs(0) = hostname
    strIP = DiagCmd("resolvehost", aArgs, "RESOLVEHOST:{", "}")

    Log LOG_TRACE, "ResolveHost", "Host " & hostname & " has IP " & strIP
    ResolveHost = TrueFalse(isValidIP(strIP), strIP, Null)
End Function


' Do a TCP test on host,port pair. String result as with the old TestTcpPort
Function TcpTest(hostname, port)
    Dim output, aArgs(1)
    aArgs(0) = hostname
    aArgs(1) = port

    output = DiagCmd("tcptest", aArgs, "TCPTEST:{", "}")
    If IsNull(output) Or IsEmpty(output) Then output = "NO_WSDIAG"
    Log LOG_TRACE, "TcpTest", "Result for " & Join(aArgs, ":") & " is " & output
    TcpTest = output
End Function


' Do a user privileges check. True if user has admin rights, else false.
Function CheckAdmin()
    Dim isAdmin

    isAdmin = DiagCmd("checkadmin", Null, "CHECKADMIN:{", "}")
    If IsNull(isAdmin) Or IsEmpty(isAdmin) Then isAdmin = "FALSE"
    Log LOG_TRACE, "CheckAdmin", "Result for admin rights check is " & isAdmin
    If isAdmin = "TRUE" Then
        CheckAdmin = True
    Else
        CheckAdmin = False
    End If
End Function
