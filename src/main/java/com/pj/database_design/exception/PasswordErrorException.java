package com.pj.database_design.exception;

/**
 * @author zyl
 */
public class PasswordErrorException extends RuntimeException {
    private static final long serialVersionUID = 5247413957259278459L;

    public PasswordErrorException(String username) {
        super("Password of '" + username + "' is wrong");
    }
}
