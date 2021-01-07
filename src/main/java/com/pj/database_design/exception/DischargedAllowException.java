package com.pj.database_design.exception;

public class DischargedAllowException extends RuntimeException {
    private static final long serialVersionUID = -4966764765737244636L;

    public DischargedAllowException(Long patientId) {

        super("Patient:"+patientId+" is not allowed to be discharged.");
    }
}
