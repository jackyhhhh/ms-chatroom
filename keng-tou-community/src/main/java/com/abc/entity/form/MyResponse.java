package com.abc.entity.form;

import com.alibaba.fastjson.annotation.JSONType;

import java.io.Serializable;

@JSONType(orders = {"statusCode", "result", "errorMsg", "data"})
public class MyResponse implements Serializable {
    private Integer statusCode;
    private String result;
    private String errorMsg;
    private Object data;

    public MyResponse(Integer statusCode, String result, String errorMsg, Object data){
        this.statusCode = statusCode;
        this.result = result;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public static MyResponse success(){return new MyResponse(200,"SUCCESS", "OK", 1);}

    public static MyResponse success(Object data){return new MyResponse(200,"SUCCESS", "OK", data);}

    public static MyResponse fail(){return new MyResponse(400,"FAILED", "BAD_REQUEST", 0);}

    public static MyResponse fail(String errorMsg){return new MyResponse(400,"FAILED", errorMsg, 0);}

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
