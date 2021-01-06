package com.pj.database_design.exception;

/**
 * @author zyl
 */
public class AddNurseException extends RuntimeException {
    private static final long serialVersionUID = -6187001582830599615L;

    public AddNurseException() {
        super("Can't add a nurse who is still responsible for the patients in his/her old working area.");
    }
}
