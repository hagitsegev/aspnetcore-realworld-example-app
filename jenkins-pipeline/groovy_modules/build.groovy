def buildAspnetCore () {
    echo "Building"
    sh ("./build.sh --target=Build")
    echo "Done Build"
}

def testAspnetCore () {
    sh ("./build.sh --target=Test")
    echo "Done Test"
}

def publishTestResults (String testsWildcardFiles) {
    archiveArtifacts artifacts: "$testsWildcardFiles"
    junit testsWildcardFiles
}

return this
