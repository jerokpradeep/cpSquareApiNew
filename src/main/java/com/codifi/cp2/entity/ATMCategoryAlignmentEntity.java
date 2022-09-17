package com.codifi.cp2.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.Getter;
import lombok.Setter;

import com.codifi.cp2.model.request.CategoryAlignmentModel;
import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".AUTOMATED_TRIGGER_FIELD_CATEGORY_ALIGNMENT")
@TypeDef(name = "json", typeClass = JsonType.class)
public class ATMCategoryAlignmentEntity extends CommonEntity {
    @Column(name = "AUTOMATED_TRIGGER_ID")
    private String automatedTriggerId;
    @Column(name = "AUTOMATED_TRIGGER_NAME")
    private String automatedTriggerName;
    @Column(name = "POSITION_ID")
    private String positionId;
    @Column(name = "POSITION_VALUE")
    private String positionValue;
    @Column(name = "DATA_CATEGORY")
    private String dataCategory;
    @Type(type = "json")
    @Column(name = "CATEGORY_JSON", columnDefinition = "json")
    private List<CategoryAlignmentModel> categoryJson;
}
