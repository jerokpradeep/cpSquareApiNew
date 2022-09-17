package com.codifi.cp2.helper;

import java.util.UUID;

import com.codifi.cp2.entity.ATMCategoryAlignmentEntity;
import com.codifi.cp2.entity.FieldListCategoryEntity;
import com.codifi.cp2.entity.ReceiverGroupDataEntity;
import com.codifi.cp2.model.request.FieldListModel;
import com.codifi.cp2.repository.ATMCategoryAlignmentRepository;
import com.codifi.cp2.repository.AutomatedConditionFunctionRepository;
import com.codifi.cp2.repository.FieldListCategoryRepository;
import com.codifi.cp2.util.CommonMethods;
import com.codifi.cp2.util.DateUtil;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RGWHelper {

    @Autowired
    ATMCategoryAlignmentRepository atmCategoryAlignmentRepository;
    @Autowired
    FieldListCategoryRepository fieldListCategoryRepository;

    /**
     * Method to populate message Data by receiver Group Data Entity
     * 
     * @return
     */
    public JSONObject populateMessageData(ReceiverGroupDataEntity messageEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            JSONObject messeageObject = new JSONObject();
            messeageObject.put("triggerName", StringUtil.checkObjectNull(messageEntity.getTriggerEventName()));
            messeageObject.put("leadTime", StringUtil.checkObjectNull(messageEntity.getLeadTime()));
            messeageObject.put("triggerMessage", StringUtil.checkObjectNull(messageEntity.getMessageBody()));
            messeageObject.put("status", StringUtil.checkObjectNull(messageEntity.getStatusMessage()));
            messeageObject.put("postponedDate", StringUtil.checkObjectNull(messageEntity.getPostponeDate()));
            messeageObject.put("postponedTime", StringUtil.checkObjectNull(messageEntity.getPostponeTime()));
            // common
            JSONArray displaySections = new JSONArray();
            JSONObject displayObject1 = new JSONObject();
            displayObject1.put("header", "Trigger Meta Data");
            displayObject1.put("key", "triggerMetaData");
            displayObject1.put("displayOrder", "1");
            displaySections.put(displayObject1);
            JSONObject displayObject2 = new JSONObject();
            displayObject2.put("header", "General Article Information");
            displayObject2.put("key", "generalArticleInformation");
            displayObject2.put("displayOrder", "2");
            displaySections.put(displayObject2);
            JSONObject displayObject3 = new JSONObject();
            displayObject3.put("header", "DC Level Information");
            displayObject3.put("key", "dcLevelInformation");
            displayObject3.put("displayOrder", "2");
            displaySections.put(displayObject3);
            // DC
            JSONArray dcArray = new JSONArray();
            JSONObject dc1 = new JSONObject();
            dc1.put("header", "DC");
            dc1.put("key", "dc");
            dcArray.put(dc1);
            JSONObject dc2 = new JSONObject();
            dc2.put("header", "DC Article Status");
            dc2.put("key", "dcArticleStatus");
            dcArray.put(dc2);
            JSONObject dc3 = new JSONObject();
            dc3.put("header", "DC Article Status Valid From Date");
            dc3.put("key", "validFromDate");
            dcArray.put(dc3);
            JSONObject dc4 = new JSONObject();
            dc4.put("header", "DC Listing Date From");
            dc4.put("key", "dcListingFromDate");
            dcArray.put(dc4);
            JSONObject dc5 = new JSONObject();
            dc5.put("header", "DC Listing Date To");
            dc5.put("key", "dcListingToDate");
            dcArray.put(dc5);
            // Set values for DC
            JSONObject dcObject = new JSONObject();
            dcObject.put("fields", dcArray);
            JSONArray dcArr = new JSONArray();
            JSONObject dcValues = new JSONObject();
            dcValues.put("dc", StringUtil.checkObjectNull(messageEntity.getValidForDc()));
            dcValues.put("dcArticleStatus", StringUtil.checkObjectNull(messageEntity.getDcArticleStatus()));
            dcValues.put("validFromDate", StringUtil.checkObjectNull(messageEntity.getDcArticleStatusValidFromDate()));
            dcValues.put("dcListingFromDate", StringUtil.checkObjectNull(messageEntity.getDcListingStartDate()));
            dcValues.put("dcListingToDate", StringUtil.checkObjectNull(messageEntity.getDcListingEndDate()));
            dcArr.put(dcValues);
            dcObject.put("values", dcArr);
            // generalArticleInformation
            JSONArray gaiArray = new JSONArray();
            JSONObject gaiArrayfields1 = new JSONObject();
            gaiArrayfields1.put("header", "Generic");
            gaiArrayfields1.put("key", "generic");
            gaiArray.put(gaiArrayfields1);

            JSONObject gaiArrayfields2 = new JSONObject();
            gaiArrayfields2.put("header", "Single");
            gaiArrayfields2.put("key", "single");
            gaiArray.put(gaiArrayfields2);

            JSONObject gaiArrayfields3 = new JSONObject();
            gaiArrayfields3.put("header", "Product Hierarchy");
            gaiArrayfields3.put("key", "productHierarchy");
            gaiArray.put(gaiArrayfields3);

            JSONObject gaiArrayfields4 = new JSONObject();
            gaiArrayfields4.put("header", "Aldi Commodity Group");
            gaiArrayfields4.put("key", "aldiCommodityGroup");
            gaiArray.put(gaiArrayfields4);

            JSONObject gaiArrayfields5 = new JSONObject();
            gaiArrayfields5.put("header", "Article Band");
            gaiArrayfields5.put("key", "articleBand");
            gaiArray.put(gaiArrayfields5);

            JSONObject gaiArrayfields6 = new JSONObject();
            gaiArrayfields6.put("header", "Varient");
            gaiArrayfields6.put("key", "varient");

            JSONObject gaiArrayfields = new JSONObject();
            gaiArrayfields.put("fields", gaiArray);
            JSONObject gaivalues = new JSONObject();

            // setvalues generalArticleInformation
            gaivalues.put("generic", StringUtil.concatStringsbySplCharbtw(messageEntity.getGenericId(),
                    messageEntity.getGenericIdDescription(), "-"));

            gaivalues.put("single", StringUtil.concatStringsbySplCharbtw(messageEntity.getSingleArticle(),
                    messageEntity.getSingleArticleDescription(), "-"));
            gaivalues.put("productHierarchy", StringUtil.concatStringsbySplCharbtw(
                    messageEntity.getProductHierarchyCode(), messageEntity.getProductHierarchyCategory(), "-"));
            gaivalues.put("aldiCommodityGroup", StringUtil.concatStringsbySplCharbtw(
                    messageEntity.getAldiCommodityGroupCode(), messageEntity.getAldiCommodityGroupDescription(), "-"));
            gaivalues.put("articleBand", StringUtil.concatStringsbySplCharbtw(messageEntity.getArticleBrandId(),
                    messageEntity.getArticleBrandDescription(), "-"));
            gaivalues.put("varient", ""); // json
            gaiArrayfields.put("values", gaivalues);
            // triggerMetaData
            JSONArray tmdArray = new JSONArray();
            JSONObject fields1 = new JSONObject();
            fields1.put("header", "Message Status");
            fields1.put("key", "messageStatus");
            tmdArray.put(fields1);

            JSONObject fields2 = new JSONObject();
            fields2.put("header", "Message Number");
            fields2.put("key", "messageNumber");
            tmdArray.put(fields2);

            JSONObject fields3 = new JSONObject();
            fields3.put("header", "Display");
            fields3.put("key", "display");
            tmdArray.put(fields3);
            JSONObject fields4 = new JSONObject();
            fields4.put("header", "DC");
            fields4.put("key", "dc");
            tmdArray.put(fields4);

            JSONObject fields5 = new JSONObject();
            fields5.put("header", "Date created");
            fields5.put("key", "createdDate");
            tmdArray.put(fields5);

            JSONObject fields6 = new JSONObject();
            fields6.put("header", "Time created");
            fields6.put("key", "createdTime");
            tmdArray.put(fields6);

            JSONObject fields7 = new JSONObject();
            fields7.put("header", "Created By");
            fields7.put("key", "createdBy");
            tmdArray.put(fields7);
            JSONObject tmdfields = new JSONObject();
            tmdfields.put("fields", tmdArray);
            JSONObject values = new JSONObject();
            // setvalues Trrigger meta data
            values.put("messageStatus", StringUtil.checkObjectNull(messageEntity.getStatusMessage()));
            values.put("messageNumber", StringUtil.checkObjectNull(messageEntity.getMessageNumber()));
            values.put("display", messageEntity.getDisplayId());
            values.put("dc", StringUtil.checkObjectNull(messageEntity.getValidForDc()));
            values.put("createdDate", DateUtil.getEpochTimeFromDate(messageEntity.getCreatedOn()));
            values.put("createdTime", DateUtil.getEpochTimeFromDate(messageEntity.getCreatedOn()));
            values.put("createdBy", StringUtil.checkObjectNull(messageEntity.getCreatedBy()));
            tmdfields.put("values", values);
            JSONArray tmdArray1 = new JSONArray();
            JSONObject tmdfields1 = new JSONObject();
            if (StringUtil.isNotNullOrEmpty(messageEntity.getTriggerEventId())) {
                ATMCategoryAlignmentEntity categoryAlignmentEntity = atmCategoryAlignmentRepository
                        .findAllByTriggerID(messageEntity.getTriggerEventId());
                if (categoryAlignmentEntity != null
                        && StringUtil.isListNotNullOrEmpty(categoryAlignmentEntity.getCategoryJson())) {
                    for (int i = 0; i < categoryAlignmentEntity.getCategoryJson().size(); i++) {
                        FieldListCategoryEntity entity = fieldListCategoryRepository.findByCategoryTechnicalName(
                                categoryAlignmentEntity.getCategoryJson().get(i).getCategoryTechnicalName());
                        if (entity != null && StringUtil.isListNotNullOrEmpty(entity.getFieldListing())) {
                            for (FieldListModel fieldListModel : entity.getFieldListing()) {
                                JSONObject fieldsOne = new JSONObject();
                                fieldsOne.put("header", fieldListModel.getFieldNameOne());
                                fieldsOne.put("key", fieldListModel.getFieldNameOne());
                                tmdArray1.put(fieldsOne);
                                JSONObject fieldsTwo = new JSONObject();
                                fieldsTwo.put("header", fieldListModel.getFieldNameTwo());
                                fieldsTwo.put("key", fieldListModel.getFieldNameTwo());
                                tmdArray1.put(fieldsTwo);
                                JSONObject fieldsThree = new JSONObject();
                                fieldsThree.put("header", fieldListModel.getFieldNameThree());
                                fieldsThree.put("key", fieldListModel.getFieldNameThree());
                                tmdArray1.put(fieldsThree);
                                JSONObject fieldsFour = new JSONObject();
                                fieldsFour.put("header", fieldListModel.getFieldNameFour());
                                fieldsFour.put("key", fieldListModel.getFieldNameFour());
                                tmdArray1.put(fieldsFour);
                                JSONObject fieldsFive = new JSONObject();
                                fieldsFive.put("header", fieldListModel.getFieldNameFive());
                                fieldsFive.put("key", fieldListModel.getFieldNameFive());
                                tmdArray1.put(fieldsFive);
                                JSONObject fieldsSix = new JSONObject();
                                fieldsSix.put("header", fieldListModel.getFieldNameSix());
                                fieldsSix.put("key", fieldListModel.getFieldNameSix());
                                tmdArray1.put(fieldsSix);
                                // tmdfields1.put("fields" + count, tmdArray1);
                            }
                        }
                    }
                    tmdfields1.put("fields", tmdArray1);
                }
            }
            // attachments
            // JSONArray attachments = new JSONArray();
            // JSONObject att = new JSONObject();
            // att.put("downloadLink",
            // "https://helloscp.cfapps.eu20.hana.ondemand.com/master/getMessageDetailsById/RG0006062025");
            // att.put("fileName", "ReceiverGroup.xlsx");
            // att.put("size", "15 MB");
            // attachments.put(att);
            // Map Objects

            messeageObject.put("attachments", StringUtil.checkObjectNull(messageEntity.getAttachment()));
            messeageObject.put("fileName", StringUtil.checkObjectNull(messageEntity.getEmbeddedAttachments()));
            messeageObject.put("displaySections", displaySections);
            messeageObject.put("triggerMetaData", tmdfields);
            messeageObject.put("testMetaData", tmdfields1);
            messeageObject.put("generalArticleInformation", gaiArrayfields);
            messeageObject.put("dcLevelInformation", dcObject);
            array.put(messeageObject);
            jsonObject.put("data", array);
            jsonObject.put("message", MessageConstants.SUCCESS);
            jsonObject.put("status", MessageConstants.SUCCESS_CODE);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Helper - populateMessageData");
        }
        return jsonObject;
    }
}