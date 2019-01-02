# build and run 
gradle build && gradle bootRun

# distribution
- id net.researchgate.release' version '2.6.0'

  gradle-release is a plugin for providing a Maven-like release process for projects using Gradle

- id "nebula.ospackage" version "6.1.1"

  Provides a task similar to Tar and Zip for constructing RPM and DEB package files.

# login test
```sh
curl -v -u GoShoppingOSClient:secret -X POST http://localhost:8000/oauth/token -H "Accept:application/json" -d "username=admin&password=123&grant_type=password"
```
