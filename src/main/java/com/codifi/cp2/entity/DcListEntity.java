package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".DC_LIST")
public class DcListEntity extends CommonEntity {
    @Column(name = "DC_ID")
    private int dcId;
    @Column(name = "DC_NAME")
    private String dcName;
}
