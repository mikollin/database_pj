package com.pj.database_design.domain;


import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author zyl
 */
@Embeddable
public class SickroomPrimarykey implements Serializable {

    private static final long serialVersionUID = 7175528651852207806L;

    private String roomNum;


    private String building;


    private String floor;


    public SickroomPrimarykey() {

    }

    public SickroomPrimarykey(String roomNum, String building, String floor) {
        this.roomNum = roomNum;
        this.building = building;
        this.floor = floor;

    }
    //重写equals方法, 最佳实践就是如下这种判断顺序:
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SickroomPrimarykey))
            return false;
        if (obj == this)
            return true;
        return this.getRoomNum().equals(((SickroomPrimarykey) obj).getRoomNum())&&
                this.getBuilding().equals(((SickroomPrimarykey) obj).getBuilding())&&
                this.getFloor().equals(((SickroomPrimarykey) obj).getFloor());
    }

    public int hashCode(){
       String tmp=building+floor+roomNum;
        return tmp.hashCode();
    }



    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

}