package com.demo.assignment.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private int statusCode;
    private String message;
    private Object content;
    private String dateTime;
    private String messageConstants;
}
