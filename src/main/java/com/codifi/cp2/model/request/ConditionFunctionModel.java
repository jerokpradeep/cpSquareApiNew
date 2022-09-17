package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;

import lombok.*;

@Component
@Getter
@Setter
public class ConditionFunctionModel { //condition
    private int id;
    private String value;
    private String link;
    private String conditionOption;
    private String measureDate;
    private String name;

}
