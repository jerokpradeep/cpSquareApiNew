package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".CONDITION_LIST")
public class ConditionListEntity extends CommonEntity {
    @Column(name = "c_id")
    private int cId;
    @Column(name = "c_name")
    private String cName;
    @Column(name = "table_field_name")
    private String tableFieldName;
}
