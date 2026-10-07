@echo off
chcp 65001 >nul
cd /d "%~dp0"
javadoc -private -encoding UTF-8 -charset UTF-8 -docencoding UTF-8 -d docs -sourcepath src -subpackages calculadora
if errorlevel 1 goto erro
start "" "docs\index.html"
exit /b 0
:erro
pause
exit /b 1
