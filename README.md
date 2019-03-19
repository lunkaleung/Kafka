# Kafka

To develop Producer and Consumer application in Kafka data framework with Kafka Java API.

## Prerequisites

To get started with installing and configuring Kafka on local system and create a simple topic and write Java program for Producer and Consumer.

## Deployment

### Compile
```
mvn clean compile assembly:single.
```

### Consumer Execution
```
java -cp target/KafkaAPIClient-1.0-SNAPSHOT-jar-with-dependencies.jar com.spnotes.kafka.simple.Consumer [TOPIC_NAME] [GROUP_NAME]
```

### Producer Execution
```
java -cp target/KafkaAPIClient-1.0-SNAPSHOT-jar-with-dependencies.jar com.spnotes.kafka.simple.Producer [TOPIC_NAME]
```

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management

