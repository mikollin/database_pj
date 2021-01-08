package com.pj.database_design.controller.request;

/**
 * @author zyl
 */
public class LoginRequest {
    private String username;
    private String password;
    private String treatmentArea;
    private String authority;


    public LoginRequest() {}

    public LoginRequest(String username, String password,String treatmentArea,String authority) {
        this.username = username;
        this.password = password;
        this.authority=authority;
        this.treatmentArea=treatmentArea;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTreatmentArea() {
        return treatmentArea;
    }

    public void setTreatmentArea(String treatmentArea) {
        this.treatmentArea = treatmentArea;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
