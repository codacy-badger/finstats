package io.finstats;

import com.google.inject.Guice;
import com.google.inject.Inject;
import io.finstats.api.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import static spark.Spark.awaitInitialization;
import static spark.Spark.port;

public class FinStatsApp {
    private static final Logger log = LoggerFactory.getLogger(FinStatsApp.class);
    private static final int DEFAULT_PORT = 7000;

    private final Router router;

    @Inject
    public FinStatsApp(Router router) {
        this.router = router;
    }

    public static void main(String[] args) {
        Guice.createInjector(new FinStatsModule())
            .getInstance(FinStatsApp.class)
            .start(DEFAULT_PORT);
    }

    private void start(int port) {
        port(port);

        Spark.get("/", (request, response) -> "Hello FinStats");
        Spark.path(router.getPath(), router.getRoutes());

        awaitInitialization();

        log.info("Running at: http://localhost:{}", port);
    }
}