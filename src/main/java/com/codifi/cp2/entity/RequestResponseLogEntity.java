package com.codifi.cp2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".REQUEST_RESPONSE_LOG")
public class RequestResponseLogEntity extends CommonEntity {
    @Column(name = "URL")
    private String url;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ENTRY_TIME")
    private Date entryTime;

    @Column(name = "DEVICE_IP")
    private String deviceIp;

    @Column(name = "USER_AGENT")
    private String userAgent;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    @Column(name = "AUTH_TOKEN")
    private String authToken;

    @Column(name = "REQUEST_INPUT")
    private String requestInput;

    @Column(name = "ELAPSED_TIME")
    private String elapsedTime;

    @Column(name = "RESPONSE_DATA")
    private String responseData;

    @Column(name = "STATUS")
    private int status;

}
