package com.codifi.cp2.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.Getter;
import lombok.Setter;

import com.codifi.cp2.model.request.CdhdrJsonModel;
import com.codifi.cp2.model.request.CdposJsonModel;
import com.codifi.cp2.model.request.CdposObjectIdJsonModel;
import com.codifi.cp2.model.request.CdposTabkeyJsonModel;
import com.codifi.cp2.model.request.OdataCdhdrJsonModel;
import com.codifi.cp2.model.request.OdataCdposJsonModel;
import com.codifi.cp2.model.request.OdataCdposObjJsonModel;
import com.codifi.cp2.model.request.OdataCdposTabkeyJsonModel;
import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".AUTOMATED_TRIGGER_CD_TABLE")
@TypeDef(name = "json", typeClass = JsonType.class)
public class AutomatedTriggerCDTableEntity extends CommonEntity {
    @Column(name = "AUTOMATED_TRIGGER_ID")
    private String automatedTriggerId;
    @Column(name = "AUTOMATED_TRIGGER_NAME")
    private String automatedTriggerName;
    @Transient
    private List<CdhdrJsonModel> hdrList;
    @Transient
    private List<CdposJsonModel> posList;
    @Transient
    private List<CdposObjectIdJsonModel> objectList;
    @Transient
    private List<CdposTabkeyJsonModel> tabList;
    @Type(type = "json")
    @Column(name = "s4_cdhdr_json", columnDefinition = "json")
    private OdataCdhdrJsonModel cdhdrJson;
    @Type(type = "json")
    @Column(name = "s4_cdpos_json", columnDefinition = "json")
    private OdataCdposJsonModel cdposJson;
    @Type(type = "json")
    @Column(name = "cdpos_objectid_json", columnDefinition = "json")
    private OdataCdposObjJsonModel objectsJson;
    @Type(type = "json")
    @Column(name = "cdpos_tabkey_json", columnDefinition = "json")
    private OdataCdposTabkeyJsonModel tabKeyJson;

}
