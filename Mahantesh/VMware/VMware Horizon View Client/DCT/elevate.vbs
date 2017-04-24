Option Explicit

Dim objShell, objWshShell, objWshProcessEnv
Dim strCommandLine, strApplication, strArguments, strMajorWinVer
Set objShell = CreateObject("Shell.Application")
Set objWshShell = WScript.CreateObject("WScript.Shell")
Set objWshProcessEnv = objWshShell.Environment("PROCESS")

' Get raw command line agruments and first argument from elevate.cmd passed in
' through environment variables.
strCommandLine = objWshProcessEnv("ELEVATE_CMDLINE")
strApplication = objWshProcessEnv("ELEVATE_APP")
strArguments = Right(strCommandLine, (Len(strCommandLine) - Len(strApplication)))

' Modify arguments to notify support.bat that we have elevated
strArguments = "elevated " & strArguments

' Only use 'runas' for Vista and up
strMajorWinVer = Left(objWshShell.RegRead("HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\CurrentVersion"), 1)
If CInt(strMajorWinVer) >= 6 Then
   objShell.ShellExecute strApplication, strArguments, "", "runas"
Else
   objShell.ShellExecute strApplication, strArguments
End If
