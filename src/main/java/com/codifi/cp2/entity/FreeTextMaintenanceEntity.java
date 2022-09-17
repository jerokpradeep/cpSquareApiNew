package com.codifi.cp2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.codifi.cp2.model.request.CategoryAlignmentModel;
import com.codifi.cp2.model.request.DistributionListModel;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".FREE_TEXT_TRIGGER_DETAIL")
@TypeDef(name = "json", typeClass = JsonType.class)
public class FreeTextMaintenanceEntity extends CommonEntity {
    @Column(name = "FREE_TEXT_TRIGGER_ID", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private String freeTextTriggerId;
    @Column(name = "FREE_TEXT_TRIGGER_NAME")
    private String triggerNameEnglish;
    @Column(name = "FREE_TEXT_MESSAGE")
    private String description;
    @Column(name = "TEMPLATE_TYPE")
    private String template;
    @Column(name = "DATE_ACTIVATED")
    private String dateActivated;
    @Column(name = "ACTIVATED_BY")
    private String activatedBy;
    @Column(name = "DATE_DEACTIVATED")
    private String dateDeactivated;
    @Column(name = "DEACTIVATED_BY")
    private String deactivatedBy;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "DEVELOPMENT_PROGRESS")
    private int developmentProgress;
    @Column(name = "DISTRIBUTION_LIST")
    private boolean distributionList;
    @Column(name = "CATEGORY_ALIGNMENT")
    private boolean categoryAlignment;
    @Column(name = "STREAM_NAME")
    private String streamName;
    @Column(name = "RECEIVER_NAME")
    private String receiverName;
    @Column(name = "TRIGGER_NAME_GERMAN")
    private String triggerNameGerman;
    @Type(type = "json")
    @Column(name = "GROUP_NAME", columnDefinition = "json")
    private List<DistributionListModel> distributionName;
    @Type(type = "json")
    @Column(name = "CATEGORY_ALIGNMENT_JSON", columnDefinition = "json")
    private List<CategoryAlignmentModel> categoryJson;

}
