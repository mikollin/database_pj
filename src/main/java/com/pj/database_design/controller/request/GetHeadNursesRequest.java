package com.pj.database_design.controller.request;

public class GetHeadNursesRequest {
    private String treatmentArea;

    public  GetHeadNursesRequest(){}
    public GetHeadNursesRequest(String treatmentArea) {
        this.treatmentArea=treatmentArea;
    }

    public String getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(String treatmentArea) {
        this.treatmentArea = treatmentArea;
    }
}
