package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class MessageHeaderModel {
    private String variableName;
    private String header;
    private String value;
}
