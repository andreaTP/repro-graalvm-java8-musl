
## Rationale

I want to be able to bundle Musl in my native binary, otherwise users without `glibc` installed will get a `java.net.UnknownHostException` at any point a DNS request is issued.

## Problem

Running the script:

```bash
./java11.sh
```

produces a static binary with Musl that works correctly on top of alpine by running:

```bash
./run.sh
```

Doing the very same but using Java 8:

```
./java8.sh
```

produces an error:

```
[error] Error: Building images for org.graalvm.nativeimage.Platform$LINUX_AMD64 requires static JDK libraries.
[error] Use JDK from https://github.com/graalvm/openjdk8-jvmci-builder/releases or https://github.com/graalvm/labs-openjdk-11/releases
[error] Missing libraries:java, nio, net, zip
```

that I have been unable to solve even providing the suggested JDKs native library.

## Expected result

The java8 GraalVM image produces a working static binary as the java11 based image does.

## Requirements

To execute this repro you need to have installed:

 - JDK
 - Sbt
 - Docker
 - curl
 - tar

Tested only on a Linux(Ubuntu) host.
