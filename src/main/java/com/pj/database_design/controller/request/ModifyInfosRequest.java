package com.pj.database_design.controller.request;

public class ModifyInfosRequest {

    private Long userId;
    private String name;
    private String gender;
    private String pwd;
    private Integer age;

    public ModifyInfosRequest(){}

    public ModifyInfosRequest(Long userId,String name,String gender,String pwd,Integer age){
        this.userId=userId;
        this.name=name;
        this.gender=gender;
        this.pwd=pwd;
        this.age=age;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
