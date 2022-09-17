package com.codifi.cp2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.codifi.cp2.model.request.ReceiveUserModel;
import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".RECEIVER_GROUP_USER")
@TypeDef(name = "json", typeClass = JsonType.class)
public class ReceiverGroupUserEntity extends CommonEntity {
    @Column(name = "STREAM_NAME")
    private String streamName;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "RECEIVER_ID")
    private String receiverId;
    @Column(name = "RECEIVER_LONG")
    private String receiverLong;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name = "STATUS")
    private String status;
    @Type(type = "json")
    @Column(name = "USER_JSON", columnDefinition = "json")
    private List<ReceiveUserModel> userJson;
}
