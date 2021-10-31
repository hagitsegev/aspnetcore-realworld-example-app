def buildAspnetCore () {
    echo "Building"
    sh ("./build.sh --target=Build")
    echo "Done Build"
}

def testAspnetCore () {
    sh ("./build.sh --target=Test")
    echo "Done Test"
}

return this
