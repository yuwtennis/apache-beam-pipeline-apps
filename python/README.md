## Available apps

tbc

Command lines for all available apps for python.

## Deployments tips

### DirectRunner

None

### FlinkRunner w/ Embedded Flink Cluster

tbc

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

### FlinkRunner w/ Local Flink cluster

See [Using the Apache Flink Runner](https://beam.apache.org/documentation/runners/flink/)

#### Memory tuning for direct memory
Make sure you set enough direct memory otherwise you will see OutOfMemory in taskmanager.  
Beam requires heavy communications through FnAPI.  
See [Configure Off-heap Memory (direct or native)](https://nightlies.apache.org/flink/flink-docs-release-1.19/docs/deployment/memory/mem_setup_tm/#configure-off-heap-memory-direct-or-native).

```markdown
java.lang.OutOfMemoryError: Cannot reserve 2097152 bytes of direct buffer memory (allocated: 267321624, limit: 268435458)
at java.nio.Bits.reserveMemory(Bits.java:178) ~[?:?]
at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:111) ~[?:?]
at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:360) ~[?:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.PoolArena$DirectArena.allocateDirect(PoolArena.java:705) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.PoolArena$DirectArena.newChunk(PoolArena.java:680) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.PoolArena.allocateNormal(PoolArena.java:212) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.PoolArena.tcacheAllocateNormal(PoolArena.java:194) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.PoolArena.allocate(PoolArena.java:136) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.PoolArena.allocate(PoolArena.java:126) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.PooledByteBufAllocator.newDirectBuffer(PooledByteBufAllocator.java:397) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:188) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.netty.buffer.AbstractByteBufAllocator.buffer(AbstractByteBufAllocator.java:124) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.netty.NettyWritableBufferAllocator.allocate(NettyWritableBufferAllocator.java:51) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.MessageFramer.writeRaw(MessageFramer.java:292) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.MessageFramer.access$400(MessageFramer.java:45) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.MessageFramer$OutputStreamAdapter.write(MessageFramer.java:382) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.com.google.protobuf.CodedOutputStream$OutputStreamEncoder.write(CodedOutputStream.java:2961) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.com.google.protobuf.CodedOutputStream$OutputStreamEncoder.writeLazy(CodedOutputStream.java:2969) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.com.google.protobuf.ByteString$LiteralByteString.writeTo(ByteString.java:1445) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.com.google.protobuf.CodedOutputStream$OutputStreamEncoder.writeBytesNoTag(CodedOutputStream.java:2757) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.com.google.protobuf.CodedOutputStream$OutputStreamEncoder.writeBytes(CodedOutputStream.java:2731) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.model.jobmanagement.v1.ArtifactApi$GetArtifactResponse.writeTo(ArtifactApi.java:2907) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.com.google.protobuf.AbstractMessageLite.writeTo(AbstractMessageLite.java:60) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.protobuf.lite.ProtoInputStream.drainTo(ProtoInputStream.java:52) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.MessageFramer.writeToOutputStream(MessageFramer.java:274) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.MessageFramer.writeKnownLengthUncompressed(MessageFramer.java:229) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.MessageFramer.writeUncompressed(MessageFramer.java:172) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.MessageFramer.writePayload(MessageFramer.java:143) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.AbstractStream.writeMessage(AbstractStream.java:66) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.ServerCallImpl.sendMessageInternal(ServerCallImpl.java:168) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.ServerCallImpl.sendMessage(ServerCallImpl.java:152) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.stub.ServerCalls$ServerCallStreamObserverImpl.onNext(ServerCalls.java:380) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.runners.fnexecution.artifact.ArtifactRetrievalService.getArtifact(ArtifactRetrievalService.java:103) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.model.jobmanagement.v1.ArtifactRetrievalServiceGrpc$MethodHandlers.invoke(ArtifactRetrievalServiceGrpc.java:316) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.stub.ServerCalls$UnaryServerCallHandler$UnaryServerCallListener.onHalfClose(ServerCalls.java:182) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.PartialForwardingServerCallListener.onHalfClose(PartialForwardingServerCallListener.java:35) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.ForwardingServerCallListener.onHalfClose(ForwardingServerCallListener.java:23) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.ForwardingServerCallListener$SimpleForwardingServerCallListener.onHalfClose(ForwardingServerCallListener.java:40) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.Contexts$ContextualizedServerCallListener.onHalfClose(Contexts.java:86) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.ServerCallImpl$ServerStreamListenerImpl.halfClosed(ServerCallImpl.java:356) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.ServerImpl$JumpToApplicationThreadServerStreamListener$1HalfClosed.runInContext(ServerImpl.java:861) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.ContextRunnable.run(ContextRunnable.java:37) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at org.apache.beam.vendor.grpc.v1p69p0.io.grpc.internal.SerializingExecutor.run(SerializingExecutor.java:133) ~[blob_p-4c01a62437a94d63c34583e5431f37c26596d5c2-5c05176897453f94f5c776973683c666:?]
at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144) ~[?:?]
at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642) ~[?:?]
at java.lang.Thread.run(Thread.java:1583) [?:?]
```

### PATH setting for Taskmanager

If you are using streaming mode , you will need to tell the Taskmanager the location of the docker command.  
Otherwise, you will see something like this in your taskmanager`s log.

```markdown
Caused by: org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.util.concurrent.UncheckedExecutionException: java.io.IOException: Cannot run program "docker": error=2, No such file or directory
	at org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.cache.LocalCache$LocalLoadingCache.getUnchecked(LocalCache.java:5022) ~[?:?]
	at org.apache.beam.runners.fnexecution.control.DefaultJobBundleFactory$SimpleStageBundleFactory.<init>(DefaultJobBundleFactory.java:458) ~[?:?]
	at org.apache.beam.runners.fnexecution.control.DefaultJobBundleFactory$SimpleStageBundleFactory.<init>(DefaultJobBundleFactory.java:443) ~[?:?]
	at org.apache.beam.runners.fnexecution.control.DefaultJobBundleFactory.forStage(DefaultJobBundleFactory.java:310) ~[?:?]
	at org.apache.beam.runners.fnexecution.control.DefaultExecutableStageContext.getStageBundleFactory(DefaultExecutableStageContext.java:38) ~[?:?]
	at org.apache.beam.runners.fnexecution.control.ReferenceCountingExecutableStageContextFactory$WrappedContext.getStageBundleFactory(ReferenceCountingExecutableStageContextFactory.java:207) ~[?:?]
	at org.apache.beam.runners.flink.translation.wrappers.streaming.ExecutableStageDoFnOperator.open(ExecutableStageDoFnOperator.java:259) ~[?:?]
	at org.apache.flink.streaming.runtime.tasks.RegularOperatorChain.initializeStateAndOpenOperators(RegularOperatorChain.java:107) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.streaming.runtime.tasks.StreamTask.restoreStateAndGates(StreamTask.java:800) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.streaming.runtime.tasks.StreamTask.lambda$restoreInternal$3(StreamTask.java:754) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.streaming.runtime.tasks.StreamTaskActionExecutor$SynchronizedStreamTaskActionExecutor.call(StreamTaskActionExecutor.java:100) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.streaming.runtime.tasks.StreamTask.restoreInternal(StreamTask.java:754) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.streaming.runtime.tasks.StreamTask.restore(StreamTask.java:713) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.runtime.taskmanager.Task.runWithSystemExitMonitoring(Task.java:958) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.runtime.taskmanager.Task.restoreAndInvoke(Task.java:927) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.runtime.taskmanager.Task.doRun(Task.java:751) ~[flink-dist-1.19.3.jar:1.19.3]
	at org.apache.flink.runtime.taskmanager.Task.run(Task.java:566) ~[flink-dist-1.19.3.jar:1.19.3]
	at java.lang.Thread.run(Unknown Source) ~[?:?]
```

This can be achieved through flink properties which can be set in Flink Configuration.  

```markdown
env:
  log:
    level: DEBUG
  java:
    opts:
      all: --add-exports=java.base/sun.net.util=ALL-UNNAMED --add-exports=java.rmi/sun.rmi.registry=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED --add-exports=java.security.jgss/sun.security.krb5=ALL-UNNAMED --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.net=ALL-UNNAMED --add-opens=java.base/java.io=ALL-UNNAMED --add-opens=java.base/java.nio=ALL-UNNAMED --add-opens=java.base/sun.nio.ch=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED --add-opens=java.base/java.text=ALL-UNNAMED --add-opens=java.base/java.time=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED --add-opens=java.base/java.util.concurrent=ALL-UNNAMED --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED --add-opens=java.base/java.util.concurrent.locks=ALL-UNNAMED
  taskmanager:
    PATH: ${PATH}
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

### Flink Runner w/ Embedded Flink Cluster

```shell
poetry run python3 python/__main__.py \
    --runner=FlinkRunner \
    --environment_type="LOOPBACK"
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

### FlinkRunner

### DirectRunner

```shell
poetry run python3 python/__main__.py \
    --streaming \
    --runner=FlinkRunner  \
    --flink_master="localhost:8081" \
    --experiments=use_deprecated_read \
    --environment_type="LOOPBACK"
```