FROM mcr.microsoft.com/playwright:focal

# === INSTALL JDK and Maven ===

# Update package lists and install OpenJDK 17
RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-17-jdk wget tar && \
    rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME environment variable based on architecture
RUN ARCH=$(dpkg --print-architecture) && \
    if [ "$ARCH" = "amd64" ]; then \
        JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64; \
    elif [ "$ARCH" = "arm64" ]; then \
        JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64; \
    else \
        echo "Unsupported architecture: $ARCH"; exit 1; \
    fi && \
    echo "JAVA_HOME=$JAVA_HOME" >> /etc/environment && \
    echo "export JAVA_HOME=$JAVA_HOME" >> /etc/profile

# Download and install Maven
RUN wget https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.6/apache-maven-3.8.6-bin.tar.gz  && \
    tar xvf apache-maven-3.8.6-bin.tar.gz && \
    mv apache-maven-3.8.6 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm apache-maven-3.8.6-bin.tar.gz

# Copy application source code and Maven configuration
COPY src /app/src
COPY pom.xml /app

# Set environment variable for Playwright browsers path
ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright