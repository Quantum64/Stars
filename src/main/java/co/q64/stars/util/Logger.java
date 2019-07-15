package co.q64.stars.util;

import co.q64.stars.binders.ConstantBinders.Name;
import org.apache.logging.log4j.LogManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Logger {
    private org.apache.logging.log4j.Logger logger;

    protected @Inject Logger(@Name String name) {
        logger = LogManager.getLogger(name);
    }

    public void info(String message) {
        logger.info(message);
    }
}
