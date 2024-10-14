FROM ubuntu:24.04

ARG MAVEN_VERSION=3.9.9
ARG USER_HOME_DIR="/root"
ARG MAVEN_HOME=/usr/share/maven
ENV MAVEN_HOME=$MAVEN_HOME
ENV PATH=$MAVEN_HOME/bin:$PATH

ENV DEBIAN_FRONTEND="noninteractive"

# Installer les dépendances (Java, Maven, curl, etc.)
RUN apt-get update \
    && apt-get install -y --fix-missing \
    openjdk-17-jdk \
    curl \
    tar \
    git \
    && apt-get clean

# Installer Maven
RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz -o /tmp/maven.tar.gz \
&& tar -xzf /tmp/maven.tar.gz -C /usr/share \
&& mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
&& ln -s /usr/share/maven/bin/mvn /usr/bin/mvn \
&& rm -f /tmp/maven.tar.gz

# Définir le JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH
#ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64/
#RUN export JAVA_HOME

#copy all the folders
COPY repository /app/repository

COPY install_dependencies.sh /app/install_dependencies.sh

COPY fr.inria.plasmalab.root/fr.inria.plasmalab* /app/fr.inria.plasmalab.root/

COPY fr.inria.plasmalab.root/compile.sh /app/fr.inria.plasmalab.root/compile.sh

#COPY fr.inria.plasmalab.matlab_ui /app/fr.inria.plasmalab.matlab_ui

COPY fr.inria.plasmalab.root/scripts /app/fr.inria.plasmalab.root/scripts

COPY fr.inria.plasmalab.root/scripts /app/fr.inria.plasmalab.root/scripts

COPY fr.inria.plasmalab.root/src /app/fr.inria.plasmalab.root/src

COPY fr.inria.plasmalab.root/target /app/fr.inria.plasmalab.root/target

COPY plasma_lab_examples /app/plasma_lab_examples

# set the working directory (inside the container)
WORKDIR /app

RUN chmod +x install_dependencies.sh

RUN ./install_dependencies.sh

RUN chmod +x fr.inria.plasmalab.root/compile.sh

WORKDIR /app/fr.inria.plasmalab.root

RUN ./compile.sh

RUN ./target/fr.inria.plasmalab-1.4.5-SNAPSHOT-distribution/plasmagui.sh

# Compiler le projet avec Maven
RUN mvn clean install
