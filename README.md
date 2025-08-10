# RabbitMQ-Websocket
Real-time notification system with RabbitMQ and Websocket (Spring Boot)
# Role-Based Real-Time Notification System

## Overview
This project implements **role-based real-time notifications** using:
- **Initiator Backend Service** – Publishes notification messages to RabbitMQ when events occur (e.g., LC saved).
- **WebSocket Service Backend** – Consumes messages from RabbitMQ, stores them in the database, and broadcasts them via WebSocket to the appropriate user role.
- **RabbitMQ** – Message broker for asynchronous communication between services.
- **Docker** – Runs RabbitMQ in a container with management UI.
- **PostgreSQL** – Stores notifications persistently.

The system ensures that whenever a relevant event happens in the Initiator Backend, all connected clients subscribed to a specific role will instantly receive the notification.

---

## Architecture

+-----------------+ AMQP +----------------------+ WebSocket +------------------+
| Initiator | ----------------> | WebSocket Service | -------------------> | Web Clients |
| Backend Service | Publish Message | Backend Service | /topic/role/{role} | (Angular, etc.) |
+-----------------+ +----------------------+ +------------------+
^ |
| v
| PostgreSQL
| Store Notifications
v
RabbitMQ (Docker)


---

## Components

### 1. Initiator Backend Service
- Publishes `NotificationDto` messages to RabbitMQ when events occur.
- Uses `RabbitTemplate` and `Jackson2JsonMessageConverter` for JSON serialization.
- Exchange: `notification.exchange`
- Routing Key: `notify.{role}` or `notify.test`

Example send method:
```java
amqpTemplate.convertAndSend("notification.exchange", "notify.test", notificationDto);

2. WebSocket Service Backend
Listens to the RabbitMQ queue notification.queue.

Stores notifications in PostgreSQL via NotificationRepository.

Uses SimpMessagingTemplate to broadcast notifications to /topic/role/{role}.

Clients subscribe to /topic/role/{role} to receive updates in real-time.

RabbitMQ binding:

BindingBuilder.bind(queue)
    .to(exchange)
    .with("notify.#");

3. RabbitMQ (Docker)
Runs locally via Docker with the management UI enabled.

Run container:

docker run -d --hostname my-rabbit --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management

Management UI: http://localhost:15672
Default credentials: guest / guest

Installation & Setup
Prerequisites
Java 17+ / Java 21 (recommended)

Maven 3+

Docker

PostgreSQL

RabbitMQ (Docker container)

1. Start RabbitMQ (Docker)
docker run -d --hostname my-rabbit --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management

2. Configure Initiator Backend
In application.yml:
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

Run:

bash
mvn spring-boot:run

3. Configure WebSocket Service Backend
In application.yml:
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
  datasource:
    url: jdbc:postgresql://localhost:5432/greenlc
    username: greenlc
    password: yourpassword


Run:

bash
mvn spring-boot:run

4. Connecting Clients
Clients connect to the WebSocket endpoint: /ws

Subscribe to: /topic/role/{roleName}

Receive notifications in real time when messages are published to RabbitMQ.

Testing
Publish from RabbitMQ UI:
Go to Exchanges → notification.exchange

Click Publish message

Routing key: notify.test

Payload:
{
  "message": "Hello from RabbitMQ UI",
  "role": "admin"
}

Publish from Initiator Backend:
Call:
notificationProducer.sendNotificationAfterLcSaved(
    new NotificationDto("Hello from Initiator Backend", "admin")
);

Troubleshooting
No message received by WebSocket service → Check RabbitMQ queue binding and routing key.

Connection refused → Ensure RabbitMQ Docker container is running and port 5672 is exposed.

DTO deserialization errors → Ensure Jackson2JsonMessageConverter is configured on both producer and consumer.

License
This project is for internal use and is not licensed for public distribution.
