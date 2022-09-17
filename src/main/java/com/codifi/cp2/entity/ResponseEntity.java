package com.codifi.cp2.entity;

import lombok.*;

@Getter
@Setter
public class ResponseEntity {
    private int status;
    private String message;
    private String reason;
    private Object result;
}
