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
    private Integer treatmentArea;


//    private String name;
//    private String gender;
//    private String pwd;
//    private Integer age;


    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User user;

    public Doctor() {
    }

//    public Doctor(Integer treatment_area,String name,String gender,String pwd,Integer age) {
////        this.age=age;
////        this.name=name;
////        this.gender=gender;
////        this.pwd=pwd;
////        this.treatment_area=treatment_area;
////    }

    public Doctor(Integer treatmentArea, User user) {
        this.user=user;
        this.treatmentArea = treatmentArea;
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

    public User getUser(){return user;}

    public void setUser(User user){this.user=user;}


    public Integer getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(Integer treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

}