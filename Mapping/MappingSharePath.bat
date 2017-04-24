@echo off

cd C:\ALMConfig\ConfigFiles
set checkcounter=0
for /F "tokens=2" %%j in (AlmConfig.txt) do (
  set /A checkcounter+=1

 
)

if !checkcounter!==7 ( echo register not required
   ) else (cmd /v:on
reg add "HKCU\Software\Microsoft\Command Processor" /v DelayedExpansion /t REG_DWORD /d 1)

IF exist z:\  (echo Already Mapped) else (
cd C:\ALMConfig\ConfigFiles
setlocal EnableDelayedExpansion
set LineNumber=3
set LineNumber1=4
set FileName=AlmConfig.txt
set counter=0
set passcounter=0

for /F "tokens=2" %%j in (AlmConfig.txt) do (
  set /A counter+=1
  echo %%j
echo check=!counter!
echo check1=!LineNumber1!
  if !counter! == !LineNumber! (set us=%%j )
  if !counter! equ %LineNumber1% (set pas=%%j )
  
)
for /F "tokens=2" %%j in (AlmConfig.txt) do (
  set /A passcounter+=1
  set site=4
echo passcounter=!passcounter!
echo site=!site!
  
  if !passcounter! == !site! (set pas=%%j )
  
)
ECHO %j%
echo USERNAME=%us%
echo PASSWORD=%pas%


net use z: \\172.28.85.164\CDVTestTeam$ /user:CABLE\%us% %pas%

echo NETWORK MAPPED SUCCESSFULLY
) 




	














