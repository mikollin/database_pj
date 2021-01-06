package com.pj.database_design.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyl
 */
@Entity
public class Sickroom implements Serializable {

    private static final long serialVersionUID = -5788797582952610748L;
//
//    @Id
//    private String room_num;
//
//    @Id
//    private String building;
//
//    @Id
//    private String floor;
    @EmbeddedId
    private SickroomPrimarykey sickroomPrimarykey;

    private Integer treatmentArea;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Sickbed> sickbeds = new ArrayList<>();

    public Sickroom() {
    }

    public Sickroom(String room_num,String building,String floor,Integer treatmentArea) {
        this.sickroomPrimarykey=new SickroomPrimarykey(room_num,building,floor);
        this.treatmentArea = treatmentArea;
    }

    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Sickroom))
            return false;
        if (obj == this)
            return true;
        return this.getSickroomPrimarykey().equals(((Sickroom) obj).getSickroomPrimarykey());
    }

    public int hashCode(){
        return sickroomPrimarykey.hashCode();//简单原则
    }



    public Integer getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(Integer treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

    public List<Sickbed> getSickbeds() {
        return sickbeds;
    }

    public void setSickbeds(List<Sickbed> sickbeds) {
        this.sickbeds = sickbeds;
    }

    public SickroomPrimarykey getSickroomPrimarykey() {
        return sickroomPrimarykey;
    }

    public void setSickroomPrimarykey(SickroomPrimarykey sickroomPrimarykey) {
        this.sickroomPrimarykey = sickroomPrimarykey;
    }
}