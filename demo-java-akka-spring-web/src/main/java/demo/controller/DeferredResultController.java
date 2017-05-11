package demo.controller;

import demo.model.Message;
import demo.service.CompletableFutureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DeferredResultController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Long DEFERRED_RESULT_TIMEOUT = 1000L;

    private AtomicLong id = new AtomicLong(0);

    @Autowired
    private CompletableFutureService completableFutureService;

    @RequestMapping("/async-non-blocking")
    public DeferredResult<Message> getAsyncNonBlocking() {
        logger.info("----------------------");
        logger.info("Controller :crea el ejecutador una vez que el resultado este completado");
        logger.info("----------------------");
        DeferredResult<Message> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
        logger.info("----------------------");
        logger.info("Controller :crea el futuro");
        logger.info("----------------------");
        CompletableFuture<Message> future = completableFutureService.get("async-non-blocking", id.getAndIncrement());
        logger.info("----------------------");
        logger.info("Controller :se llamó al servicio de completar el futuro");
        logger.info("----------------------");
        future.whenComplete((result, error) -> {
            if (error != null) {

                deferred.setErrorResult(error);
            } else {
                logger.info("----------------------");
                logger.info("Ejecuta el resultado cuando lo recibe");
                logger.info("----------------------");
                deferred.setResult(result);
            }
        });
        return deferred;
    }
}
