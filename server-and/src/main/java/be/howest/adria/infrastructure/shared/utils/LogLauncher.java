package be.howest.adria.infrastructure.shared.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogLauncher extends RuntimeException {
    public LogLauncher(String message, Throwable cause, Logger logger, Level level) {
        super(message, cause);
        logger.log(level, message, cause);
    }
}