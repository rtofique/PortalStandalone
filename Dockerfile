FROM openjdk:8

ADD /target/portal-standalone-0.1.0.jar test-rest.jar

EXPOSE 8080

CMD java -jar test-rest.jar
