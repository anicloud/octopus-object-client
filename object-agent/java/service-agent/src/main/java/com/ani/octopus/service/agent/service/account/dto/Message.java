package com.ani.octopus.service.agent.service.account.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Created by zhaoyu on 15-11-11.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = -6332778024900066625L;

    public HttpStatus status;
    public String message;

    public Message() {
    }

    public Message(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
