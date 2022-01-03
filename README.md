# Kafka Chat

Chat that uses kafka to send messages.

## Running

### Server

Enter folder `server` and then initialize the spring system:

```bash
mvn spring-boot:run
```

Then, run the kafka server with the `docker-compose`:

```bash
docker-compose up -d
```

### Client

Execute the javafx system. You need the javafx runtime libraries.

```bash
mvn javafx:run
```