package demo.actor;

import akka.actor.UntypedActor;
import demo.model.Message;
import demo.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("workerActor")
@Scope("prototype")
public class WorkerActor extends UntypedActor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BusinessService businessService;

    final private CompletableFuture<Message> future;

    public WorkerActor(CompletableFuture<Message> future) {
        this.future = future;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        logger.info("----------------------");
        logger.info("El negocio ejecuta el mensaje");
        logger.info("----------------------");
        businessService.perform(this + " " + message);

        if (message instanceof Message) {
            logger.info("----------------------");
            logger.info("una vez completado el mensaje, se lo da al future");
            logger.info("----------------------");
            future.complete((Message) message);
        } else {
            unhandled(message);
        }

        getContext().stop(self());
    }
}
