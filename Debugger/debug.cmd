  @set project=%~dp0 
  @set package=%1
  
  @rem kill
  @adb shell am force-stop %package%                                       
  @rem uninstall
  @adb shell pm uninstall %package%
  @rem copy package
  @adb push .\app\build\outputs\apk\app-debug.apk /data/local/tmp/%package%
  @rem install
  @adb shell pm install -r "/data/local/tmp/%package%"
  @rem start apk  
  @set component=%package%/%package%.MainActivity
  @set action=android.intent.action.MAIN
  @set category=android.intent.category.LAUNCHER
  @adb shell am start -D -n %component% -a %action% -c %category%
  
  @adb shell ps ^| grep %package% > pid.txt
  @for /f "delims=" %%t in (pid.txt) do @set str=%%t
  @del pid.txt
:next_char
  @set "char=%str:~0,1%"
  @if "%char%" equ " " @goto next_space
  @set "str=%str:~1%"
  @goto next_char

:next_space
  @set "char=%str:~0,1%"
  @if "%char%" neq " " @goto port
  @set "str=%str:~1%"
  @goto next_space

:port
  @set temp=%str%
  @set pid=
:next_port
  @set "char=%temp:~0,1%"
  @if "%char%" equ " " @goto connect
  @set pid=%pid%%char%
  @set "temp=%temp:~1%"
  @goto next_port

:connect
  @set port=1025
  @adb forward tcp:%port% jdwp:%pid%
  @rem connect debugged
  java -classpath ".\out\production\Debugger;D:\Program Files\Java\jdk1.8.0_45\lib\tools.jar" com.runbox.debug.Debugger .\debug.jdb
    
