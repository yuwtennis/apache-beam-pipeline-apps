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
_Example Class Name_ will be used to set environment variable _EXAMPLE_CLASS_ .

| Example Class Name                | Description                                       | State |
|-----------------------------------|---------------------------------------------------|-------|
| MongoIOSimpleReadService          | Simply read from mongodb                          | Done  |
| MongoIOSimpleWriteService         | Simply write to mongodb                           | Done  |
| MongoIOSimpleQueryService         | Simply read from mongodb using custom query       | Done  |
| ElasticsearchIOSimpleReadService  | Simply read from elasticsearch using custom query | Done  |
| ElasticsearchIOSimpleWriteService | Simply write to elasticsearch.                    | Done  |

## How to

### Python

See [README](python/README.md)

### Java

Also refer to [TUTORIAL](TUTORIAL.md) for more information.

#### Set environment variables

```shell
source ../envrc.sample
```

#### Run

```shell
./gradlew run
```
