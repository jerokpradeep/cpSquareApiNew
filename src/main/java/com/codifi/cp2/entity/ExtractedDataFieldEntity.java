package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".EXTRACTED_DATA_FIELD_LIST")
public class ExtractedDataFieldEntity extends CommonEntity {
    @Column(name = "S4_TABLE_NAME")
    private String tableName;
    @Column(name = "S4_FIELD_NAME")
    private String FieldName;
    @Column(name = "ODATA_SERVICE")
    private String odataService;
    @Column(name = "MESSAGE_NAME")
    private String messageName;
    @Column(name = "SAP_FIELD_NAME")
    private String sapFieldName;
    @Column(name = "EASY_NAME")
    private String easyName;
    @Column(name = "DATE_TYPE")
    private String dateType;
    @Column(name = "MESSAGE_RELEVANT")
    private String messageRelevant;
}
