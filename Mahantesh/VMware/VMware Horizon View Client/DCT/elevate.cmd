@echo off
setlocal

:: WSH willl attempt to parse arguments, so circumvent this and pass in as
:: environment variables.
set ELEVATE_CMDLINE=%*
set ELEVATE_APP=%1
:: Use start so that the window running this command is not left open
start wscript //nologo "%~dpn0.vbs" %*
