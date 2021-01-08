package com.pj.database_design.controller.request;

public class GetMessagesRequest {
    private Long userId;


    public GetMessagesRequest(){}
    public GetMessagesRequest(Long userId){
        this.userId=userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
