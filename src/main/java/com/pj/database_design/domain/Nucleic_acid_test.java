package com.pj.database_design.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zyl
 */
@Entity
public class Nucleic_acid_test implements Comparable<Nucleic_acid_test> {

    private static final long serialVersionUID = 4995146226442448950L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long testId;

    private Integer result; //0表示核酸阴性  1表示核酸检测阳性
    private Integer conditionRate;//轻症0 重症1 危重症2

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Patient patient;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Doctor doctor;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;



    public Nucleic_acid_test() {
    }

    public Nucleic_acid_test(Integer result, Integer conditionRate, Patient patient, Doctor doctor,Date date) {
        this.result=result;
        this.conditionRate = conditionRate;
        this.patient=patient;
        this.doctor=doctor;
        this.date=date;
    }



    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Nucleic_acid_test))
            return false;
        if (obj == this)
            return true;
        return this.getTestId().equals(((Nucleic_acid_test) obj).getTestId());
    }

    public int hashCode(){
        return testId.intValue();//简单原则
    }


    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    @Override
    public int compareTo(Nucleic_acid_test test) {           //重写Comparable接口的compareTo方法，
        if (this.getDate().getTime() > test.getDate().getTime()) {
            return -1;
        } else if (this.getDate().getTime() < test.getDate().getTime()) {
            return 1;
        } else {
            return 0;
        }
    }
}