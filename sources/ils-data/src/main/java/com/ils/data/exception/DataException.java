package com.ils.data.exception;

/**
 * Created by mara on 7/11/15.
 */
public class DataException extends Exception {

    public DataException() {
        super();
    }

    public DataException(String cause) {
        super(cause);
    }

    public DataException(Throwable e) {
        super(e);
    }

    public DataException(String cause, Throwable e) {
        super(cause, e);
    }
}
