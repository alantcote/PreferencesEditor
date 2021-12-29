@echo off

setlocal

set APP_JAR=PreferencesEditor-0.1.0-SNAPSHOT.jar

start /B javaw --module-path lib --add-modules java.prefs,javafx.base,javafx.graphics,javafx.controls,javafx.fxml -jar %APP_JAR%
rem java --module-path lib --add-modules java.prefs,javafx.base,javafx.graphics,javafx.controls,javafx.fxml -jar %APP_JAR%

endlocal