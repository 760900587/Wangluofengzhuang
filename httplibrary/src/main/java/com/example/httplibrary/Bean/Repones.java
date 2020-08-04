package com.example.httplibrary.Bean;

import com.google.gson.JsonElement;

public class Repones {
    private JsonElement data;
    private int errorCode;
    private String errorMsg;

    public Repones(JsonElement data, int errorCode, String errorMsg) {
        this.data = data;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
