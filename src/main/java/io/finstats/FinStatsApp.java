package io.finstats;

import com.google.inject.Guice;
import io.finstats.api.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;
import static spark.Spark.awaitInitialization;
import static spark.Spark.port;

class FinStatsApp {
    private static final Logger log = LoggerFactory.getLogger(FinStatsApp.class);
    private static final int DEFAULT_PORT = 7000;
    private static final String BASE_URL = "http://localhost";

    private int serverPort;

    public FinStatsApp(int port) {
        this.serverPort = port;
    }

    void start(int port) {
        Router router = Guice
            .createInjector(new FinStatsModule())
            .getInstance(Router.class);

        if (port == 0) {
            log.info("Starting on random serverPort");
        }

        port(port);

        Spark.path(router.getPath(), router.getRoutes());

        awaitInitialization();

        port = Spark.port();
        log.info("Running at: http://localhost:{}", port);
    }

    public String getBaseUrl() {
        return String.format("%s:%d", BASE_URL, serverPort);
    }

    public static void main(String[] args) {
        new FinStatsApp().start(DEFAULT_PORT);
    }
}