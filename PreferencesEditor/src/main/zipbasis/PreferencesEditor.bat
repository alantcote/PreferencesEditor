rem @echo off

setlocal

set APP_JAR=PreferencesEditor-0.1.0-SNAPSHOT.jar

start /B javaw -cp lib/CLP*.jar,%APP_JAR% --module-path lib --add-modules javafx.controls,javafx.web -jar %APP_JAR%

endlocal