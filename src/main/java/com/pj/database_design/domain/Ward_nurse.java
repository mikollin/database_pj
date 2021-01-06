package com.pj.database_design.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyl
 */
@Entity
public class Ward_nurse {

    private static final long serialVersionUID = 8953652274646165254L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wardNurseId;

    private String name;
    private String gender;
    private String pwd;
    private Integer age;

    private Integer treatmentArea;
    private Integer patientCount; //实际该病床护士负责的病人数

    //redundancy can be found by sickbed
    @OneToMany(cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
    private List<Patient> patients = new ArrayList<>();


    public Ward_nurse() {
    }

    public Ward_nurse(Integer treatmentArea, Integer patientCount, String name, String gender, String pwd, Integer age) {
        this.age=age;
        this.name=name;
        this.gender=gender;
        this.pwd=pwd;
        this.treatmentArea = treatmentArea;
        this.patientCount = patientCount;
    }

    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Ward_nurse))
            return false;
        if (obj == this)
            return true;
        return this.wardNurseId.equals(((Ward_nurse) obj).getWardNurseId());
    }

    public int hashCode(){
        return wardNurseId.intValue();//简单原则
    }


    public Integer getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(Integer treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

    public Long getWardNurseId() {
        return wardNurseId;
    }

    public void setWardNurseId(Long wardNurseId) {
        this.wardNurseId = wardNurseId;
    }

    public Integer getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(Integer patientCount) {
        this.patientCount = patientCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

//    public List<Sickbed> getSickbeds() {
//        return sickbeds;
//    }
//
//    public void setSickbeds(List<Sickbed> sickbeds) {
//        this.sickbeds = sickbeds;
//    }
}