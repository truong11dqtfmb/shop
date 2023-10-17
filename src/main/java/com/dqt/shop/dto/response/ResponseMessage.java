package com.dqt.shop.dto.response;


import lombok.Data;

@Data
public class ResponseMessage {

    private boolean success;
    private String message;

    private Object data;

    public ResponseMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseMessage(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static ResponseMessage ok(String message) {
        return new ResponseMessage(true, message);
    }

    public static ResponseMessage error(String message) {
        return new ResponseMessage(false, message);
    }

    public static ResponseMessage ok(String message, Object data) {
        return new ResponseMessage(true, message, data);
    }

    public static ResponseMessage error(String message, Object data) {
        return new ResponseMessage(false, message, data);
    }
}

