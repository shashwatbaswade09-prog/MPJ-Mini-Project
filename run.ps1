# MPJ Vault - Portable Runner
$mavenVersion = "3.9.6"
$mavenHome = "$PSScriptRoot\.maven"
$mavenZip = "$mavenHome\maven.zip"
$mavenUrl = "https://archive.apache.org/dist/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"

if (-not (Test-Path $mavenHome)) {
    New-Item -ItemType Directory -Path $mavenHome | Out-Null
}

if (-not (Test-Path "$mavenHome\apache-maven-$mavenVersion")) {
    Write-Host "--- MPJ Vault: First time setup ---" -ForegroundColor Cyan
    Write-Host "Downloading Maven $mavenVersion..."
    Invoke-WebRequest -Uri $mavenUrl -OutFile $mavenZip
    Write-Host "Extracting Maven..."
    Expand-Archive -Path $mavenZip -DestinationPath $mavenHome
    Remove-Item $mavenZip
    Write-Host "Setup complete!" -ForegroundColor Green
}

# Add Maven to current session PATH
$env:PATH = "$mavenHome\apache-maven-$mavenVersion\bin;$env:PATH"

# Fix for Modern JDK (Java 17+) compatibility with Tomcat 7
$env:MAVEN_OPTS = "--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.util.concurrent=ALL-UNNAMED"

Write-Host "--- Starting MPJ Vault Banking System ---" -ForegroundColor Cyan
Write-Host "Access at: http://localhost:8081" -ForegroundColor Yellow

# Run the project
mvn tomcat7:run -f backend/pom.xml
