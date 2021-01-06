package com.pj.database_design.exception;

/**
 * @author zyl
 */
public class AuthErrorException extends RuntimeException {
    private static final long serialVersionUID = -6187001582830599615L;

    public AuthErrorException(String level) {
        super("Authority of '" + level + "' is wrong");
    }
}
