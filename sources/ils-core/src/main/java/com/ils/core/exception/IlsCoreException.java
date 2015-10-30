package com.ils.core.exception;

/**
 * Created by mara on 7/11/15.
 */
public class IlsCoreException extends Exception {

    public IlsCoreException() {
        super();
    }

    public IlsCoreException(String cause) {
        super(cause);
    }

    public IlsCoreException(Throwable e) {
        super(e);
    }

    public IlsCoreException(String cause, Throwable e) {
        super(cause, e);
    }
}
