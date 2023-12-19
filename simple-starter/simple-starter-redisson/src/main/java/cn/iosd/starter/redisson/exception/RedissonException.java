package cn.iosd.starter.redisson.exception;

/**
 * Redisson执行异常
 *
 * @author ok1996
 */
public class RedissonException extends RuntimeException {

    public RedissonException() {
        super();
    }

    public RedissonException(String message) {
        super(message);
    }

    public RedissonException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedissonException(Throwable cause) {
        super(cause);
    }

    protected RedissonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
