package com.abc.bean;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"code", "result", "msg", "obj"})
public class MyResponse {
    private Integer code;
    private String result;
    private String msg;
    private Object obj;

    public MyResponse(){}

    public MyResponse(Integer code, String result, String msg, Object obj){
        this.code = code;
        this.result = result;
        this.msg = msg;
        this.obj = obj;
    }

    public static MyResponse success(){return new MyResponse(200, "SUCCESS", "OK", 1); }

    public static MyResponse success(Object obj){return new MyResponse(200, "SUCCESS", "OK", obj); }

    public static MyResponse fail(){return new MyResponse(200, "FAILED", "BAD_REQUEST", 0); }

    public static MyResponse fail(String msg){return new MyResponse(200, "FAILED", msg, 0); }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }


}
