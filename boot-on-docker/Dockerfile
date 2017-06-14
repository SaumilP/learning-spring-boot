FROM centos
MAINTAINER Saumil Patel

# declare variables
ENV JAVA_VERSION 8u131
ENV BUILD_VERSION b11
ENV BUILD_HASH d54c1d3a095b4ff2b6607d096fa80163

# Upgrading system
RUN yum -y upgrade
RUN yum -y install wget

# Downloading & ConfigureJava 8
# Actual URL : http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.rpm
RUN wget -nv --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_VERSION-$BUILD_VERSION/$BUILD_HASH/jdk-$JAVA_VERSION-linux-x64.rpm" -O /tmp/jdk-8-linux-x64.rpm
RUN yum -y install /tmp/jdk-8-linux-x64.rpm
RUN alternatives --install /usr/bin/java jar /usr/java/latest/bin/java 200000
RUN alternatives --install /usr/bin/javaws javaws /usr/java/latest/bin/javaws 200000
RUN alternatives --install /usr/bin/javac javac /usr/java/latest/bin/javac 200000

EXPOSE 8080

# install Spring artifacts
VOLUME /tmp
ADD /target/boot-on-docker-0.1-SNAPSHOT.jar boot-on-docker-0.1-SNAPSHOT.jar
RUN sh -c 'touch /boot-on-docker-0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/boot-on-docker-0.1-SNAPSHOT.jar"]
