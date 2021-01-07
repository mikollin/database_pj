package com.pj.database_design.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zyl
 */
@Entity
public class Treat_record  implements Comparable<Treat_record>{

    private static final long serialVersionUID = -2213080401544373969L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Patient patient;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Ward_nurse wardNurse;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    private Float temperature;
    private String symptom;
    private Integer liveState; //0康复出院 1在院治疗 2死亡
    private Integer result; //0表示核酸阴性  1表示核酸检测阳性
    private Integer conditionRate;//轻症0 重症1 危重症2


    public Treat_record() {

    }


    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Treat_record))
            return false;
        if (obj == this)
            return true;
        return this.getRecordId().equals(((Treat_record) obj).getRecordId());
    }

    public int hashCode(){
        return recordId.intValue();//简单原则
    }


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Ward_nurse getWardNurse() {
        return wardNurse;
    }

    public void setWardNurse(Ward_nurse wardNurse) {
        this.wardNurse = wardNurse;
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

    public Integer getConditionRate() {
        return conditionRate;
    }

    public void setConditionRate(Integer conditionRate) {
        this.conditionRate = conditionRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Treat_record record) {           //重写Comparable接口的compareTo方法，
        if (this.getDate().getTime() > record.getDate().getTime()) {
            return -1;
        } else if (this.getDate().getTime() < record.getDate().getTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }
}