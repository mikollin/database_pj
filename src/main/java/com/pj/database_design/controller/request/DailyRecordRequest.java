package com.pj.database_design.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pj.database_design.domain.Ward_nurse;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DailyRecordRequest {
    private Long patientId;
    private Long nurseId;

    private Date date;

    private Float temperature;
    private String symptom;
    private Integer liveState; //0康复出院 1在院治疗 2死亡
    private Integer result; //0表示核酸阴性  1表示核酸检测阳性


    public DailyRecordRequest(){}

    public DailyRecordRequest(Long patientId,Long nurseId,Date date,Float temperature,String symptom,Integer liveState,Integer result){
        this.patientId=patientId;
        this.nurseId=nurseId;
        this.date=date;
        this.temperature=temperature;
        this.symptom=symptom;
        this.liveState=liveState;
        this.result=result;

    }


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public Integer getLiveState() {
        return liveState;
    }

    public void setLiveState(Integer liveState) {
        this.liveState = liveState;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

}
