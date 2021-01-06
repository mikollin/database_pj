package com.pj.database_design.domain;


import javax.persistence.*;

/**
 * @author zyl
 */
@Entity
public class Emergency_nurse {

    private static final long serialVersionUID = 4164021917499812368L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emergencyNurseId;

    private String name;
    private String gender;
    private String pwd;
    private Integer age;

    public Emergency_nurse(){}

    public Emergency_nurse(String name,String gender,String pwd,Integer age) {
        this.age=age;
        this.name=name;
        this.gender=gender;
        this.pwd=pwd;
    }

    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Emergency_nurse))
            return false;
        if (obj == this)
            return true;
        return this.getEmergencyNurseId().equals(((Emergency_nurse) obj).getEmergencyNurseId());
    }

    public int hashCode(){
        return emergencyNurseId.intValue();//简单原则
    }



    public Long getEmergencyNurseId() {
        return emergencyNurseId;
    }

    public void setEmergencyNurseId(Long emergencyNurseId) {
        this.emergencyNurseId = emergencyNurseId;
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