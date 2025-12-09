# apache-beam-pipeline-apps

* [Available apps](#available-apps)
  * [Java](#java)
  * [Python](#python)
* [Platform environment](#platform-environment)
  * [Binaries](#binaries)
  * [Docker](#docker)
  * [Kubernetes](#kubernetes)
* [How to](#how-to)
  * [Java](#java-1)
  * [Python](#py)

## Target audience

People who use apache beam.

## Motivation

Not just for personal studying, but also would like to help people with pipeline implementation.

## Platform environment

Repository provides support to set up the environment.  
If you not to choose your own setup feel free to use it.

### Binaries

Run script to download the binaries.

Downloading Apache Flink.

```shell
bash scripts/download-app.sh -t flink -a APP_VERSION -s SCALA_VERSION
```

Downloading Apache Spark.

TBC

### Docker

Use [docker compose](compose.yml) when setting up docker environment.

```shell
docker compose up -d
```

### Kubernetes

Repository provides helm chart for setting up deployments , etc.

```shell
helm xxx
```

## How tos

### Python

See [README](python/README.md)

### Java

See [README](java/README.md)


