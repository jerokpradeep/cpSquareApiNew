package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".AUTOMATED_TRIGGER_DETAIL")
public class AutomatedTriggerEntity extends CommonEntity {
    @Column(name = "AUTOMATED_TRIGGER_ID", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private String automatedTriggerId;
    @Column(name = "TRIGGER_NAME_ENGLISH")
    private String triggerNameEnglish;
    @Column(name = "AUTOMATED_TRIGGER")
    private String description;
    @Column(name = "DATE_ACTIVATED")
    private String dateActivated;
    @Column(name = "DATE_DEACTIVATED")
    private String dateDeactivated;
    @Column(name = "LEAD_TIME_AVAILABLE")
    private boolean leadTimeAvailable;
    @Column(name = "EASY_NAME")
    private String easyName;
    @Column(name = "LEAD_TIME_DATA_S4_TABLE_NAME")
    private String tableName;
    @Column(name = "S4_TABLE_FIELD_NAME")
    private String fieldName;
    @Column(name = "TRIGGER_CHANGED_BY")
    private String triggerChangedBy;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CATEGORY_ALIGNMENT")
    private boolean categoryAlignment;
    @Column(name = "CONDITIONAL_FUNCTION")
    private boolean conditionalFunction;
    @Column(name = "DEVELOPMENT_PROGRESS")
    private int developmentProgress;
    @Column(name = "TRIGGER_NAME_GERMAN")
    private String triggerNameGerman;

}
