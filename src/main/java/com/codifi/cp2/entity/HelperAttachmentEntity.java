package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".HELPER_ATTACHMENT_DETAILS")
public class HelperAttachmentEntity extends CommonEntity {
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PAGE_ID")
    private Long pageId;
    @Column(name = "LANGUAGE_ID")
    private Long languageId;
    @Column(name = "ATTACHMENT")
    private String attachment;
    @Column(name = "NAME")
    private String name;
    @Column(name = "FILE_TYPE")
    private String fileType;

}
