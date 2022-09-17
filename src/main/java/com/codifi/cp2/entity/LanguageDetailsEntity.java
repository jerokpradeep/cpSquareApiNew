package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".LANGUAGE_DETAILS")
public class LanguageDetailsEntity extends CommonEntity {
    @Column(name = "LANGUAGE")
    private String language;
    @Column(name = "SHORT_FORM")
    private String shortForm;
}
