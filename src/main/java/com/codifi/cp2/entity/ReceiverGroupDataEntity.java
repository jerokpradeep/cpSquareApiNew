package com.codifi.cp2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "\"sap_cp2\".RECEIVER_GROUP_DATA")
public class ReceiverGroupDataEntity extends CommonEntity {
    @Column(name = "AGE_RESTRICTION_DESCRIPTION")
    private String ageRestrictionDescription;
    @Column(name = "AGE_RESTRICTION_ID")
    private String ageRestrictionId;
    @Column(name = "AGE_RESTRICTION_RELEVANT")
    private String ageRestrictionRelevant;
    @Column(name = "ALDI_COMMODITY_GROUP")
    private String aldiCommodityGroup;
    @Column(name = "ALDI_COMMODITY_GROUP_CODE")
    private String aldiCommodityGroupCode;
    @Column(name = "ALDI_COMMODITY_GROUP_DESCRIPTION")
    private String aldiCommodityGroupDescription;
    @Column(name = "ALDI_SUB_COMMODITY_GROUP")
    private String aldiSubCommodityGroup;
    @Column(name = "APPROVAL_STATUS")
    private String approvalStatus;
    @Column(name = "APPROVED_BY")
    private String approvedBy;
    @Column(name = "APPROVED_DATE")
    private String approvedDate;
    @Column(name = "ARTICLE_BRAND_DESCRIPTION")
    private String articleBrandDescription;
    @Column(name = "ARTICLE_BRAND_ID")
    private String articleBrandId;
    @Column(name = "ARTICLE_CATEGORY")
    private String articleCategory;
    @Column(name = "ARTICLE_CLASS")
    private String articleClass;
    @Column(name = "ARTICLE_STATUS")
    private String articleStatus;
    @Column(name = "ARTICLE_TYPE")
    private String articleType;
    @Column(name = "ARTICLE_TYPE_ZCODE_DESCRIPTION")
    private String articleTypeZcodeDescription;
    @Column(name = "ASSIGNED_BY")
    private String assignedBy;
    @Column(name = "ASSIGNED_TO")
    private String assignedTo;
    @Column(name = "ATTACHMENT")
    private String attachment;
    @Column(name = "ATTACHMENT_COUNT")
    private String attachmentCount;
    @Column(name = "ATTACHMENTS_YN")
    private boolean attachmentsYn;
    @Column(name = "AUTOMATED_TRIGGER_OR_FREE_TEXT")
    private String automatedTriggerOrFreeText;
    @Column(name = "BASE_UNIT_OF_MEASURE_DESCRIPTION")
    private String baseUnitOfMeasureDescription;
    @Column(name = "BASE_UNIT_OF_MEASURE_UOM")
    private String baseUnitOfMeasureUom;
    @Column(name = "CASE_DIMENSION_TABLE_HEIGHT_NEW")
    private String caseDimensionTableHeightNew;
    @Column(name = "CASE_DIMENSION_TABLE_HEIGHT_OLD")
    private String caseDimensionTableHeightOld;
    @Column(name = "CASE_DIMENSION_TABLE_LENGTH_NEW")
    private String caseDimensionTableLengthNew;
    @Column(name = "CASE_DIMENSION_TABLE_LENGTH_OLD")
    private String caseDimensionTableLengthOld;
    @Column(name = "CASE_DIMENSION_TABLE_UOM_NEW")
    private String caseDimensionTableUomNew;
    @Column(name = "CASE_DIMENSION_TABLE_UOM_OLD")
    private String caseDimensionTableUomOld;
    @Column(name = "CASE_DIMENSION_TABLE_WIDTH_NEW")
    private String caseDimensionTableWidthNew;
    @Column(name = "CASE_DIMENSION_TABLE_WIDTH_OLD")
    private String caseDimensionTableWidthOld;
    @Column(name = "CASE_WEIGHT_TABLE_GROSS_WEIGHT_NEW")
    private String caseWeightTableGrossWeightNew;
    @Column(name = "CASE_WEIGHT_TABLE_NET_WEIGHT_NEW")
    private String caseWeightTableNetWeightNew;
    @Column(name = "CASE_WEIGHT_TABLE_NET_WEIGHT_OLD")
    private String caseWeightTableNetWeightOld;
    @Column(name = "CASE_WEIGHT_TABLE_UOM_NEW")
    private String caseWeightTableUomNew;
    @Column(name = "CASE_WEIGHT_TABLE_UOM_OLD")
    private String caseWeightTableUomOld;
    @Column(name = "CASES_PER_LAYER")
    private String casesPerLayer;
    @Column(name = "CLAIMED_BY")
    private String claimedBy;
    @Column(name = "COLOR")
    private String color;
    @Column(name = "COMMODITY_CODE")
    private String commodityCode;
    @Column(name = "COMMODITY_CODE_DESCRIPTION")
    private String commodityCodeDescription;
    @Column(name = "COMPARISON_PRICE_NET_WEIGHT_NUMBER")
    private String comparisonPriceNetWeightNumber;
    @Column(name = "COMPARISON_PRICE_UNIT_CONTENT_UNIT")
    private String comparisonPriceUnitContentUnit;
    @Column(name = "COMPARISON_PRICE_UNIT_NUMBER")
    private String comparisonPriceUnitNumber;
    @Column(name = "CONTENT_UNIT")
    private String contentUnit;
    @Column(name = "DANGEROUS_GOODS_DESCRIPTION")
    private String dangerousGoodsDescription;
    @Column(name = "DANGEROUS_GOODS_INDICATOR")
    private String dangerousGoodsIndicator;
    @Column(name = "DC_ARTICLE_STATUS")
    private String dcArticleStatus;
    @Column(name = "DC_ARTICLE_STATUS_VALID_FROM_DATE")
    private String dcArticleStatusValidFromDate;
    @Column(name = "DC_LISTING_END_DATE")
    private String dcListingEndDate;
    @Column(name = "DC_LISTING_START_DATE")
    private String dcListingStartDate;
    @Column(name = "DISPLAY_ID")
    private int displayId;
    @Column(name = "DISPLAY_ID_DESCRIPTION")
    private String displayIdDescription;
    @Column(name = "DROP_SHIP_INDICATOR_YN")
    private String dropShipIndicatorYn;
    @Column(name = "EMBEDDED_ATTACHMENTS")
    private String embeddedAttachments;
    @Column(name = "GENERIC_ID")
    private String genericId;
    @Column(name = "GENERIC_ID_DESCRIPTION")
    private String genericIdDescription;
    @Column(name = "GROSS_WEIGHT_OLD")
    private String grossWeightOld;
    @Column(name = "HAZARDOUS_RESTRICTION")
    private String hazardousRestriction;
    @Column(name = "INCOTERM_LOC_NEW")
    private String incotermLocNew;
    @Column(name = "INCOTERM_LOC_OLD")
    private String incotermLocOld;
    @Column(name = "INCOTERM_NEW")
    private String incotermNew;
    @Column(name = "INCOTERM_OLD")
    private String incotermOld;
    @Column(name = "IS_CLAIMED")
    private boolean isClaimed;
    @Column(name = "IS_READ")
    private boolean isRead;
    @Column(name = "LAST_CHANGED_DATE")
    private String lastChangedDate;
    @Column(name = "LEAD_TIME")
    private String leadTime;
    @Column(name = "LOADING_GROUP_CODE")
    private String loadingGroupCode;
    @Column(name = "LOADING_GROUP_DESCRIPTION")
    private String loadingGroupDescription;
    @Column(name = "MATNR")
    private String matnr;
    @Column(name = "MESSAGE_BODY")
    private String messageBody;
    @Column(name = "MESSAGE_NUMBER")
    private String messageNumber;
    @Column(name = "OA_ITEM_NUMBER")
    private String oaItemNumber;
    @Column(name = "ORDER_AREA_DESCRIPTION")
    private String orderAreaDescription;
    @Column(name = "ORDER_AREA_ID")
    private String orderAreaId;
    @Column(name = "OTHER_AREA")
    private String otherArea;
    @Column(name = "OUTLINE_AGREEMENT")
    private String outlineAgreement;
    @Column(name = "OUTLINE_AGREEMENT_DATE_VALID_FROM")
    private String outlineAgreementDateValidFrom;
    @Column(name = "OUTLINE_AGREEMENT_DATE_VALID_TO")
    private String outlineAgreementDateValidTo;
    @Column(name = "OUTLINE_AGREEMENT_NUMBER")
    private String outlineAgreementNumber;
    @Column(name = "PALETTE_LAYER_COUNT")
    private String paletteLayerCount;
    @Column(name = "PALETTE_TYPE")
    private String paletteType;
    @Column(name = "PALETTE_TYPE_DESCRIPTION")
    private String paletteTypeDescription;
    @Column(name = "PO_ITEM_NUMBER")
    private String poItemNumber;
    @Column(name = "PO_NUMBER")
    private String poNumber;
    @Column(name = "POSTED_DATE")
    private String postedDate;
    @Column(name = "POSTED_ON")
    private String postedOn;
    @Column(name = "POSTPONE_DATE")
    private String postponeDate;
    @Column(name = "POSTPONE_TIME")
    private String postponeTime;
    @Column(name = "PRODUCT_HIERARCHY_CATEGORY")
    private String productHierarchyCategory;
    @Column(name = "PRODUCT_HIERARCHY_CODE")
    private String productHierarchyCode;
    @Column(name = "PRODUCT_HIERARCHY_DESCRIPTION")
    private String productHierarchyDescription;
    @Column(name = "PRODUCT_HIERARCHY_NODE")
    private String productHierarchyNode;
    @Column(name = "PROMOTION_ID")
    private String promotionId;
    @Column(name = "PROMOTION_ID_DATE_VALID_FROM")
    private String promotionIdDateValidFrom;
    @Column(name = "PROMOTION_ID_DATE_VALID_TO")
    private String promotionIdDateValidTo;
    @Column(name = "PROMOTION_ID_VALID_FOR_DC")
    private String promotionIdValidForDc;
    @Column(name = "RECEIVED_REQUEST")
    private boolean receivedRequest;
    @Column(name = "RECEIVER_GROUP_ID")
    private String receiverGroupId;
    @Column(name = "RECEIVER_GROUP_NAME")
    private String receiverGroupName;
    @Column(name = "REGIONS")
    private String regions;
    @Column(name = "REM_SHELF_LIFE")
    private String remShelfLife;
    @Column(name = "RETAIL_PRICE_CONDITION_AMOUNT")
    private String retailPriceConditionAmount;
    @Column(name = "RETAIL_PRICE_CONDITION_VALID_FROM")
    private String retailPriceConditionValidFrom;
    @Column(name = "RETAIL_PRICE_CONDITION_VALID_TO")
    private String retailPriceConditionValidTo;
    @Column(name = "SENT_FOR_APPROVAL")
    private boolean sentForApproval;
    @Column(name = "SENT_ON_DATE")
    private String sentOnDate;
    @Column(name = "SHELF_LIFE")
    private String shelfLife;
    @Column(name = "SHELF_LIFE_UOM")
    private String shelfLifeUom;
    @Column(name = "SINGLE_ARTICLE")
    private String singleArticle;
    @Column(name = "SINGLE_ARTICLE_DESCRIPTION")
    private String singleArticleDescription;
    @Column(name = "STATUS_MESSAGE")
    private String statusMessage;
    @Column(name = "STOCK_PLANNER_CODE")
    private String stockPlannerCode;
    @Column(name = "SUBSTITUTION_ARTICLE")
    private String substitutionArticle;
    @Column(name = "SUBSTITUTION_ARTICLE_DESCRIPTION")
    private String substitutionArticleDescription;
    @Column(name = "SUPPLIER_DESCRIPTION")
    private String supplierDescription;
    @Column(name = "SUPPLIER_ID")
    private int supplierId;
    @Column(name = "SYSTEM_SEND_DATE")
    private String systemSendDate;
    @Column(name = "TEMP_ZONE")
    private String tempZone;
    @Column(name = "TEMPERATURE_CONDITION_HIGHER_CODE")
    private String temperatureConditionHigherCode;
    @Column(name = "TEMPERATURE_CONDITION_HIGHER_DESCRIPTION")
    private String temperatureConditionHigherDescription;
    @Column(name = "TEMPERATURE_CONDITION_LOWER_CODE")
    private String temperatureConditionLowerCode;
    @Column(name = "TEMPERATURE_CONDITION_LOWER_DESCRIPTION")
    private String temperatureConditionLowerDescription;
    @Column(name = "TO_BE_SENT_ON")
    private String toBeSentOn;
    @Column(name = "TOTAL_SHELF_LIFE")
    private String totalShelfLife;
    @Column(name = "TRANSPORT_GROUP")
    private String transportGroup;
    @Column(name = "TRANSPORTATION_GROUP_DESCRIPTION")
    private String transportationGroupDescription;
    @Column(name = "TRIGGER_EVENT_ID")
    private String triggerEventId;
    @Column(name = "TRIGGER_EVENT_NAME")
    private String triggerEventName;
    @Column(name = "UNITS_PER_PALLET_TYPE")
    private String unitsPerPalletType;
    @Column(name = "UOM_GROUP_DC_SUPPLIER_LEVEL")
    private String uomGroupDcSupplierLevel;
    @Column(name = "VALID_FOR_DC")
    private String validForDc;
    @Column(name = "VARIANT_ID_1")
    private String variantId1;
    @Column(name = "VARIANT_ID_1_DESCRIPTION")
    private String variantId1Description;
    @Column(name = "VARIANT_ID_2")
    private String variantId2;
    @Column(name = "VARIANT_ID_2_DESCRIPTION")
    private String variantId2Description;
    @Column(name = "VARIANT_ID_3")
    private String variantId3;
    @Column(name = "VARIANT_ID_3_DESCRIPTION")
    private String variantId3Description;
    @Column(name = "VARIANT_ID_4")
    private String variantId4;
    @Column(name = "VARIANT_ID_4_DESCRIPTION")
    private String variantId4Description;
    @Column(name = "VARIANTS")
    private String variants;
    @Column(name = "WARRANTY_YN")
    private boolean warrantyYn;
    @Transient
    private List<String> messageIds;
    @Transient
    private String action;

    // @Column(name = "CASE_DIMENSION")
    // private String caseDimension;
    // @Column(name = "CASE_WEIGHT")
    // private String caseWeight;
    // @Column(name = "CHANGES")
    // private String changes;
    // @Column(name = "DC_LEVEL_INFO")
    // private String dcLevelInfo;
    // @Column(name = "INCOTERM")
    // private String incoterm;
    // @Column(name = "NOTES")
    // private String notes;
    // @Column(name = "PURCHASE_GROUP")
    // private String purchaseGroup;
    // @Column(name = "VARIANT_INFORMATION")
    // private String variantInformation;

}
