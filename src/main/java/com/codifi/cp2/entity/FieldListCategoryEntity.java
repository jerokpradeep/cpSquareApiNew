package com.codifi.cp2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.codifi.cp2.model.request.FieldListModel;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".FIELD_LIST_CATEGORY")
@TypeDef(name = "json", typeClass = JsonType.class)
public class FieldListCategoryEntity extends CommonEntity {
    @Column(name = "CATEGORY_TECHNICAL_NAME")
    private String categoryTechnicalName;
    @Column(name = "CATEGORY_NAME_ON_MESSAGE")
    private String categoryNameOnMessage;
    @Column(name = "FIELD_NAME_ON_MESSAGE")
    private String fieldNameOnMessage;
    @Column(name = "TRIGGER_EVENT")
    private String triggerEvent;
    @Type(type = "json")
    @Column(name = "FIELD_LIST_NAME", columnDefinition = "json")
    private List<FieldListModel> fieldListing;

}
