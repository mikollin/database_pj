package com.pj.database_design.controller.request;

public class InitialRecordRequest {
    private String name;
    private Integer age;
    private String gender;
    private Integer conditionRate;//轻症0 重症1 危重症2

    public InitialRecordRequest(){}

    public InitialRecordRequest(String name,Integer age,String gender,Integer conditionRate){
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.conditionRate=conditionRate;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

}
