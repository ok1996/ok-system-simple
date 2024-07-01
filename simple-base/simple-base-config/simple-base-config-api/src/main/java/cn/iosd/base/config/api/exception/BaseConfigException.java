package cn.iosd.base.config.api.exception;

/**
 * @author ok1996
 */
public class BaseConfigException extends RuntimeException {
    public BaseConfigException() {
        super();
    }

    public BaseConfigException(String message) {
        super(message);
    }

    public BaseConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseConfigException(Throwable cause) {
        super(cause);
    }

    protected BaseConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
