$ErrorActionPreference = 'Stop'

$javaHome = 'C:\Users\cypri\.jdk\java25\jdk-25.0.2'
$javaExecutable = Join-Path $javaHome 'bin\java.exe'

if (-not (Test-Path $javaExecutable)) {
    throw "JDK 25 was not found at $javaHome"
}

$env:JAVA_HOME = $javaHome
$env:Path = "$javaHome\bin;$env:Path"

& $javaExecutable -version
& .\mvnw.cmd spring-boot:run
exit $LASTEXITCODE
