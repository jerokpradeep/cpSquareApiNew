package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "\"sap_cp2\".COMMODITY_GROUP")
public class CommodityGroupEntity extends CommonEntity {
    @Column(name = "CG_ID")
    private int cgId;
    @Column(name = "COMMODITY_GROUP_NAME")
    private String commodityGroupName;
}