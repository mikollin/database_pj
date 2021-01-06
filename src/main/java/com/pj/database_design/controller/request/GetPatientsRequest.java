package com.pj.database_design.controller.request;

/**
 * @author zyl
 */
public class GetPatientsRequest {
    private String treatmentArea;

    public GetPatientsRequest() {}

    public GetPatientsRequest(String treatmentArea) {
        this.treatmentArea=treatmentArea;
    }

    public String getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(String treatmentArea) {
        this.treatmentArea = treatmentArea;
    }
}
