'
' (c) VMware
'
Option Explicit

' Magic namespace constants
Const HKLM = &H80000002
Const ForReading = 1
Const ForWriting = 2
Const ForAppending = 8
Const DISPLAY_DETAILS=False

' Thirdparty locations
Const TERA_SG_LOGLEVEL_VALUE = "HKLM\Software\Teradici\SecurityGateway\LogLevel"

' Actions
Const ACTION_SET_DEFAULT = 0
Const ACTION_VIEW_INFO   = 1
Const ACTION_VIEW_DEBUG  = 2
Const ACTION_VIEW_TRACE  = 3
Const ACTION_VIEW_ALL    = 4
Const ACTION_PCOIP_INFO  = 5
Const ACTION_PCOIP_DEBUG = 6
Const ACTION_PCOIP_TRACE = 7
Const ACTION_VCHAN_INFO  = 8
Const ACTION_VCHAN_DEBUG = 9
Const ACTION_VCHAN_TRACE = 10

' Log levels
Const LOG_DEFAULT=0
Const LOG_ALL   = 1
Const LOG_TRACE = 2
Const LOG_DEBUG = 3
Const LOG_INFO  = 4
Const LOG_WARN  = 5
Const LOG_ERROR = 6
Const LOG_FATAL = 7

' use this only with cscript
If InStr(LCase(WScript.FullName), "cscript") = 0 Then
    WScript.Echo "You must run this script using the cscript engine. Please " _
        & "run support.bat included in this directory."
    WScript.Quit 1
End If

' Objects we use throughout the script
Dim WShell : Set wshell = wscript.CreateObject("WScript.Shell")
Dim FSO : Set fso = CreateObject("Scripting.FileSystemObject")
Dim reg : Set reg = GetObject("winmgmts:{impersonationLevel=impersonate}!\\" _
                    & ".\root\default:StdRegProv")

Const companyName = "VMware, Inc."
Const prodName = "VMware Horizon Client"   ' Product name for display purposes
Const strShortName = "vdm"               ' The string used when space is at a premium
Const prodRegKey = "VMware VDM"
Const prodFolderName = "vdm-sdct"
' We'll use these to see what VDM products are installed
Const strClientMSICaption = "VMware Horizon Client"
Const strServerMSICaption = "VMware View Connection Server"
Const strAgentMSICaption = "VMware View Agent"

' Global vars needed everywhere
Dim installpath
Dim boolIsClient, boolIsServer, boolIsAgent, boolIs32On64

' Client install path
Dim ClientDir : ClientDir = fso.GetFolder(".").ParentFolder
Dim clientExe : clientExe = ClientDir & "\vmware-view.exe"

' Get version
Dim Version : Version = fso.GetFileVersion(clientExe)

Log "Set log levels for " & prodName & " (" & Version & ")"

' Determine if we are client, server, or agent. Error if more than one
CheckRoles

' Now we know what product is here, we can found out where it is installed if it's written in the registry
installpath = GetInstallPath()

' Now do the work

Detail "** Current settings:"
Detail "Message framework debug enabled: " & getDebugEnabled()
Detail "Message framework trace enabled: " & getTraceEnabled()
Detail "Java diagnostics enabled: " & getEnableJavaView()

Dim intAction : intAction = SelectAction()

Detail "Setting log levels"
performAction(intAction)

'
' main() ends.
'

Function SelectAction
    Dim strUserInput
    ' If the user input the log level parameter, we should use it directly.
    If WScript.Arguments.Count = 0 Then
        If boolIsServer Then
            Log "Please select a log level. Note that increased logging will slow your"
            Log "connection broker down and will generate much larger log files."
        Else
            Log "Please select a log level."
        End If
        Log "  1. View Info"
        Log "  2. View Debug"
        Log "  3. View Trace"
        Log "  4. View All"

        If boolIsClient Or boolIsAgent Then
            Log "  5. PCoIP Info"
            Log "  6. PCoIP Debug"
            Log "  7. Virtual Channel Info"
            Log "  8. Virtual Channel Debug"
            Log "  9. Virtual Channel Trace"
        End If

        Log "or..."
        Log "  0. Reset to installation defaults"
        WScript.StdOut.Write "Choice [blank to exit]: "
        strUserInput = Trim(WScript.StdIn.ReadLine)
    Else
        strUserInput = WScript.Arguments(0)
    End If

    Select Case strUserInput
        Case "0" : SelectAction = ACTION_SET_DEFAULT
        Case "1" : SelectAction = ACTION_VIEW_INFO
        Case "2" : SelectAction = ACTION_VIEW_DEBUG
        Case "3" : SelectAction = ACTION_VIEW_TRACE
        Case "4" :
		    ' If the user inputs the log level after "logLevels", we need not to show the warning message and wait for the user's choice.
		    If WScript.Arguments.Count = 0 Then
				WScript.StdOut.Write "[Warning] In View All log level, huge amount of logs will be generated and you may want to switch to another log level shortly, Confirm(Y/N):"
				Dim strUserInputConfirm : strUserInputConfirm = Trim(WScript.StdIn.ReadLine)
				If strUserInputConfirm = "Y" Or strUserInputConfirm = "y" Then
					SelectAction = ACTION_VIEW_ALL
				Else
					Log "Aborted. No changes to logging have been made."
					WScript.Quit 0
				End If
			Else
				SelectAction = ACTION_VIEW_ALL
			End If
        Case "5" : SelectAction = ACTION_PCOIP_INFO
        Case "6" : SelectAction = ACTION_PCOIP_DEBUG
        Case "7" : SelectAction = ACTION_VCHAN_INFO
        Case "8" : SelectAction = ACTION_VCHAN_DEBUG
        Case "9" : SelectAction = ACTION_VCHAN_TRACE
        Case ""
            Log "Aborted. No changes to logging have been made."
            WScript.Quit 0
        Case Else
            Log "NOT A VALID SELECTION"
            SelectAction = SelectAction()
    End Select
End Function

Sub performAction(intAction)
   Select Case intAction
      Case ACTION_SET_DEFAULT
         setViewLevels(LOG_DEFAULT)
         If boolIsClient Or boolIsAgent Then
            setPCoIPLevels(LOG_DEFAULT)
            setVChanLevels(LOG_DEFAULT)
         End If

      Case ACTION_VIEW_INFO   : setViewLevels(LOG_INFO)
      Case ACTION_VIEW_DEBUG  : setViewLevels(LOG_DEBUG)
      Case ACTION_VIEW_TRACE  : setViewLevels(LOG_TRACE)
      Case ACTION_VIEW_ALL    : setViewLevels(LOG_ALL)

      Case ACTION_PCOIP_INFO  : setPCoIPLevels(LOG_INFO)
      Case ACTION_PCOIP_DEBUG : setPCoIPLevels(LOG_DEBUG)

      Case ACTION_VCHAN_INFO  : setVChanLevels(LOG_INFO)
      Case ACTION_VCHAN_DEBUG : setVChanLevels(LOG_DEBUG)
      Case ACTION_VCHAN_TRACE : setVChanLevels(LOG_TRACE)
   End Select
End Sub

Function setPCoIPLevels(intLogLevel)
    Dim boolResult : boolResult = False
    Dim strRegLoc : strRegLoc = strRegSoft("HKLM\") & "Teradici\PCoIP\pcoip_admin\pcoip.event_filter_mode"

   If intLogLevel = LOG_DEFAULT Then
      Log "Removing registry settings for PCoIP log level."
      boolResult = deleteRegistryString(strRegLoc)
   Else
      Log "Updating registry settings for PCoIP log level " & strLog(intLogLevel) & "."

      Select Case intLogLevel
         Case LOG_INFO  : boolResult = setRegistryNumber(strRegLoc, 2)
         Case LOG_DEBUG : boolResult = setRegistryNumber(strRegLoc, 3)
      End Select
   End If

   If boolResult Then
      Log "  Result: SUCCESS"
   Else
      Log "  Result: ERROR"
      Log "*** ERROR ***"
   End If

   setPCoIPLevels = boolResult
End Function

Function setVChanLevels(intLogLevel)
    Dim boolResult : boolResult = False
    Dim strRegLoc : strRegLoc = strRegProduct("HKLM\") & "Log\VChanLogLevel"

   If intLogLevel = LOG_DEFAULT Then
      Log "Removing registry settings for virtual channel log level."
      boolResult = deleteRegistryString(strRegLoc)
   Else
      Log "Updating registry settings for virtual channel log level " & strLog(intLogLevel) & "."

      Select Case intLogLevel
         Case LOG_INFO  : boolResult = setRegistryString(strRegLoc, "0")
         Case LOG_DEBUG : boolResult = setRegistryString(strRegLoc, "1")
         Case LOG_TRACE : boolResult = setRegistryString(strRegLoc, "10")
      End Select
   End If

   If boolResult Then
      Log "  Result: SUCCESS"
   Else
      Log "  Result: ERROR"
      Log "*** ERROR ***"
   End If

   setVChanLevels = boolResult
End Function

Sub setViewLevels(intLogLevel)
    If boolIsAgent Then setAgentLevels(intLogLevel)
    If boolIsClient Then setClientLevels(intLogLevel)
    If boolIsServer Then setServerLevels(intLogLevel)
End Sub

Sub setAgentLevels(intLogLevel)
   Detail "Registry: " & setRegistryLevels(intLogLevel)
End Sub

Sub setClientLevels(intLogLevel)
   Detail "Registry: " & setRegistryLevels(intLogLevel)
End Sub

Sub setServerLevels(intLogLevel)
   Dim boolJavaView, strLog4j
   boolJavaView = intLogLevel <= LOG_TRACE
   strLog4j = strLog(intLogLevel)
   Detail "Registry: " & setRegistryLevels(intLogLevel)
   Detail "PSG: " & setPSGLogLevel(intLogLevel)
   Log "Setting Log4j level to " & strLog4j
   Log "  Result: " & TrueFalse(setLog4j(intLogLevel),"SUCCESS","FAILED")
End Sub


Function setPSGLogLevel(intLogLevel)
   Dim strPsgLevel : strPsgLevel = "2"
   Select Case intLogLevel
      Case LOG_INFO : strPsgLevel = "3"
      Case LOG_DEBUG : strPsgLevel = "2"
      Case LOG_TRACE : strPsgLevel = "0"
   End Select
   setPSGLogLevel = setRegistryString(TERA_SG_LOGLEVEL_VALUE, strPsgLevel)
End Function


Function setRegistryLevels(intLogLevel)
    Dim boolResult
    Dim boolTrace : boolTrace = intLogLevel <= LOG_TRACE
    Dim boolDebug : boolDebug = intLogLevel <= LOG_DEBUG

    If intLogLevel = LOG_DEFAULT Then
        Log "Removing registry settings for log levels. Note: this will not remove any"
        Log "group policy settings."
        boolResult = deleteRegistryString(strRegProduct("HKLM\") & "DebugEnabled")
        boolResult = deleteRegistryString(strRegProduct("HKLM\") & "TraceEnabled")
        Log "  Result: SUCCESS"
    ElseIf intLogLevel = LOG_ALL Then
        Log "Setting registry settings for log levels."
        Log " Wanted log level: " & strLog(intLogLevel)

        If setTraceEnabled(True) And setDebugEnabled(True) And setClientLogLevel("All") Then
            Log "  Result: SUCCESS"
            setRegistryLevels = True
        Else
            Log "  Result: ERROR"
            Log "*** ERROR ***"
            setRegistryLevels = False
        End If
    Else
        Log "Setting registry settings for log levels. Note: these can be overridden by"
        Log "group policy. Wanted log level: " & strLog(intLogLevel)
        Log "  Debug level logs: " & boolDebug
        Log "  Trace level logs: " & boolTrace

        If setTraceEnabled(boolTrace) And setDebugEnabled(boolDebug) And setClientLogLevel("Info") Then
            Log "  Result: SUCCESS"
            setRegistryLevels = True
        Else
            Log "  Result: ERROR"
            Log "*** ERROR ***"
            setRegistryLevels = False
        End If
    End If
End Function

Function getEnableJavaView()
    ' check the tunnel service only, as that's present on both CS and SS
    getEnableJavaView = ToBool(getRegistryString(strRegProduct("HKLM\") & "tunnelService\Params\enableJavaView"))
End Function

Function setEnableJavaView(boolEnabled)
    Dim bTun, bTom, bMsg
    Dim strRegValue : strRegValue = TrueFalse(boolEnabled, "true", "false")

    bTun = setRegistryString(strRegProduct("HKLM\") & "tunnelService\Params\enableJavaView", strRegValue)
    bTom = setRegistryString(strRegProduct("HKLM\") & "tomcatService\Params\enableJavaView", strRegValue)
    bMsg = setRegistryString(strRegProduct("HKLM\") & "messageBusService\Params\enableJavaView", strRegValue)

    ' check the tunnel service only, as that's present on both CS and SS
    setEnableJavaView = bTun

    Detail "JavaView:: Tun:" & bTun & " Tom:" & bTom & " Msg:" & bMsg
End Function

Function getTraceEnabled()
    getTraceEnabled = ToBool(getRegistryString(strRegProduct("HKLM\") & "TraceEnabled"))
End Function

Function setTraceEnabled(boolEnabled)
    Dim strRegValue : strRegValue = TrueFalse(boolEnabled, "true", "false")
    setTraceEnabled = setRegistryString(strRegProduct("HKLM\") & "TraceEnabled", strRegValue)
End Function

Function getDebugEnabled()
    getDebugEnabled = ToBool(getRegistryString(strRegProduct("HKLM\") & "DebugEnabled"))
End Function

Function setDebugEnabled(boolEnabled)
    Dim strRegValue : strRegValue = TrueFalse(boolEnabled, "true", "false")
    setDebugEnabled = setRegistryString(strRegProduct("HKLM\") & "DebugEnabled", strRegValue)
End Function

Function setClientLogLevel(strLogLevel)
    setClientLogLevel = setRegistryString(strRegProduct("HKLM\") & "LogLevel", strLogLevel)
End Function


Function setLog4j(intLogLevel)
    Dim DefaultLog4j : DefaultLog4j = installpath & "lib\log4j.default"
    Dim LibLog4j : LibLog4j = installpath & "lib\log4j.properties"
    Dim TunnelLog4j : TunnelLog4j = installpath & "sslgateway\conf\log4j.cfg"
    Dim MessageBusLog4j : MessageBusLog4j = installpath & "messagebus\jars\log4j.properties"
    Dim strLogFile, bFileOk, bFoundFile

    ' start result as true, but no files found
    setLog4j = True
    bFoundFile = False

    ' Loop through each of the log files
    For Each strLogFile In Array(LibLog4j, TunnelLog4j, MessageBusLog4j)
        Detail "Working with " & Quote(strLogFile)
        If FSO.FileExists(strLogFile) Then
            Detail Quote(strLogFile) & " exists"
            bFoundFile = True
            bFileOk = True
            ' save a backup copy
            If Not FSO.FileExists(DefaultLog4j) And Not FSO.FileExists(strLogFile & ".bak") Then
                Detail "Default file does not exist, creating a backup"
                On Error Resume Next
                Err.Clear
                FSO.CopyFile strLogFile, strLogFile & ".bak"
                If Err.Number Then
                    Log "  ERROR: Can't backup " & Quote(strLogFile) & " - " & Err.Description
                    bFileOk = False
                End If
                On Error GoTo 0
            End If

            If bFileOk And intLogLevel = LOG_DEFAULT Then
                Detail "Default log level, restoring default file"
                On Error Resume Next
                Err.Clear
                FSO.DeleteFile strLogFile
                If FSO.FileExists(DefaultLog4j) Then
                    Detail "Replacing with installed default"
                    FSO.CopyFile DefaultLog4j, strLogFile
                Else
                    Detail "Moving backup file in place"
                    FSO.MoveFile strLogFile & ".bak", strLogFile
                End If
                If Err.Number Then
                    Log "  ERROR: Can't restore " & Quote(strLogFile) & " - " & Err.Description
                    bFileOk = False
                End If
                On Error GoTo 0
            ElseIf bFileOk Then
                bFileOk = createLog4j(strLogFile, intLogLevel)
                Detail "Generating replacement file. Result: " & bFileOk
            End If

            setLog4j = setLog4j And bFileOk
        End If
    Next

    Detail "Found one or more log4j files to modify: " & bFoundFile
    setLog4j = setLog4j And bFoundFile

End Function

Function createLog4j(strLogFile, intLogLevel)
    Dim oLogFile, strLevel, strIdentLine
    strIdentLine = "# Generated file, produced " & Date & " " & Time
    strLevel = strLog(intLogLevel)
    On Error Resume Next
    Err.Clear
    Set oLogFile = FSO.OpenTextFile(strLogFile, ForWriting)
    If Err.Number Then
        Log "  ERROR for log file " & Quote(strLogFile) & ": " & Err.Description
        createLog4j = False
    Else
        On Error GoTo 0
        oLogFile.WriteLine strIdentLine
        oLogFile.WriteLine "# Root category, set to ERROR. We only want to track our code"
        oLogFile.WriteLine "log4j.rootCategory=ERROR,vdm"
        oLogFile.WriteLine "# Default appender, goes to framework"
        oLogFile.WriteLine "log4j.appender.vdm=net.propero.workspace.windowsagent.appender.WSAppender"
        oLogFile.WriteLine "# Log level for all VDM code"
        oLogFile.WriteLine "log4j.category.com.vmware.vdi=" & strLevel
        oLogFile.WriteLine "log4j.category.net.propero=" & strLevel
        oLogFile.WriteLine "# Always log the audit messages"
        oLogFile.WriteLine "log4j.category.broker.Audit=INFO"
        oLogFile.WriteLine "# Profiling log, always at DEBUG"
        oLogFile.WriteLine "log4j.category.broker.Profiling=DEBUG"
        oLogFile.Close
        createLog4j = checkNewLog4j(strLogFile, strIdentLine)
    End If
End Function

Function checkNewLog4j(strLogFile, strIdentLine)
    Dim oLogFile : Set oLogFile = FSO.OpenTextFile(strLogFile, ForReading)
    Dim strTestLine : strTestLine = oLogFile.ReadLine
    oLogFile.Close
    Detail "Check log4j :: Want:" & Quote(strIdentLine) & " Got:" & Quote(strTestLine)
    checkNewLog4j = (strTestLine = strIdentLine)
End Function


Function GetInstallPath()
   Dim strInstallPathRegValue, strInstallPathRegData
   ' TODO: Rethink if we allow multiple components to be installed on one computer
   Select Case True
      Case boolIsClient : strInstallPathRegValue = "ClientInstallPath"
      Case boolIsServer : strInstallPathRegValue = "ServerInstallPath"
      Case boolIsAgent : strInstallPathRegValue = "AgentInstallPath"
   End Select

   ' client is a 32-bit app from 64-bit installer, retry the Wow6432Node
   strInstallPathRegData = getRegistryString(strRegProduct("HKLM\") & strInstallPathRegValue)
   If Not boolIs32On64 And IsNull(strInstallPathRegData) Then
      Detail "Product installpath not present, rechecking under Wow6432Node"
      boolIs32On64 = True
      strInstallPathRegData = getRegistryString(strRegProduct("HKLM\") & strInstallPathRegValue)
   End If

   If IsNull(strInstallPathRegData) Then
      Log "Installpath from " & strInstallPathRegValue & " not found."
      WScript.Quit 5
   Else
      GetInstallPath = strInstallPathRegData
      Detail "VDM component installed in " & Quote(GetInstallPath)
   End If
End Function


' Determine if we are client, server, or agent. Error if more than one
Sub CheckRoles
    Dim intNumRoles : intNumRoles = 0
    Detail "Checking the " & prodName & " role this computer plays by examining installed MSIs..."
    CheckRole2
    If Not (boolIsClient Or boolIsServer Or boolIsAgent) Then
        Detail "Did not find any roles, checking for 32 bit software on 64 bit OS"
        boolIs32On64 = True
        CheckRole2
    End If

    If boolIsClient Then intNumRoles = intNumRoles + 1
    If boolIsServer Then intNumRoles = intNumRoles + 1
    If boolIsAgent Then intNumRoles = intNumRoles + 1
    If intNumRoles < 1 Then
        Log "Could not find a " & prodName & " role. Exiting."
        wscript.quit 3
    ElseIf intNumRoles > 1 Then
        Log "This computer has more than one " & prodName & " role, which is unsupported. Exiting."
        wscript.quit 4
    End If
End Sub

Sub CheckRole2
    Dim sBaseKey, arSubKeys, iRC, sKey, sValue
    sBaseKey = strRegSoft("") & "Microsoft\Windows\CurrentVersion\Uninstall\"
    Detail "Checking under registry key " & Quote(sBaseKey)
    iRC = reg.EnumKey(HKLM, sBaseKey, arSubKeys)
    If Not IsArray(arSubKeys) Then Exit Sub
    For Each sKey In arSubKeys
        iRC = reg.GetStringValue(HKLM, sBaseKey & sKey, "DisplayName", sValue)
        If iRC <> 0 Then
            reg.GetStringValue HKLM, sBaseKey & sKey, "QuietDisplayName", sValue
        End If

        If sValue = strClientMSICaption Then
            boolIsClient = True
            Detail "This computer has '" & strClientMSICaption & "' installed"
            Exit For
        End If
    Next
End Sub

' Convert and quote a string
Function Quote(strIn)
    Dim i, s
    For i = 1 To Len(strIn)
        s = s & Chr(Asc(Mid(strIn, i, 1)))
    Next
    Quote = Chr(34) & s & Chr(34)
End Function

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

Function strLog(intLogLevel)
    Select Case intLogLevel
        Case LOG_DEFAULT : strLog = "DEFAULT"
        Case LOG_ALL   : strLog = "ALL"
        Case LOG_TRACE : strLog = "TRACE"
        Case LOG_DEBUG : strLog = "DEBUG"
        Case LOG_INFO  : strLog = "INFO"
        Case LOG_WARN  : strLog = "WARN"
        Case LOG_ERROR : strLog = "ERROR"
        Case Else : strLog = "UNKNOWN"
    End Select
End Function

Function strRegProduct(strStart)
    strRegProduct = strRegSoft(strStart) & companyName & "\" & prodRegKey & "\"
End Function

Sub Detail(strLine)
    If DISPLAY_DETAILS Then Log "-> " & strLine
End Sub

Sub Log(strLine)
    wscript.stdout.WriteLine strLine
End Sub

' returns true on success
Function setRegistryNumber(strRegKey, intRegValue)
    Dim readInt
    On Error Resume Next
    Err.Clear
    wshell.RegWrite strRegKey, intRegValue, "REG_DWORD"
    If Err.Number Then
        setRegistryNumber = False
        Detail Err.Number & " - " & Err.Description
    Else
        On Error GoTo 0
        readInt = getRegistryString(strRegKey)
        Detail "For " & strRegKey & "..."
        Detail "  Want: " & Quote(intRegValue) & " Got: " & Quote(readInt)
        setRegistryNumber = (intRegValue = readInt)
    End If
End Function

Function setRegistryString(strRegKey, strRegValue)
    Dim readStr
    On Error Resume Next
    Err.Clear
    wshell.RegWrite strRegKey, strRegValue
    If Err.Number Then
        setRegistryString = False
        Detail Err.Number & " - " & Err.Description
    Else
        On Error GoTo 0
        readStr = getRegistryString(strRegKey)
        Detail "For " & strRegKey & "..."
        Detail "  Want: " & Quote(strRegValue) & " Got: " & Quote(readStr)
        setRegistryString = (strRegValue = readStr)
    End If
End Function

' returns true on success
Function deleteRegistryString(strRegKey)
    Detail "Deleting " & strRegKey & "..."
    On Error Resume Next
    Err.Clear
    wshell.RegDelete strRegKey
    If Err.Number Then
        deleteRegistryString = False
        Detail Err.Number & " - " & Err.Description
    Else
        deleteRegistryString = True
    End If
End Function

Function getRegistryString(strRegKey)
    On Error Resume Next
    Err.Clear
    getRegistryString = wshell.RegRead(strRegKey)
    If Err.Number Then
        getRegistryString = Null
        Detail Err.Number & " - " & Err.Description
    End If
End Function

Function ToBool(valueIn)
    If IsNull(valueIn) Or IsEmpty(valueIn) Then
        ToBool = False
    ElseIf IsNumeric(valueIn) Then
        ToBool = CBool(valueIn)
    ElseIf IsObject(valueIn) Or IsArray(valueIn) Then
        ToBool = True
    Else
        If Trim(UCase(valueIn)) = "FALSE" Then
            ToBool = False
        Else
            ToBool = True
        End If
    End If
End Function