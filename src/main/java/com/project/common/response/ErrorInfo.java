package com.project.common.response;

public class ErrorInfo {

    private int code;
    private String msg;

    public ErrorInfo() {
    }

    public ErrorInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
