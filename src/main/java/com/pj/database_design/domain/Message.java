package com.pj.database_design.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zyl
 */
@Entity
public class Message implements Comparable<Message> {

    private static final long serialVersionUID = 8957101640580343712L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer treatmentAreaNow;

    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Patient patient;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User user;

    public Message() {
    }

    public Message( User user,Integer treatmentAreaNow) {
        this.user=user;
        this.treatmentAreaNow = treatmentAreaNow;
    }

    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Message))
            return false;
        if (obj == this)
            return true;
        return this.getId().equals(((Message) obj).getId());
    }

    public int hashCode(){
        return id.intValue();//简单原则
    }

    public User getUser(){return user;}

    public void setUser(User user){this.user=user;}


    public Integer getTreatmentAreaNow() {
        return treatmentAreaNow;
    }

    public void setTreatmentAreaNow(Integer treatmentAreaNow) {
        this.treatmentAreaNow = treatmentAreaNow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public int compareTo(Message test) {           //重写Comparable接口的compareTo方法，
        if (this.getDate().getTime() > test.getDate().getTime()) {
            return -1;
        } else if (this.getDate().getTime() < test.getDate().getTime()) {
            return 1;
        } else {
            return 0;
        }
    }


}
