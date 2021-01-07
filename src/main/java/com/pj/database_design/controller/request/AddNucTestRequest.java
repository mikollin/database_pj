package com.pj.database_design.controller.request;

import java.util.Date;

public class AddNucTestRequest {
    private Long patientId;
    private Long doctorId;
    private Date date;
    private Integer result;//0为阴性 1为阳性
    private Integer conditionRate;//轻症0 重症1 危重症2


    public AddNucTestRequest(){}
    public AddNucTestRequest(Long patientId,Long doctorId,Date date,Integer result,Integer conditionRate){
        this.patientId=patientId;
        this.doctorId=doctorId;
        this.date=date;
        this.result=result;
        this.conditionRate=conditionRate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Integer getConditionRate() {
        return conditionRate;
    }

    public void setConditionRate(Integer conditionRate) {
        this.conditionRate = conditionRate;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
