# apache-beam-pipeline-apps

* [Available apps](#available-apps)
  * [Java](#java)
  * [Python](#python)
* [Tutorial](#tutorial)
* [How to](#how-to)
  * [Test](#test)
  * [Build](#build)
  * [Run](#run)

## Target audience

People who use apache beam.

## Motivation

Not just for personal studying, but also would like to help people with pipeline implementation.

## Available apps

### Java

See [examples](java/src/main/java/app/examples).

| Pipeline Name                     | Description                                       | State |
|-----------------------------------|---------------------------------------------------| ----- |
| MongoIOSimpleReadService          | Simply read from mongodb                          | Done |
| MongoIOSimpleWriteService         | Simply write to mongodb                           | Done |
| MongoIOSimpleQueryService         | Simply read from mongodb using custom query       | Done |
| ElasticsearchIOSimpleReadService  | Simply read from elasticsearch using custom query | Done |
| ElasticsearchIOSimpleWriteService | Simply write to elasticsearch.                    | Done |

### Python

tbc

## Tutorial

See [TUTORIAL.md](TUTORIAL.md).

## How to

### Java

#### Test

This is a instruction for running test locally. Tests are also ran in Github Action.

```shell
cd java/
./gradlew test
```

#### Build

```shell
cd java/
./gradlew shadowJar
```

#### Run
```shell
cd java

# Set environment variables accordingly
source ../envrc.sample

# Run JAR file
java -cp build/libs/java-1.0-SNAPSHOT-all.jar app.examples.APPNAME

e.g
java -cp build/libs/java-1.0-SNAPSHOT-all.jar app.examples.ElasticsearchIOSimpleRead
```