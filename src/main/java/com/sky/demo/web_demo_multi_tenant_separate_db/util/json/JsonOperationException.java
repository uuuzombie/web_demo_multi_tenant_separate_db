package com.sky.demo.web_demo_multi_tenant_separate_db.util.json;

/**
 * Created by rg on 9/13/16.
 */
public class JsonOperationException extends RuntimeException {

    public JsonOperationException() {
        super();
    }

    public JsonOperationException(String message) {
        super(message);
    }

    public JsonOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonOperationException(Throwable cause) {
        super(cause);
    }

    protected JsonOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
