package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "\"sap_cp2\".ORDER_AREA_LIST")
public class OrderAreaListEntity extends CommonEntity {
    @Column(name = "ORDER_AREA_ID")
    private int orderAreaId;
    @Column(name = "ORDER_AREA_NAME")
    private String orderAreaName;
    @Column(name = "DC_ID")
    private String dcId;
}
