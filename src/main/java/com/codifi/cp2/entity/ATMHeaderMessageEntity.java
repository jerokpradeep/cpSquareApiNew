package com.codifi.cp2.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import lombok.Getter;
import lombok.Setter;

import com.codifi.cp2.model.request.MessageHeaderModel;
import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".AUTOMATED_TRIGGER_HEADER_MESSAGE")
@TypeDef(name = "json", typeClass = JsonType.class)
public class ATMHeaderMessageEntity extends CommonEntity {
    @Column(name = "AUTOMATED_TRIGGER_ID")
    private String automatedTriggerId;
    @Column(name = "AUTOMATED_TRIGGER_NAME")
    private String automatedTriggerName;
    @Column(name = "HEADER_TEXT")
    private String headerText;
    @Type(type = "json")
    @Column(name = "message_header_json", columnDefinition = "json")
    private List<MessageHeaderModel> messageHeaderJson;
}
