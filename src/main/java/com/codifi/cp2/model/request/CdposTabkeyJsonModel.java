package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;
import lombok.*;

@Component
@Getter
@Setter
public class CdposTabkeyJsonModel {
    private int id;
    private String fieldName;
    private String trimFrom;
    private String trimTo;
}
