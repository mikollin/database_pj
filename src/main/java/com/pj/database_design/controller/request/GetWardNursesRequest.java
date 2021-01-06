package com.pj.database_design.controller.request;

public class GetWardNursesRequest {
    private String treatmentArea;

    public GetWardNursesRequest(){}
    public GetWardNursesRequest(String treatmentArea) {
        this.treatmentArea=treatmentArea;
    }

    public String getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(String treatmentArea) {
        this.treatmentArea = treatmentArea;
    }
}
