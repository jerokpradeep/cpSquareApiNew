package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".TRIGGER_FIELD_LIST")
public class TriggerFieldListEntity extends CommonEntity {
    @Column(name = "FIELD_NAME")
    private String fieldName;
    @Column(name = "CP2_FIELD_NAME")
    private String cp2FieldName;
    @Column(name = "S4_FIELD_NAME")
    private String s4FieldName;
    @Column(name = "S4_TABLE_NAME")
    private String s4TableName;

}
