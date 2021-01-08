package com.pj.database_design.exception;

/**
 * @author zyl
 */
public class NucTestIntervalException extends RuntimeException {
    private static final long serialVersionUID = -8282569709809215026L;

    public NucTestIntervalException() {
        super("Can't add a new test within 24 hours.");
    }
}
