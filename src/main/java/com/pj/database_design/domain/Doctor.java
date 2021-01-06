package com.pj.database_design.domain;


import javax.persistence.*;

/**
 * @author zyl
 */
@Entity
public class Doctor {

    private static final long serialVersionUID = -764369486727586073L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long doctorId;
    private Integer treatment_area;


    private String name;
    private String gender;
    private String pwd;
    private Integer age;

    public Doctor() {
    }

    public Doctor(Integer treatment_area,String name,String gender,String pwd,Integer age) {
        this.age=age;
        this.name=name;
        this.gender=gender;
        this.pwd=pwd;
        this.treatment_area=treatment_area;
    }

    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Doctor))
            return false;
        if (obj == this)
            return true;
        return this.getDoctorId().equals(((Doctor) obj).getDoctorId());
    }

    public int hashCode(){
        return doctorId.intValue();//简单原则
    }



    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }



    public Integer getTreatment_area() {
        return treatment_area;
    }

    public void setTreatment_area(Integer treatment_area) {
        this.treatment_area = treatment_area;
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