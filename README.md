[![Build Status](https://travis-ci.org/gkuiper/uppaal-api.svg?branch=master)](https://travis-ci.org/gkuiper/uppaal-api)

## Install
Download and unpack Uppaal 4.1.19. If the jar files of Uppaal cannot be found in the local directory (uppaal.jar and lib/model.jar) make sure to use the path to the directory where Uppaal is install as prefix, eg ~/uppaal64-4.1.19/{uppaal.jar,lib/model.jar}.

Install Uppaal jars locally
```bash
RUN_TYPE=install ./mvn-install.sh com.uppaal 4.1.19 {uppaal.jar,lib/model.jar}
```

Deploy to local repository
```bash
RUN_TYPE=deploy REPO_ID=central REPO_URL=http://localhost:8081/artifactory/libs-release ./mvn-install.sh com.uppaal 4.1.19 {uppaal.jar,lib/model.jar}
```

Show the dependencies
```bash
RUN_TYPE=print ./mvn-install.sh com.uppaal 4.1.19 {uppaal.jar,lib/model.jar}
```

## Run

```bash
mvn exec:java
```
