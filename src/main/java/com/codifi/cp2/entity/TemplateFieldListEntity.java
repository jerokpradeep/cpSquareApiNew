package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".TEMPLATE_FIELD_LIST")
public class TemplateFieldListEntity extends CommonEntity {
    @Column(name = "template_name")
    private String templateName;
    @Column(name = "field_name")
    private String fieldName;
}
