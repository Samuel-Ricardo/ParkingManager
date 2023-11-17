FROM maven:3.8.5-openjdk-17-slim

WORKDIR /parking_manager
COPY . .

RUN mvn clean install

CMD mvn spring-boot:run