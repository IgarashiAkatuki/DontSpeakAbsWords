package com.project.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Result<T> {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;


    public Result() {
    }

    public Result(String msg, int code){
        this.message = msg;
        this.code = code;
    }


    /**
     * 请求成功
     * @return
     * @param <T>
     */
    public static <T> Result<T> suc(){
        return suc(null);
    }

    /**
     * 请求成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> suc(T data){

        Result<T> result = new Result<>();
        result.setCode(ResponseStatusCode.SUCCESS.getResultCode());
        result.setMessage(ResponseStatusCode.SUCCESS.getResultMsg());
        result.setData(data);

        return result;
    }

    /**
     *  请求失败
     * @param errorInfo
     * @return
     * @param <T>
     */
    public static <T> Result<T> error(ErrorInfo errorInfo){

        Result<T> result = new Result<>();
        result.setCode(errorInfo.getCode());
        result.setMessage(errorInfo.getMsg());

        return result;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
