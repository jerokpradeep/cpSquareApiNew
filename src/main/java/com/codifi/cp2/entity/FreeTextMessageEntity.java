package com.codifi.cp2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.codifi.cp2.model.request.DcListingModel;
import com.codifi.cp2.model.request.DistributionListModel;
import com.codifi.cp2.model.request.FTWDistributionListModel;
import com.codifi.cp2.model.request.FTWReceiverGroupModel;
import com.codifi.cp2.model.request.FTWReceiverJsonModel;
import com.codifi.cp2.model.request.ReceiveUserModel;
import com.vladmihalcea.hibernate.type.json.JsonType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"sap_cp2\".FREE_TEXT_MESSAGE")
@TypeDef(name = "json", typeClass = JsonType.class)
public class FreeTextMessageEntity extends CommonEntity {
    @Column(name = "RECEIVER_GROUP_NAME")
    private String receiverGroupName;
    @Column(name = "MESSAGE_NUMBER")
    private String messageNumber;
    @Column(name = "MESSAGE_NAME")
    private String messageName;
    @Column(name = "FREE_TEXT_EVENT")
    private String freeTextEvent;
    @Column(name = "TEMPLATE")
    private String template;
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "APPROVED_ON")
    private String approvedOn;
    @Column(name = "ARTICLE_BRAND_ID_AND_DESCRIPTION")
    private String articleBrandIdAndDescription;
    @Column(name = "ARTICLE_STATUS_ON_DC_LEVEL")
    private String articleStatusOnDcLevel;
    @Column(name = "ARTICLE_SUBstitution_substitution_id_and_description")
    private String articleSubstitutionSubstitutionIdAndDescription;
    @Column(name = "ARTICLE_TYPE_ZCODE_AND_DESCRIPTION")
    private String articleTypeZcodeAndDescription;
    @Column(name = "BASE_UNIT_OF_MEASURE")
    private String baseUnitOfMeasure;
    @Column(name = "COMMODITY_CODE_AND_DESCRIPTION")
    private String commodityCodeAndDescription;
    @Column(name = "COMPARISON_PRICE_UNIT")
    private String comparisonPriceUnit;
    @Column(name = "DANGEROUS_GOODS")
    private String dangerousGoods;
    @Column(name = "DC_LISTING_END_DATE")
    private String dcListingEndDate;
    @Column(name = "DC_LISTING_START_DATE")
    private String dcListingStartDate;
    @Column(name = "DISPLAY_ID_AND_DESCRIPTION")
    private String displayIdAndDescription;
    @Column(name = "GENERIC_ID_AND_DESCRIPTION")
    private String genericIdAndDescription;
    @Column(name = "INCOTERM")
    private String incoterm;
    @Column(name = "LOADING_GROUP_AND_DESCRIPTION")
    private String loadingGroupAndDescription;
    @Column(name = "MEMO")
    private String memo;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "MESSAGE_DEVELOPMENT_STATUS")
    private String messageDevelopmentStatus;
    @Column(name = "NOTES")
    private String notes;
    @Column(name = "OA_ITEM_NUMBER")
    private String oaItemNumber;
    @Column(name = "OUTLINE_AGREEMENT")
    private String outlineAgreement;
    @Column(name = "OUTLINE_AGREEMENT_DATE_RANGE")
    private String outlineAgreementDateRange;
    @Column(name = "OUTLINE_AGREEMENT_NUMBER")
    private String outlineAgreementNumber;
    @Column(name = "PO_ITEM_NUMBER")
    private String poItemNumber;
    @Column(name = "PO_NUMBER")
    private String poNumber;
    @Column(name = "POSTED_DATE")
    private String postedDate;
    @Column(name = "PRODUCT_HIERARCHY_AND_DESCRIPTION")
    private String productHierarchyAndDescription;
    @Column(name = "PRODUCT_HIERARCHY_CATEGORY_NODE_AND_DESCRIPTION")
    private String productHierarchyCategoryNodeAndDescription;
    @Column(name = "PRODUCT_HIERARCHY_MAIN_GROUP_AND_DESCRIPTION")
    private String productHierarchyMainGroupAndDescription;
    @Column(name = "REGION")
    private String region;
    @Column(name = "REMAINING_SHELF_LIFE_AND_UOM")
    private String remainingShelfLifeAndUom;
    @Column(name = "SENDER_APPROVED_REJECTED_BY")
    private String senderApprovedRejectedBy;
    @Column(name = "SENDER_APPROVED_REJECTED_ON")
    private String senderApprovedRejectedOn;
    @Column(name = "SENDER_APPROVED_STATUS")
    private String senderApprovedStatus;
    @Column(name = "SENDER_LAST_STATUS_CHANGE_BY")
    private String senderLastStatusChangeBy;
    @Column(name = "SENDER_LAST_STATUS_CHANGE_DATE")
    private String senderLastStatusChangeDate;
    @Column(name = "SENT_ON_DATE")
    private String sentOnDate;
    @Column(name = "SHELF_LIFE_AND_UOM")
    private String shelfLifeAndUom;
    @Column(name = "STOCK_PLANNER")
    private String stockPlanner;
    @Column(name = "STOCK_PLANNER_CODE")
    private String stockPlannerCode;
    @Column(name = "SUPPLIER_ID_AND_SUPPLIER_NAME")
    private String supplierIdAndSupplierName;
    @Column(name = "TEMPERATURE_ZONE_AND_DESCRIPTION")
    private String temperatureZoneAndDescription;
    @Column(name = "TO_BE_SENT_ON")
    private String toBeSentOn;
    @Column(name = "TOTAL_SHELF_LIFE_AND_UOM")
    private String totalShelfLifeAndUom;
    @Column(name = "TRANSPORTATION_GROUP_AND_DESCRIPTION")
    private String transportationGroupAndDescription;
    @Column(name = "VARIANT_IDS_AND_DESCRIPTIONS")
    private String variantIdsAndDescriptions;
    @Column(name = "ATTACHMENT_COUNT")
    private int attachmentCount;
    @Column(name = "DISPLAY_ID")
    private int displayId;
    @Column(name = "SUPPLIER_ID")
    private int supplierId;
    @Column(name = "IS_READ")
    private boolean isRead;
    @Type(type = "json")
    @Column(name = "DC", columnDefinition = "json")
    private List<DcListingModel> dc;
    @Type(type = "json")
    @Column(name = "DISTRIBUTION_LIST", columnDefinition = "json")
    private List<FTWDistributionListModel> distributionList;
    @Transient
    private List<String> messageIds;
    @Transient
    private String action;
    @Type(type = "json")
    @Column(name = "RECEIVER_GROUP_JSON", columnDefinition = "json")
    private FTWReceiverJsonModel receiverGroupJson;
    @Column(name = "ATTACHMENTS")
    private String attachments;
    @Column(name = "ATTACHMENT_NAME")
    private String attachmentName;
    @Transient
    private List<FTWReceiverGroupModel> rgmUserJson;
    @Transient
    private List<DistributionListModel> rgmData;
    // @Column(name="dc_impacted")
    // private String dc_impacted;
    // private json distribution_list;
    // @Column(name="distribution_list")
    // @Column(name = "PURCHASE_GROUP")
    // private json purchaseGroup;
    // @Column(name="streams_distribution")
    // private json streams_distribution;
}
