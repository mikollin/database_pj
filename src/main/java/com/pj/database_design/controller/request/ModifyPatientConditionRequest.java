package com.pj.database_design.controller.request;

public class ModifyPatientConditionRequest {
    private Long patientID;
    private String newCondition;

    public ModifyPatientConditionRequest(){}
    public ModifyPatientConditionRequest(Long patientID,String newCondition){
        this.patientID=patientID;
        this.newCondition=newCondition;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public String getNewCondition() {
        return newCondition;
    }

    public void setNewCondition(String newCondition) {
        this.newCondition = newCondition;
    }
}
