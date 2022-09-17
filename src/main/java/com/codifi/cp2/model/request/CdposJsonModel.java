package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;
import lombok.*;

@Component
@Getter
@Setter
public class CdposJsonModel {
    private int id;
    private String s4CdposOId; // s4ObjectClass
    private String s4CdposTab;// s4TabName
    private String s4CdposFld;// s4FName
    private String s4CdposCInd; // s4ChngInd
    private String s4CdposOldval;// s4OldValue
    private String s4CdposNewval; // s4NewValue
    private String s4Link;
}
