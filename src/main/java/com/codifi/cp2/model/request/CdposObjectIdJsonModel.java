package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class CdposObjectIdJsonModel {
    private int id;
    private String fieldName;
    private String trimFrom;
    private String trimTo;
}
