package com.codifi.cp2.helper;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codifi.cp2.entity.FreeTextMaintenanceEntity;
import com.codifi.cp2.entity.FreeTextMessageEntity;
import com.codifi.cp2.entity.ReceiverGroupMaintenanceEntity;
import com.codifi.cp2.entity.ReceiverGroupUserEntity;
import com.codifi.cp2.model.request.FTWDistributionListModel;
import com.codifi.cp2.model.response.FTWAdditionalFieldsModel;
import com.codifi.cp2.repository.FreeTextMaintenanceRepository;
import com.codifi.cp2.repository.FreeTextMessageRepository;
import com.codifi.cp2.repository.ReceiverGroupMaintenanceRepository;
import com.codifi.cp2.repository.ReceiverGroupUserRepository;
import com.codifi.cp2.util.DateUtil;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FTWHelper {
    @Autowired
    FreeTextMessageRepository freeTextMessageRepository;
    @Autowired
    FreeTextMaintenanceRepository freeTextMaintenanceRepository;
    @Autowired
    ReceiverGroupUserRepository receiverGroupUserRepository;
    @Autowired
    ReceiverGroupMaintenanceRepository groupMaintenanceRepository;

    /**
     * Method reteives the columns for respective templates
     * 
     * @param templateName
     * @return column information
     */
    public List<FTWAdditionalFieldsModel> constructAdditionalData(String templateName) {
        List<FTWAdditionalFieldsModel> additionalFieldsEntityList = new ArrayList<FTWAdditionalFieldsModel>();
        if (templateName != null && templateName.length() > 0) {
            String[] fieldDatas = templateName.split(",");
            for (int i = 0; i < fieldDatas.length; i++) {
                String data = fieldDatas[i].trim();
                switch (data) {
                case "Display ID":
                    FTWAdditionalFieldsModel additionalDisplay = new FTWAdditionalFieldsModel();
                    additionalDisplay.setKey("displayId");
                    additionalDisplay.setHeader("Display ID");
                    additionalFieldsEntityList.add(additionalDisplay);
                    break;

                case "Distribution Centre":
                    FTWAdditionalFieldsModel additionalDc = new FTWAdditionalFieldsModel();
                    additionalDc.setKey("dc");
                    additionalDc.setHeader("Distribution Centre");
                    additionalFieldsEntityList.add(additionalDc);
                    break;

                case "Supplier ID":
                    FTWAdditionalFieldsModel additionalSupplier = new FTWAdditionalFieldsModel();
                    additionalSupplier.setKey("supplierId");
                    additionalSupplier.setHeader("Supplier ID");
                    additionalFieldsEntityList.add(additionalSupplier);
                    break;

                case "Outline Agreement Number":
                    FTWAdditionalFieldsModel additionalOaNum = new FTWAdditionalFieldsModel();
                    additionalOaNum.setKey("outlineAgreementNumber");
                    additionalOaNum.setHeader("Outline Agreement Number");
                    additionalFieldsEntityList.add(additionalOaNum);
                    break;

                case "OA Item Number":
                    FTWAdditionalFieldsModel qaItemNum = new FTWAdditionalFieldsModel();
                    qaItemNum.setKey("oaItemNumber");
                    qaItemNum.setHeader("Outline Agreement Line Item");
                    additionalFieldsEntityList.add(qaItemNum);
                    break;

                case "Purchase Order Number":
                    FTWAdditionalFieldsModel poOrderNum = new FTWAdditionalFieldsModel();
                    poOrderNum.setKey("purchaseOrderNumber");
                    poOrderNum.setHeader("Purchase Order Number");
                    additionalFieldsEntityList.add(poOrderNum);
                    break;

                case "PO Item Number":
                    FTWAdditionalFieldsModel poItemNum = new FTWAdditionalFieldsModel();
                    poItemNum.setKey("purchaseOrderLineItem");
                    poItemNum.setHeader("Purchase Order Line Item");
                    additionalFieldsEntityList.add(poItemNum);
                    break;
                }
            }
        }
        return additionalFieldsEntityList;
    }

    /**
     * Method to construct Free Text Work bench data
     */
    public JSONArray populateFTWData(String fieldName, String triggerId, String senderStatus) {
        JSONArray array = new JSONArray();
        try {
            List<FreeTextMessageEntity> triggerdataModels = null;
            FreeTextMaintenanceEntity freeTextMaintenanceEntity = freeTextMaintenanceRepository
                    .findByTriggerId(triggerId);
            if (StringUtil.isNotNullOrEmpty(senderStatus)
                    && StringUtil.isEqual(senderStatus, MessageConstants.CONST_STATUS_AWAITING_APPROVAL)) {
                triggerdataModels = freeTextMessageRepository.getPendingApprovalMsgs(triggerId);
            } else {
                triggerdataModels = freeTextMessageRepository.findByTriggerId(triggerId);
            }
            if (StringUtil.isListNotNullOrEmpty(triggerdataModels)) {
                String triggerNameEng = triggerId;
                String triggerNameGer = triggerId;
                if (StringUtil.isNotNullOrEmpty(freeTextMaintenanceEntity.getTriggerNameEnglish())) {
                    triggerNameEng += " - " + freeTextMaintenanceEntity.getTriggerNameEnglish();
                }
                if (StringUtil.isNotNullOrEmpty(freeTextMaintenanceEntity.getTriggerNameGerman())) {
                    triggerNameGer += " - " + freeTextMaintenanceEntity.getTriggerNameGerman();
                }
                for (FreeTextMessageEntity dataModel : triggerdataModels) {
                    if (dataModel != null) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("triggerNameEnglish", StringUtil.checkObjectNull(triggerNameEng));
                        jsonObject.put("triggerNameGerman", StringUtil.checkObjectNull(triggerNameGer));
                        jsonObject.put("messageNumber", StringUtil.checkObjectNull(dataModel.getMessageNumber()));
                        jsonObject.put("displayId", dataModel.getDisplayId());
                        jsonObject.put("createdBy", StringUtil.checkObjectNull(dataModel.getCreatedBy()));
                        jsonObject.put("createdOn", DateUtil.getEpochTimeFromDate(dataModel.getCreatedOn()));
                        jsonObject.put("sendOnDate", StringUtil.checkObjectNull(dataModel.getSentOnDate()));
                        jsonObject.put("approvedOn", StringUtil.checkObjectNull(dataModel.getApprovedOn()));
                        jsonObject.put("approvedBy", StringUtil.checkObjectNull(dataModel.getApprovedBy()));
                        jsonObject.put("senderApprovedStatus",
                                StringUtil.checkObjectNull(dataModel.getSenderApprovedStatus()));
                        jsonObject.put("postedDate", StringUtil.checkObjectNull(dataModel.getPostedDate()));
                        jsonObject.put("isRead", dataModel.isRead());
                        jsonObject.put("supplierId", dataModel.getSupplierId());
                        jsonObject.put("outlineAgreementNumber",
                                StringUtil.checkObjectNull(dataModel.getOutlineAgreementNumber()));
                        jsonObject.put("oaItemNumber", StringUtil.checkObjectNull(dataModel.getOaItemNumber()));
                        jsonObject.put("poNumber", StringUtil.checkObjectNull(dataModel.getPoNumber()));
                        jsonObject.put("poItemNumber", StringUtil.checkObjectNull(dataModel.getPoItemNumber()));
                        jsonObject.put("dcs",
                                StringUtil.isListNotNullOrEmpty(dataModel.getDc()) ? dataModel.getDc() : "");
                        jsonObject.put("distributionCenter",
                                StringUtil.isListNotNullOrEmpty(dataModel.getDistributionList())
                                        ? dataModel.getDistributionList()
                                        : "");
                        array.put(jsonObject);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return array;
        }
        return array;
    }

    public String generateMessageNumber(String triggerId) {
        long epoch = Instant.now().toEpochMilli();
        Long maxCount = freeTextMessageRepository.getMaxId();
        int maxRecord = 0;
        if (maxCount != null) {
            maxRecord = maxCount.intValue();
        }
        maxRecord = maxRecord + 1;
        String formatted = String.format("%010d", maxRecord);
        String messageNumber = triggerId + String.valueOf(epoch) + formatted;
        return messageNumber;
    }

    public JSONObject constructTemplateValues(List<String> templates, FreeTextMessageEntity freeTextMessageEntity,
            JSONObject jsonObject) {
        for (String template : templates) {
            switch (template) {
            case "Display ID":
                jsonObject.put("DisplayId", freeTextMessageEntity.getDisplayId());
            case "Distribution Centre":
                jsonObject.put("DistributionCentre", freeTextMessageEntity.getDc());
            case "Supplier ID":
                jsonObject.put("SupplierId", freeTextMessageEntity.getSupplierId());
            case "Outline Agreement Number":
                jsonObject.put("OutlineAgreementNumber",
                        StringUtil.checkObjectNull(freeTextMessageEntity.getOutlineAgreementNumber()));
            case "OA Item Number":
                jsonObject.put("OaItemNumber", StringUtil.checkObjectNull(freeTextMessageEntity.getOaItemNumber()));
            case "Purchase Order Number":
                jsonObject.put("PurchaseOrderNumber", StringUtil.checkObjectNull(freeTextMessageEntity.getPoNumber()));
            case "PO Item Number":
                jsonObject.put("PoItemNumber", StringUtil.checkObjectNull(freeTextMessageEntity.getPoItemNumber()));
            }
        }
        return jsonObject;
    }

    /**
     * Method to create Distribution List
     */
    public JSONObject constructDistributionList(FreeTextMessageEntity freeTextMessageEntity, JSONObject jsonObject) {
        JSONArray jsonArray = new JSONArray();
        JSONArray userArray = new JSONArray();
        List<String> ids = new ArrayList<>();
        try {
            FreeTextMaintenanceEntity freeTextMaintainence = freeTextMaintenanceRepository
                    .findByTriggerId(freeTextMessageEntity.getFreeTextEvent());
            if (freeTextMaintainence != null
                    && StringUtil.isListNotNullOrEmpty(freeTextMaintainence.getDistributionName())) {
                freeTextMaintainence.getDistributionName().forEach(dn -> {
                    if (StringUtil.isNotNullOrEmpty(dn.getId()) && StringUtil.isNotNullOrEmpty(dn.getName())
                            && !ids.contains(dn.getId())) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("id", StringUtil.checkObjectNull(dn.getId()));
                        jsonObject2.put("name", StringUtil.checkObjectNull(dn.getName()));
                        ids.add(dn.getId());
                        jsonArray.put(jsonObject2);
                        ReceiverGroupUserEntity groupUserEntity = receiverGroupUserRepository
                                .findAllByTriggerID(dn.getId());
                        if (groupUserEntity != null && StringUtil.isListNotNullOrEmpty(groupUserEntity.getUserJson())) {
                            JSONObject userObject = new JSONObject();
                            groupUserEntity.getUserJson().forEach(user -> {
                                userObject.put("receiverGroup",
                                        groupUserEntity.getReceiverId() + "-" + groupUserEntity.getReceiverLong());
                                userObject.put("sapLoginId", user.getSapLoginId());
                                userObject.put("firstName", user.getFirstName());
                                userObject.put("lastName", user.getLastName());
                                userObject.put("email", user.getEmail());
                                userArray.put(userObject);
                            });
                        }
                    }
                });
                jsonObject.put("allReceiverList", jsonArray);
                jsonObject.put("allUserList", userArray);
            }
        } catch (Exception e) {
            return jsonObject;
        }
        return jsonObject;
    }
}
