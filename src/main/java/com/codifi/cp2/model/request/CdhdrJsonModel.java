package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class CdhdrJsonModel {

    private int id;
    private String s4CdhdrOId;// s4objectClass
    private String s4CdhdrTcode;// s4tCode

}
