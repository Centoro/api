@echo off
setlocal
set "JAVA_HOME=C:\Users\cypri\.jdk\java25\jdk-25.0.2"
set "PATH=%JAVA_HOME%\bin;%PATH%"
set "MAVEN_HOME=C:\ProgramData\chocolatey\lib\maven\apache-maven-3.9.11"
set "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo JDK 25 was not found at %JAVA_HOME%
    exit /b 1
)
if not exist "%MAVEN_CMD%" (
    echo Maven was not found at %MAVEN_CMD%
    exit /b 1
)

"%JAVA_HOME%\bin\java.exe" -version
call "%MAVEN_CMD%" spring-boot:run
exit /b %ERRORLEVEL%
