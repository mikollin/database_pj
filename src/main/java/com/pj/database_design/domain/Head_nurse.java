package com.pj.database_design.domain;


import javax.persistence.*;

/**
 * @author zyl
 */
@Entity
public class Head_nurse {

    private static final long serialVersionUID = -3526519104841435692L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long headNurseId;
    private String name;
    private String gender;
    private String pwd;
    private Integer age;
    private Integer treatmentArea;

    public Head_nurse() { }

    public Head_nurse(Integer treatmentArea, String name, String gender, String pwd, Integer age) {
        this.age=age;
        this.name=name;
        this.gender=gender;
        this.pwd=pwd;
        this.treatmentArea = treatmentArea;

    }

    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Head_nurse))
            return false;
        if (obj == this)
            return true;
        return this.getHeadNurseId().equals(((Head_nurse) obj).getHeadNurseId());
    }

    public int hashCode(){
        return headNurseId.intValue();//简单原则
    }


    public Integer getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(Integer treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

    public Long getHeadNurseId() {
        return headNurseId;
    }

    public void setHeadNurseId(Long headNurseId) {
        this.headNurseId = headNurseId;
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
}