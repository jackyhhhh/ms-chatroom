package com.abc.bean;

public class MyResponse {
    private Integer code;
    private String result;
    private String msg;
    private Object obj;

    public MyResponse(){}

    public MyResponse(String msg){
        this.code = 200;
        this.obj = null;
        this.msg = msg;
        if("OK".equalsIgnoreCase(msg)){
            this.msg = "OK";
            this.result = "SUCCESS";
        }else {
            this.result = "FAILED";
        }
    }

    public MyResponse(Object obj){
        this.code = 200;
        this.result = "SUCCESS";
        this.msg = "OK";
        this.obj = obj;
    }

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
