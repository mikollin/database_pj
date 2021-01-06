package com.pj.database_design.domain;


import javax.persistence.*;

/**
 * @author zyl
 */
@Entity
public class Sickbed {

    private static final long serialVersionUID = 7940797831438600814L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sickbedId;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Ward_nurse wardNurse;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Patient patient=null;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Sickroom sickroom;

    private Integer treatmentArea;

    public Sickbed() {
    }

    public Sickbed(Sickroom sickroom) {
        this.sickroom=sickroom;
        this.treatmentArea=sickroom.getTreatmentArea();
    }

    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sickbed))
            return false;
        if (obj == this)
            return true;
        return this.getSickbedId().equals(((Sickbed) obj).getSickbedId());
    }

    public int hashCode(){
        return sickbedId.intValue();//简单原则
    }


    public Long getSickbedId() {
        return sickbedId;
    }

    public void setSickbedId(Long sickbedId) {
        this.sickbedId = sickbedId;
    }

    public Ward_nurse getWardNurse() {
        return wardNurse;
    }

    public void setWardNurse(Ward_nurse wardNurse) {
        this.wardNurse = wardNurse;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Sickroom getSickroom() {
        return sickroom;
    }

    public void setSickroom(Sickroom sickroom) {
        this.sickroom = sickroom;
    }

    public Integer getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(Integer treatmentArea) {
        this.treatmentArea = treatmentArea;
    }
}