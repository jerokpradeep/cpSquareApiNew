package com.codifi.cp2.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.codifi.cp2.model.request.StreamEventJsonModel;
import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".STREAM_TRIGGER_EVENT")
@TypeDef(name = "json", typeClass = JsonType.class)
public class StreamTriggerEventEntity extends CommonEntity {
    @Transient
    private String eventType;
    @Column(name = "STREAM_NAME")
    private String streamName;
    @Column(name = "RECEIVER_ID")
    private String receiverId;
    @Column(name = "RECEIVER_LONG")
    private String receiverLong;
    @Type(type = "json")
    @Column(name = "EVENT_JSON", columnDefinition = "json")
    private List<StreamEventJsonModel> eventJson;
    @Type(type = "json")
    @Column(name = "FREE_TEXT_JSON", columnDefinition = "json")
    private List<StreamEventJsonModel> freeTextJson;
}
