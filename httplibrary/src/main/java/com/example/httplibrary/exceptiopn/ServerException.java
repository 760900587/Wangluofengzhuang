package com.example.httplibrary.exceptiopn;
/// * 自定义服务器错误

public class ServerException extends RuntimeException {
   private String msg;
   private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
