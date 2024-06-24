# Start with a base image that has Java and Maven installed
FROM selenium/standalone-firefox:latest

# Set the working directory in the container
WORKDIR /automation-testing
# Switch to the root user to install packages
USER root
# Install Java JDK and Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk wget curl maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Verify Java and Maven installation
RUN java -version && mvn -version

# Install Allure Framework
RUN apt-get update && \
    apt-get install -y wget && \
    ALLURE_VERSION=2.29.0 && \
    wget -qO- https://github.com/allure-framework/allure2/releases/download/${ALLURE_VERSION}/allure-${ALLURE_VERSION}.tgz | tar -zx -C /opt/ && \
    ln -s /opt/allure-${ALLURE_VERSION}/bin/allure /usr/bin/allure

RUN allure --version
# Install dependencies and package the application
# Copy the pom.xml and the source code into the container
COPY . .
# Ensure that the 'automation-testing' directory and its contents are owned by the selenium user
RUN chown -R seluser:seluser /automation-testing

# Switch back to the seluser
USER seluser
# Set the JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64

# Run main.java
CMD ["mvn", "compile", "exec:java"]
