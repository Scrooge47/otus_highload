FROM amazoncorretto:17-alpine-jdk as build
WORKDIR application
ADD ./ .

RUN apk update apk add dos2unix
RUN dos2unix ./mvnw
RUN ./mvnw clean package install -DskipTests
RUN java -Djarmode=layertools -jar ./target/*.jar extract --destination ./target/extracted
FROM amazoncorretto:17-alpine-jdk

WORKDIR application

COPY --from=build application/target/extracted/dependencies/ ./
COPY --from=build application/target/extracted/spring-boot-loader/ ./
COPY --from=build application/target/extracted/snapshot-dependencies/ ./
COPY --from=build application/target/extracted/application/ ./

ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
