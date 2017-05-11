package demo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final private String payload;
    final private long id;

    public Message(String payload, long id) {
        this.payload = payload;
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        logger.info("----------------------");
        logger.info("crea el mensaje");
        logger.info("----------------------");
        return "Message{" + "payload='" + payload + '\'' + ", id=" + id + '}';
    }
}
