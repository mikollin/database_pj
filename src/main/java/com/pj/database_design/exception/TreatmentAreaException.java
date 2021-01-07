package com.pj.database_design.exception;

/**
 * @author zyl
 */
public class TreatmentAreaException extends RuntimeException {
    private static final long serialVersionUID = -7676567061553978674L;

    public TreatmentAreaException() {
        super("Area inconsistent!");
    }
}
