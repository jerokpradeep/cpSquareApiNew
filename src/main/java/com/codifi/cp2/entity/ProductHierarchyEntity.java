package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"sap_cp2\".PRODUCT_HIERARCHY")
public class ProductHierarchyEntity extends CommonEntity {
    @Column(name = "PRODUCT_HIERARCHY_ID")
    private int productHierarchyId;
    @Column(name = "PRODUCT_HIERARCHY_NAME")
    private String productHierarchyName;
}
