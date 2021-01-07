package com.pj.database_design.exception;

/**
 * @author zyl
 */
public class MoveNurseException extends RuntimeException {
        private static final long serialVersionUID = -2801502515298958609L;

    public MoveNurseException() {
        super("Can't add/delete a nurse who is still responsible for the patients in his/her old working area.");
    }
}
