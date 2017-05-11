package demo.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import demo.di.SpringExtension;
import demo.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CompletableFutureService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    public CompletableFuture<Message> get(String payload, Long id) {
        logger.info("----------------------");
        logger.info("Se ejecuta el servicio que se encargara de dispara el escenario de actores");
        logger.info("----------------------");

        CompletableFuture<Message> future = new CompletableFuture<>();
        logger.info("----------------------");
        logger.info("El sistema de actores crea el actor, pasando para el constructor el futuro");
        logger.info("----------------------");

        ActorRef workerActor = actorSystem.actorOf(springExtension.props("workerActor", future), "worker-actor");
        logger.info("----------------------");
        logger.info("Se le dice al actor que debe hacer");
        logger.info("----------------------");


        workerActor.tell(new Message(payload, id), null);

        logger.info("----------------------");
        logger.info("Devuelve el futuro");
        logger.info("----------------------");

        return future;
    }
}
