package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".HELP_DETAILS")
public class HelpEntity extends CommonEntity {
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PAGE_ID")
    private Long pageId;
    @Column(name = "LANGUAGE_ID")
    private Long languageId;

}
