package com.pj.database_design.exception;

/**
 * @author zyl
 */
public class AuthErrorException extends RuntimeException {
    private static final long serialVersionUID = 5572416177683963415L;

    public AuthErrorException(String level) {
        super("Authority of '" + level + "' is wrong");
    }
}
