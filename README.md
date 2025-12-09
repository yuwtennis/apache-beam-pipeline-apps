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
Below is a setup using [minikube](https://minikube.sigs.k8s.io/).

```shell
minikube config set driver docker
minikube start \
  --container-runtime=containerd \
  --memory 8192
helm repo add jetstack https://charts.jetstack.io --force-update
helm install \
  cert-manager jetstack/cert-manager \
  --namespace cert-manager \
  --create-namespace \
  --version v1.16.5 \
  --set crds.enabled=true
  
helm install \
  -f charts/components/strimzi-values.yaml \
  --namespace beam \
  --create-namespace \
  components ./charts/components/
```

Termination.
```shell
helm uninstall --namespace beam components
```

## How tos

### Python

See [README](python/README.md)

### Java

See [README](java/README.md)


