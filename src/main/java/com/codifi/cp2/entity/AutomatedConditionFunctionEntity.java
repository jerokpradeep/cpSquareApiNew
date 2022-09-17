package com.codifi.cp2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.codifi.cp2.model.request.ConditionFunctionModel;
import com.codifi.cp2.model.request.OdataCondFuncModel;
import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".AUTOMATED_TRIGGER_FUNCTION")
@TypeDef(name = "json", typeClass = JsonType.class)
public class AutomatedConditionFunctionEntity extends CommonEntity {
    @Column(name = "AUTOMATED_TRIGGER_ID")
    private String automatedTriggerId;
    @Column(name = "AUTOMATED_TRIGGER_NAME")
    private String automatedTriggerName;
    @Transient
    private List<ConditionFunctionModel> conditionFunction;
    @Type(type = "json")
    @Column(name = "condition_function_json", columnDefinition = "json")
    private OdataCondFuncModel conditionArray;
}
