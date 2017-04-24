@echo off

:: Need to be in local directory
pushd %~dp0

if not "%1" == "elevated" goto elevate

if "%2" == "loglevels" goto loglevels
if "%2" == "dct" goto supporttool
:: default
goto supporttool


:elevate
call "%~dp0elevate.cmd" "%~f0" %*
goto :eof


:: run support script
:supporttool
cscript //nologo vdm-support.vbs %*
goto end

:loglevels
if "%3" == "" (
   cscript //nologo vdm-debug.vbs
   goto end
) else (
   :: If the user inputs the log level after "logLevels", it will not want to see the pause info "Press any key to continue...".
   cscript //nologo vdm-debug.vbs %3
   exit
)

:end
popd
pause
