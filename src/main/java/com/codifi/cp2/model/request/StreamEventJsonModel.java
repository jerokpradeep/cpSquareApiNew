package com.codifi.cp2.model.request;

import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class StreamEventJsonModel {
    private int id;
    private String triggerId;
    private String triggerName;
    private String productHierarchy;
    private String articleType;
    private int LeadTime;
}
