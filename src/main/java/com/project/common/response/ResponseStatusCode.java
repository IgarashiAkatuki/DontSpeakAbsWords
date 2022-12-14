package com.project.common.response;

public enum ResponseStatusCode {

    SUCCESS(0,"请求成功"),
    FAILED(1,"请求失败"),
    ERROR(2,"服务器错误"),
    INVALID_PARAMETER(3,"参数不合法"),
    NOT_FOUND(4,"未查询到相关内容");


    private final int resultCode;
    private final String resultMsg;
    ResponseStatusCode(int resultCode,String resultMsg){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }
}
