package com.pj.database_design.controller.request;

public class BrowseTreatRecordsRequest {
    private Long patientId;
    public BrowseTreatRecordsRequest(){}
    public BrowseTreatRecordsRequest(Long patientId){
        this.patientId=patientId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
