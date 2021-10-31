var target = Argument("target", "Default");
var tag = Argument("tag", "cake");

Task("Restore")
  .Does(() =>
{
    DotNetCoreRestore(".");
});

Task("Build")
  .Does(() =>
{
    DotNetCoreBuild(".");
});

Task("Test")
  .Does(() =>
{
    var files = GetFiles("tests/**/*.csproj");
    var settings = new DotNetCoreTestSettings
    {
        // Outputing test results as XML so that VSTS can pick it up
        ArgumentCustomization = args => args.Append("--logger \"trx;LogFileName=TestResults.trx\"")
    };
    foreach(var file in files)
    {
        DotNetCoreTest(file.ToString(), settings);
    }
    XmlTransform("trx-to-junit.xslt", "tests/Conduit.IntegrationTests/TestResults/TestResults.trx", "tests/Conduit.IntegrationTests/TestResults/TestResults.xml",
    new XmlTransformationSettings { Indent = true, Encoding = Encoding.Unicode});
});

Task("Publish")
  .Does(() =>
{
    var settings = new DotNetCorePublishSettings
    {
        Framework = "netcoreapp2.1",
        Configuration = "Release",
        OutputDirectory = "./publish",
        Runtime = "linux-x64",
        VersionSuffix = tag
    };

    DotNetCorePublish("src/Conduit", settings);
});

Task("Default")
    .IsDependentOn("Restore")
    .IsDependentOn("Build")
    .IsDependentOn("Test")
    .IsDependentOn("Publish");

 Task("Rebuild")
    .IsDependentOn("Restore")
    .IsDependentOn("Build");


RunTarget(target);
