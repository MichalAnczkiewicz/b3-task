FROM mcr.microsoft.com/playwright:focal

# === INSTALL JDK and Maven ===

RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-17-jdk

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

RUN wget https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.6/apache-maven-3.8.6-bin.tar.gz  && \
    tar xvf apache-maven-3.8.6-bin.tar.gz && \
    mv apache-maven-3.8.6 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn

COPY src /app/src
COPY pom.xml /app

ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright