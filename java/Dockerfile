# Use image built by apache beam project
FROM apache/beam_java11_sdk:2.42.0

# Copy keystore to the image
ARG KEYSTORE_NAME=ca.p12
COPY certs/$KEYSTORE_NAME /tmp/$KEYSTORE_NAME