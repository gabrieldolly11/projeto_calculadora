@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\calculadora\*.java
if errorlevel 1 goto erro
java -cp bin calculadora.TesteCalculadora
if errorlevel 1 goto erro
pause
exit /b 0
:erro
echo Falha. Confira se o JDK 17 ou superior esta instalado.
pause
exit /b 1
