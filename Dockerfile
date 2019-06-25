FROM openjdk:8

ADD /target/portal-standalone-0.1.0.jar test-number_lookup.jar

EXPOSE 8080

CMD java -jar test-number_lookup.jar
