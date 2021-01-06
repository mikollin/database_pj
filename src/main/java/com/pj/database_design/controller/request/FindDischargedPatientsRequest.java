package com.pj.database_design.controller.request;

public class FindDischargedPatientsRequest {
    private String treatmentArea;
    public FindDischargedPatientsRequest(){}

    public FindDischargedPatientsRequest(String treatmentArea){
        this.treatmentArea=treatmentArea;
    }

    public String getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(String treatmentArea) {
        this.treatmentArea = treatmentArea;
    }
}
