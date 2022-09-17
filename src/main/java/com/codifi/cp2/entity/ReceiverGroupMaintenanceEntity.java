package com.codifi.cp2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import com.codifi.cp2.model.request.DcListingModel;
import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".RECEIVER_GROUP_DETAIL")
@TypeDef(name = "json", typeClass = JsonType.class)
public class ReceiverGroupMaintenanceEntity extends CommonEntity {
    @Column(name = "STREAM_NAME")
    private String streamName;
    @Column(name = "RECEIVER_ID", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private String receiverId;
    @Column(name = "RECEIVER_LONG", updatable = false)
    private String receiverName;
    @Column(name = "RECEIVER_SHORT")
    private String receiverShort;
    @Column(name = "RECEIVER_GROUP_EMAIL")
    private String receiverGroupEmail;
    @Column(name = "DEVELOPMENT_PROGRESS", insertable = false, updatable = false)
    private int developmentProgress;
    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "MESSAGE_DELIVERY")
    private String messageDelivery;
    @Column(name = "EXCEL_CONSOLIDATION")
    private String excelConsolidation;
    @Column(name = "HOUSEKEEPING_TIME")
    private int housekeepingTime;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "DATE_ACTIVATED")
    private String dateActivated;
    @Column(name = "DATE_DEACTIVATED")
    private String dateDeactivated;
    @Column(name = "CONTACT_PERSON")
    private String contactPerson;
    @Column(name = "WEEKDAY")
    private String weekday;
    @Type(type = "json")
    @Column(name = "DC_LISTING", columnDefinition = "json")
    private List<DcListingModel> dcListing;

}
