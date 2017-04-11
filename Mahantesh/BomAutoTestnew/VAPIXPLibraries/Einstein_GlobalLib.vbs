'===========================================================================================================================
'Function Name : getParamValue
'Description   :  Get the parameter value of the VAPI-XP script
'Arguments     : paramName,CurrentTSTest
'Return value  : paramValue - Value of the parameter
'============================================================================================================================

Function getParamValue(paramName,CurrentTSTest)
 blnTrue = "TRUE"
     'Output the parameters
      Set TSParams = CurrentTSTest.Params
       For i = 0 To TSParams.Count -1
           If StrComp(Trim(TSParams.ParamName(i)), Trim(paramName), 1) = 0 Then
             blnTrue = "TRUE"
             paramValue = TSParams.ParamValue(i)
           End If
       Next
       If blnTrue ="TRUE" Then
         If paramValue = "" Then
           Set paramValueFct = CurrentTSTest.ParameterValueFactory
           Set lst = paramValueFct.NewList("")
           For Each param In lst
           With param
              If StrComp(Trim(.Name), Trim(paramName), 1) = 0 Then
                blnTrue = "TRUE"
                paramhtmlValue = .DefaultValue
                htmlValues = split(paramhtmlValue,"<span")
                htmlValue1 = split(htmlValues(1),"</span>")
                htmlValue2 = Split(htmlValue1(0),">")
                paramValue = htmlValue2(1)
              End If
           End With
           Next
         End If
       End If
       getParamValue = paramValue
End Function

'===========================================================================================================================
'Function Name : readFile
'Description   :  To read the status from the text file
'Arguments     : sPath
'Return value  : sData - Status of the test from the text file
'============================================================================================================================

function readFile(sPath)
    const forReading = 1
    dim objFSO, objFile, sData, sContent
    set objFSO = createobject("Scripting.FileSystemObject")
    If (objFSO.FileExists(sPath)) Then
		set objFile = objFSO.openTextFile(sPath, ForReading)

		sData = "Passed"

		sContent = objFile.ReadAll()

		If instr(sContent,"Failed") > 0 then

			sData = "Failed" 

		End If
		objFile.close
		set objFile = nothing
		set objFSO = nothing
	Else
		sData = "Not Completed"
	End If
    readFile = sData
end function

'===========================================================================================================================
'Function Name : getResultfile
'Description   :  To get the path of the result folder
'Arguments     : resultfolder, sTCName
'Return value  : oNewFold.Path - Path of the result
'============================================================================================================================

function getResultfile(resultfolder, sTCName)
      getResultfile = ""
      Set objFSO = CreateObject("Scripting.FileSystemObject")
      FolderToScan = resultfolder
      Set objFolder = objFSO.GetFolder(FolderToScan)
      Set oNewFold = Nothing
      NewestDate = #1/1/1970#

      For Each objFold In objFolder.SubFolders
          If objFold.DateLastModified > NewestDate Then
              NewestDate = objFold.DateLastModified
              Set oNewFold = objFold
          End If
      Next
      Set objFold = Nothing
      If Not oNewFold Is Nothing Then
           getResultfile =  oNewFold.Path
      End If
      Set objFSO = nothing
end function

'===========================================================================================================================
'Function Name : getZipFileName
'Description   : To get the zipped report File
'Arguments     : resultfolder, sTCName
'Return value  : oNewFold.Path - Path of the result
'============================================================================================================================

function getZipFileName(resultfolder, sTCName)
      getZipFileName = ""
      Set objFSO = CreateObject("Scripting.FileSystemObject")
      FolderToScan = resultfolder
      Set objFolder = Nothing
      Set objFolder = objFSO.GetFolder(FolderToScan)
      Set oNewFold = Nothing
      NewestDate = #1/1/1970#

      For Each objFold In objFolder.SubFolders
          If objFold.DateLastModified > NewestDate Then
              NewestDate = objFold.DateLastModified
              Set oNewFold = objFold
          End If
      Next
      getZipFileName= oNewFold.name
end function

'===========================================================================================================================
'Function Name : executeScriptandAttachResult
'Description   : To trigger the test method from the command prompt and Attach the result to ALM after the execution
'Arguments     : Debug,CurrentTSTest,CurrentRun,TDHelper
'Return value  : ouresult - Status of the test script
'============================================================================================================================

Function executeScriptandAttachResult(Debug,CurrentTSTest,CurrentRun,TDHelper,CurrentTestSet)
  Dim testSuitName, sTCName,objWSH, objUserVariables,strProjectPath,objFSO, outFile, objShell, resultfolder, resultfile,getZipName, ouresult,strENVIRONMENT,strBROWSER
  'Get All The parametes
  testSuitName = Trim(getParamValue("suiteName",CurrentTSTest))
  sTCName = Trim(CurrentTSTest.TestName)
  'strENVIRONMENT = CurrentTestSet.Field("CY_USER_04")
  strENVIRONMENT="UAT"
  'strBROWSER = CurrentTestSet.Field("CY_USER_05") 
  strBROWSER = "chrome"
  Set objWSH =  CreateObject("WScript.Shell")
  Set objUserVariables = objWSH.Environment("USER")
  strProjectPath = objUserVariables("EINSTEIN_WORKSPACE")
  Set objWSH = NOTHING

'Create a batch file
  Set objFSO=CreateObject("Scripting.FileSystemObject")
  outFile="D:\workspace\BomAutoTest\gautorun.bat"
  Set objFile = objFSO.CreateTextFile(outFile,True)
  objFile.Write "cd "&strProjectPath&""& vbCrLf
  objFile.Write "set browser="&strBROWSER&""& vbCrLf
  objFile.Write "set test="&strENVIRONMENT&""& vbCrLf
  objFile.Write "%1 %2 %3" & vbCrLf
  objFile.Write "pause"  & vbCrLf
  objFile.Close
  XTools.Sleep 1000
  Set objFile = NOTHING

'trigger the test script from command prompt
  Set objShell = CreateObject("WScript.Shell")
  objShell.Run "D:\workspace\BomAutoTest\gautorun.bat mvn ""-Dtest="&testSuitName&"#"&sTCName&""" test",1,True
  Set objShell = NOTHING

'get the result folder path
  resultfolder = strProjectPath&"\Results\"
'get the html reprot file name
  resultfile = getResultfile(resultfolder,sTCName)
'read the test run status
  ouresult = readFile(resultfile & "\result.txt" )

  getZipName=getZipFileName(resultfolder,sTCName)
'get the zip file name
  strLogFile= resultfile & "\" & sTCName & ".zip"
Set fso = CreateObject("scripting.filesystemobject")
Set fil = fso.GetFile (strLogFile)
If fil.Size > 0 Then
   TDHelper.UploadAttachment strLogFile, CurrentRun
Else
	ouresult = "Not Completed"
End If
Set fso = NOTHING
Set fil = NOTHING
  executeScriptandAttachResult = ouresult

End Function
