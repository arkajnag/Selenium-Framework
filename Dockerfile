FROM alpine:latest
MAINTAINER Arkajyoti Nag(arka.imps@gmail.com)
RUN apk update
RUN apk add --no-cache maven
RUN apk add --no-cache openjdk8
RUN mkdir /allure
RUN mkdir /allure-results
RUN mkdir /allure-report
RUN mkdir /allure-config
RUN apk update && \
	apk add ca-certificates && \
	update-ca-certificates && \
	apk add openssl && \
	apk add unzip
RUN wget https://repo1.maven.org/maven2/io/qameta/allure/allure-commandline/2.13.3/allure-commandline-2.13.3.zip
RUN unzip allure-commandline-2.13.3.zip -d /allure
RUN rm allure-commandline-2.13.3.zip
ENV PATH="/allure/bin:${PATH}"
ENV ALLURE_CONFIG="/allure-config/allure.properties"
COPY pom.xml /ecommerce-selenium/pom.xml
COPY src /ecommerce-selenium/src
COPY target/ecommerce-selenium-0.0.1-SNAPSHOT.jar /ecommerce-selenium/target/ecommerce-selenium-0.0.1-SNAPSHOT.jar
COPY testng.xml /ecommerce-selenium/testng.xml
