def buildAspnetCore () {
    echo "Building"
    sh ("./build.sh --target=Build")
    echo "Done Build"
}

def testAspnetCore () {
    echo "Testing"
}

return this
