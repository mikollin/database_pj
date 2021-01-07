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
//    private String name;
//    private String gender;
//    private String pwd;
//    private Integer age;


    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User user;

    private Integer treatmentArea;

    public Head_nurse() { }


    public Head_nurse(Integer treatmentArea,User user) {
        this.user=user;
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

    public User getUser(){return user;}

    public void setUser(User user){this.user=user;}


}