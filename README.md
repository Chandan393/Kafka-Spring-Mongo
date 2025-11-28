# Kafka Spring Boot -> MongoDB (via Kafka Connect) example

This repository is a minimal demonstration of:
- A Spring Boot REST app that performs CRUD on an in-memory store and publishes events to Kafka.
- A Docker Compose setup that runs Zookeeper, Kafka, Kafka Connect, and MongoDB.
- An example MongoDB Kafka Sink connector configuration that writes Kafka topic `orders` into MongoDB.

## How it works
1. The Spring Boot app exposes `/orders` endpoints. On each CREATE/UPDATE/DELETE it publishes an event to Kafka topic `orders`.
2. Kafka Connect (with MongoDB Sink) reads from topic `orders` and writes documents into MongoDB.

## What's included
- `pom.xml` - Maven project
- `src/...` - Java source code
- `docker-compose.yml` - services for Kafka ecosystem and MongoDB
- `mongo-sink-connector.json` - connector configuration to POST to Kafka Connect
- this README with quick start instructions

## Quick start (requires Docker & Maven)
1. Build the Spring Boot jar:
```bash
mvn -DskipTests package
```

2. Start the infra:
```bash
docker-compose up -d
```

3. Create the connector (wait ~10s for Connect to be ready):
```bash
curl -X POST -H "Content-Type: application/json" --data @mongo-sink.json http://localhost:8083/connectors
```

4. Run the Spring Boot app (or build and run jar):
```bash
mvn spring-boot:run
# or
java -jar target/kafka-spring-mongo-0.0.1-SNAPSHOT.jar
```

5. Test:
```bash
# create an order
curl -X POST -H "Content-Type: application/json" -d '{"item":"Laptop","quantity":1,"price":1200}' http://localhost:8080/orders
```

6. Check MongoDB:
```bash
# connect to mongo
docker exec -it kafka-mongo-mongo-1 mongo
use mydb
db.orders.find().pretty()
```

## Notes
- This sample uses in-memory storage for demonstration. MongoDB receives the Kafka events and acts as the persistent store in this pattern.
- The connector configuration uses `ReplaceOneDefaultStrategy` and `FullKeyStrategy` to map Kafka key -> document `_id`. Adjust to your needs.
