@echo off
cd /d "%~dp0"
javac -encoding UTF-8 -cp "lib/*" -d out *.java
if errorlevel 1 (
    echo Compile failed.
    pause
    exit /b 1
)
java -cp "out;lib/*" project.Library3.Main
