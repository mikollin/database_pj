package com.pj.database_design.controller.request;

public class BrowseNucTestsRequest {
    private Long patientId;
    public BrowseNucTestsRequest(){}
    public BrowseNucTestsRequest(Long patientId){
        this.patientId=patientId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
