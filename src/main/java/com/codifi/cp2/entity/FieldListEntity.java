package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".FIELD_LIST")
public class FieldListEntity extends CommonEntity {
    @Column(name = "f_id")
    private int fId;
    @Column(name = "f_name")
    private String fName;

}
