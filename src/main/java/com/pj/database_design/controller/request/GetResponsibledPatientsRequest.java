package com.pj.database_design.controller.request;

public class GetResponsibledPatientsRequest {
    private Long nureseId;

    public GetResponsibledPatientsRequest(){}

    public GetResponsibledPatientsRequest(Long nureseId){
        this.nureseId=nureseId;

    }

    public Long getNureseId() {
        return nureseId;
    }

    public void setNureseId(Long nureseId) {
        this.nureseId = nureseId;
    }
}
