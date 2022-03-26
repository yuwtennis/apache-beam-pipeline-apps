# apache-beam-pipeline-apps

* [Directory model](#directory-model)
* [Available apps](#available-apps)
  * [Java](#java)
  * [Python](#python)

## Target audience

People who use apache beam.

## Motivation

Not just for personal studying, but also would like to help people with pipeline implementation.

## Directory model
```
.
└── src
    ├── main
    │   └── java
    │       └── net
    │           └── yuwtennis
    │               └── app
    │                   ├── helpers
    │                   │   └── fns
    │                   └── pipelines
    │                       ├── connectors
    │                       └── elements
```

## Available apps

### Java
| Pipeline Name | Description | State |
| ---------------- | ----------- | ----- |
| MongoIOSimpleReadService | Simply read from mongodb | Done |
| MongoIOSimpleWriteService | Simply read from mongodb | Done |

### Python

tbc

## Available actions

### For java
#### Pre-requisite

* mvn (Tested on `3.8.4`)
* java (Tested on `openjdk 11.0.15`)
* mongodb if using `connector.MongoIORepository`. See `scripts/mongodb/createUser.js` for required roles.

#### Available environment variables

| Name | Description | Example |
| ---- | ----------- | ------- |
| MONGO_HOST | mongodb endpoint | localhost:27017 |
| MONGO_DB | Database name to connect to | literature |
| MONGO_COLLECTIONS | Collection name to connect to | quotes |
| MONGO_USER | User name to authenticate | myuser |
| MONGO_PASSWORD | Password | mypassword |
| PIPELINE_CLASS | Service name to run | net.yuwtennis.app.pipelines.MongoIOSimpleWriteService |
| LOG4J_LEVEL | See [Level](https://logging.apache.org/log4j/2.x/log4j-api/apidocs/org/apache/logging/log4j/Level.html) | INFO |


#### Actions

1. Unit test
```
make utest
```

2. Clean artifacts
```
make clean
```

3. Package
```
make package
```

4. Run pipeline
```
make run
```