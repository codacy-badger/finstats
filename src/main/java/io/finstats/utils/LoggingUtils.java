package io.finstats.utils;

import static io.vertx.core.logging.LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

public class LoggingUtils {

  public static void configureLogging() {
    System.setProperty("logback.configurationFile", "logback.xml");
    System.setProperty(LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());
  }
}
