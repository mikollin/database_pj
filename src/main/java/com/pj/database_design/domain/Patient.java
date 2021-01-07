package com.pj.database_design.domain;


import javax.persistence.*;

/**
 * @author zyl
 */
@Entity
public class Patient {

    private static final long serialVersionUID = 6161500322549288999L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long patientId;


    private String name;
    private String gender;
    private Integer treatmentArea; //轻症0 重症1 危重症2
    private Integer conditionRate;//轻症0 重症1 危重症2
    private Integer age;
    private Integer liveState; //0康复出院 1在院治疗 2死亡
    private Integer isAllowedDischarged;//是否允许出院 0不允许 1允许

//    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Sickbed sickbed;

//    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private  List<Nucleic_acid_test> tests = new ArrayList<>();

    public Patient() {
        this.isAllowedDischarged=0;
    }

    public Patient(Integer treatmentArea, String name, String gender, Integer conditionRate, Integer age) {
        this.age=age;
        this.treatmentArea = treatmentArea;
        this.name=name;
        this.gender=gender;
        this.conditionRate = conditionRate;
        this.isAllowedDischarged=0;
    }

    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Patient))
            return false;
        if (obj == this)
            return true;
        return this.getPatientId().equals(((Patient) obj).getPatientId());
    }

    public int hashCode(){
        return patientId.intValue();//简单原则
    }



    public Integer getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(Integer treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public Integer getConditionRate() {
        return conditionRate;
    }

    public void setConditionRate(Integer conditionRate) {
        this.conditionRate = conditionRate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getLiveState() {
        return liveState;
    }

    public void setLiveState(Integer liveState) {
        this.liveState = liveState;
    }

    public Integer getIsAllowedDischarged() {
        return isAllowedDischarged;
    }

    public void setIsAllowedDischarged(Integer isAllowedDischarged) {
        this.isAllowedDischarged = isAllowedDischarged;
    }


//    public Sickbed getSickbed() {
//        return sickbed;
//    }
//
//    public void setSickbed(Sickbed sickbed) {
//        this.sickbed = sickbed;
//    }

//    public List<Nucleic_acid_test> getTests() {
//        return tests;
//    }
//
//    public void setTests(List<Nucleic_acid_test> tests) {
//        this.tests = tests;
//    }
}