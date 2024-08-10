package com.security.implementation.security.basicAuth.model;

import lombok.Data;

@Data
public class Response {
    private String ResponseCode;
    private String ResponseMessage;
    private Object data;
    
}
