package com.pj.database_design.controller;

/**
 * @author zyl
 */
public class GetSickbedsRequest {
    private String treatmentArea;

    public GetSickbedsRequest() {}

    public GetSickbedsRequest(String treatmentArea) {
        this.treatmentArea=treatmentArea;
    }

    public String getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(String treatmentArea) {
        this.treatmentArea = treatmentArea;
    }
}
