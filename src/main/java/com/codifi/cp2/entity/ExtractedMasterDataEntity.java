package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".EXTRACTION_MASTER_TABLE")
public class ExtractedMasterDataEntity extends CommonEntity {
    @Column(name = "SAP_FIELD_NAME")
    private String sapFieldName;
    @Column(name = "EASY_NAME")
    private String easyName;
    @Column(name = "TABLE_NAME")
    private String tableName;
    @Column(name = "FIELD_NAME")
    private String fieldName;
    @Column(name = "ODATA_SERVICE")
    private String odataService;
    @Column(name = "DATE_TYPE")
    private String dateType;

}
