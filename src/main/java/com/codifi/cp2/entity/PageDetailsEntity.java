package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".PAGE_DETAILS")
public class PageDetailsEntity extends CommonEntity {
    @Column(name = "PAGE_NAME")
    private String pageName;
    @Column(name = "DESCRIPTION")
    private String description;
}
