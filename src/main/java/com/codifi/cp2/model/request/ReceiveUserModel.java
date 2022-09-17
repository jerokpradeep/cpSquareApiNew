package com.codifi.cp2.model.request;

import java.time.Instant;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ReceiveUserModel {
    private int id;
    private String approver;
    private String firstName;
    private String lastName;
    private String sapLoginId;
    private String createdOn = String.valueOf(Instant.now().toEpochMilli());
    private String email;
}
