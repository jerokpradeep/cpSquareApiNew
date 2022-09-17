package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class FTWReceiverGroupModel {
    private int id;
    private String receiverGroup;
    private String approver;
    private String firstName;
    private String lastName;
    private String sapLoginId;
    private String createdOn;
    private String email;
}
