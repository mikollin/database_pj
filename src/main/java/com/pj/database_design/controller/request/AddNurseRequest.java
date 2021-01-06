package com.pj.database_design.controller.request;

public class AddNurseRequest {

    private String treatmentArea;
    private Long nurseID;

    public AddNurseRequest(){}

    public AddNurseRequest(Long nurseID,String treatmentArea){
        this.treatmentArea=treatmentArea;
        this.nurseID=nurseID;
    }

    public String getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(String treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

    public Long getNurseID() {
        return nurseID;
    }

    public void setNurseID(Long nurseID) {
        this.nurseID = nurseID;
    }
}
