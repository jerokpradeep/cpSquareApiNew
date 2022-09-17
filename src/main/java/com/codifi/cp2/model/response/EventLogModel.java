package com.codifi.cp2.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventLogModel {
    private long id;
    private String eventDate;
    private String description;
    private String status;
    private String userId;
    private String source;
    private String request;
    private String response;
    private String deviceIp;
    private String userAgent;
}