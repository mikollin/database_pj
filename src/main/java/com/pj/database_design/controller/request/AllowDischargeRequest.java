package com.pj.database_design.controller.request;

public class AllowDischargeRequest {

    private Long patinetId;
    private Long doctorId;

    public AllowDischargeRequest(){}
    public AllowDischargeRequest(Long patinetId,Long doctorId){
        this.patinetId=patinetId;
        this.doctorId=doctorId;
    }

    public Long getPatinetId() {
        return patinetId;
    }

    public void setPatinetId(Long patinetId) {
        this.patinetId = patinetId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
