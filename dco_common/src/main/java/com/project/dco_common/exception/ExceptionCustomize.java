package com.project.dco_common.exception;

import lombok.Data;

@Data
public class ExceptionCustomize extends RuntimeException {

    public ExceptionCustomize() {
        super();
    }

    public ExceptionCustomize(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionCustomize(String message) {
        super(message);
    }

    public ExceptionCustomize(Throwable cause) {
        super(cause);
    }

}
