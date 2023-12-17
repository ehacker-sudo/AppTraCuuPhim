package com.example.apptracuuphim.resource;

import com.example.apptracuuphim.model.Credit.Credit;

import java.util.List;

public class RateResource {
    public boolean success;
    public int status_code;
    public String status_message;
    public int rate;

    public RateResource(int rate) {
        this.rate = rate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

}
