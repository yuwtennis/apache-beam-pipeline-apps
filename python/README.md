
Command lines for all available apps for python.

## Deployments

### DirectRunner

None

### PortableDirectRunner

Start job server. Here I use the job server describe in [Java multi-language pipelines quickstart](https://beam.apache.org/documentation/sdks/java-multi-language-pipelines/).

```shell
poetry run python -m apache_beam.runners.portability.local_job_service_main -p 8099
```
Start SDK harness

```shell
docker compose up -d sdk-harness-python
```

### PortableRunner w/ Flink

Start job server , flink, SDK harness.

See below for more information.  
[How Are Beam Programmers Translated In Language Portability](https://flink.apache.org/2020/02/22/apache-beam-how-beam-runs-on-top-of-flink/#how-are-beam-programs-translated-in-language-portability)   
[Using the Apache Flink Runner](https://beam.apache.org/documentation/runners/flink/)

Result not guaranteed with multiple taskmanager .

```shell
docker compose up -d flink-job-server jobmanager taskmanager sdk-harness-python
```

## HelloWorld

```shell
export EXAMPLE_CLASS='HelloWorld'
```

### DirectRuner

```shell
poetry run python3 python/__main__.py \
    --runner=DirectRunner
```
### PortableDirectRunner

Make sure you start your env for [PortableDirectRunner](#portabledirectrunner).

```shell
poetry run python3 python/__main__.py \
    --runner=PortableRunner \
    --job_endpoint='localhost:8099' \
    --environment_type="EXTERNAL" \
    --environment_config="localhost:50000"
```

### Portable Runner w/ Flink

Make sure you start your env for [PortableRunner w/ Flink](#portable-runner-w-flink).

```shell
poetry run python3 python/__main__.py \
    --runner=PortableRunner \
    --job_endpoint='localhost:8099' \
    --environment_type="EXTERNAL" \
    --environment_config="localhost:50000"
```

### PortableRunner w/ Kafka

Notes: See [Issue 20979](https://github.com/apache/beam/issues/20979#issuecomment-1219575981)

## KafkaReadWrite

```shell
export EXAMPLE_CLASS='KafkaReadWrite'
```

### DirectRunner

```shell
poetry run python3 python/__main__.py \
    --streaming \
    --runner=DirectRunner 
```

### PortableRunner w/ Flink

Make sure you start your env for [PortableRunner w/ Flink](#portable-runner-w-flink).

```shell
poetry run python3 python/__main__.py \
    --streaming \
    --runner=PortableRunner \
    --job_endpoint='localhost:8099' \
    --environment_type="EXTERNAL" \
    --environment_config="localhost:50000"
```