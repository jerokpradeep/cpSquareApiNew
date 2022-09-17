package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".LANGUAGE_TRANSLATION_DETAILS")
public class TranslateEntity extends CommonEntity {
    @Column(name = "TEXT_NAME")
    private String textName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PAGE_ID")
    private Long pageId;
    @Column(name = "LANGUAGE_ID")
    private Long languageId;
    @Column(name = "TEXT_ID")
    private Long textId;

}
