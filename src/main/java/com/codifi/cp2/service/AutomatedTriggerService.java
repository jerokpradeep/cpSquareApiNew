package com.codifi.cp2.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.codifi.cp2.entity.ATMCategoryAlignmentEntity;
import com.codifi.cp2.entity.ATMHeaderMessageEntity;
import com.codifi.cp2.entity.AutomatedConditionFunctionEntity;
import com.codifi.cp2.entity.AutomatedTriggerCDTableEntity;
import com.codifi.cp2.entity.AutomatedTriggerEntity;
import com.codifi.cp2.entity.ConditionListEntity;
import com.codifi.cp2.entity.ExtractedDataFieldEntity;
import com.codifi.cp2.entity.FieldListEntity;
import com.codifi.cp2.model.request.ConditionFunctionModel;
import com.codifi.cp2.model.request.OdataCdhdrJsonModel;
import com.codifi.cp2.model.request.OdataCdposJsonModel;
import com.codifi.cp2.model.request.OdataCdposObjJsonModel;
import com.codifi.cp2.model.request.OdataCdposTabkeyJsonModel;
import com.codifi.cp2.model.request.OdataCondFuncModel;
import com.codifi.cp2.model.request.OdataConditionModel;
import com.codifi.cp2.model.response.DevlopmentProgressModel;
import com.codifi.cp2.repository.ATMCategoryAlignmentRepository;
import com.codifi.cp2.repository.ATMHeaderMessageRepository;
import com.codifi.cp2.repository.AutomatedConditionFunctionRepository;
import com.codifi.cp2.repository.AutomatedTriggerCDTableRepository;
import com.codifi.cp2.repository.AutomatedTriggerRepository;
import com.codifi.cp2.repository.ConditionListRepository;
import com.codifi.cp2.repository.ExtractedDataFieldRepository;
import com.codifi.cp2.repository.FieldListRepository;
import com.codifi.cp2.util.CommonMethods;
import com.codifi.cp2.util.DateUtil;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AutomatedTriggerService {
    @Autowired
    AutomatedTriggerRepository automatedTriggerRepository;
    @Autowired
    ConditionListRepository conditionListRepository;
    @Autowired
    FieldListRepository fieldListRepository;
    @Autowired
    AutomatedTriggerCDTableRepository automatedTriggerCDTableRepository;
    @Autowired
    AutomatedConditionFunctionRepository automatedConditionFunctionRepository;
    @Autowired
    ATMCategoryAlignmentRepository atmCategoryAlignmentRepository;
    @Autowired
    ATMHeaderMessageRepository atmHeaderMessageRepository;
    @Autowired
    ExtractedDataFieldRepository extractedDataFieldRepository;

    /**
     * Method To get All Automated Trigger Maintenance List
     * 
     * @param
     * @author Pradeep
     * @return List of Automated Trigger Maintenance Data
     */
    public ResponseEntity<String> getAutomatedTriggerDetail() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<AutomatedTriggerEntity> automatedTriggerDetailList = automatedTriggerRepository.findAllActive();
            if (StringUtil.isListNotNullOrEmpty(automatedTriggerDetailList)) {
                for (AutomatedTriggerEntity automatedTriggerDetail : automatedTriggerDetailList) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", automatedTriggerDetail.getId());
                    jsonObject2.put("triggerId",
                            StringUtil.checkObjectNull(automatedTriggerDetail.getAutomatedTriggerId()));
                    jsonObject2.put("triggerNameEnglish",
                            StringUtil.checkObjectNull(automatedTriggerDetail.getTriggerNameEnglish()));
                    jsonObject2.put("triggerNameGerman",
                            StringUtil.checkObjectNull(automatedTriggerDetail.getTriggerNameGerman()));
                    jsonObject2.put("createdOn", DateUtil.getEpochTimeFromDate(automatedTriggerDetail.getCreatedOn()));
                    jsonObject2.put("createdBy", StringUtil.checkObjectNull(automatedTriggerDetail.getCreatedBy()));
                    jsonObject2.put("status", StringUtil.checkObjectNull(automatedTriggerDetail.getStatus()));
                    jsonObject2.put("devPercent",
                            (automatedTriggerDetail.getDevelopmentProgress() != 0
                                    && automatedTriggerDetail.getDevelopmentProgress() > 0)
                                            ? automatedTriggerDetail.getDevelopmentProgress()
                                            : 0);
                    jsonObject2.put("activatedOn",
                            StringUtil.isNotNullOrEmpty(automatedTriggerDetail.getDateActivated())
                                    ? automatedTriggerDetail.getDateActivated()
                                    : "");
                    jsonObject2.put("deActivatedOn",
                            StringUtil.isNotNullOrEmpty(automatedTriggerDetail.getDateDeactivated())
                                    ? automatedTriggerDetail.getDateDeactivated()
                                    : "");
                    array.put(jsonObject2);
                }
                jsonObject.put("data", array);
                jsonObject.put("resString", "Accessed Automated Trigger List.");
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - getAutomatedTriggerDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to update Automated Trigger detail status
     * 
     * @author Pradeep Ravichandran
     * @param atmEntity
     * @return
     */
    public ResponseEntity<String> updateATMStatus(AutomatedTriggerEntity atmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        long epoch = Instant.now().toEpochMilli();
        int count = 0;
        try {
            if (atmEntity != null && StringUtil.isNotNullOrEmpty(atmEntity.getStatus())
                    && StringUtil.isNotNullOrEmpty(atmEntity.getCreatedBy()) && atmEntity.getId() != null
                    && atmEntity.getId() > 0) {
                if (atmEntity.getStatus().equalsIgnoreCase("active")) {
                    count = automatedTriggerRepository.updateStatusActive(atmEntity.getId(), atmEntity.getStatus(),
                            String.valueOf(epoch), atmEntity.getCreatedBy());
                } else {
                    count = automatedTriggerRepository.updateStatusInActiveAndUD(atmEntity.getId(),
                            atmEntity.getStatus(), String.valueOf(epoch), atmEntity.getCreatedBy());
                }
                if (count > 0) {
                    Optional<AutomatedTriggerEntity> entity = automatedTriggerRepository.findById(atmEntity.getId());
                    if (entity != null) {
                        AutomatedTriggerEntity udpatedAutomatedTriggerEntity = entity.get();
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("resultString", MessageConstants.STATUS_UPDATED);
                        jsonObject2.put("triggerId", udpatedAutomatedTriggerEntity.getAutomatedTriggerId());
                        jsonObject2.put("activatedOn", udpatedAutomatedTriggerEntity.getDateActivated());
                        jsonObject2.put("deActivatedOn", udpatedAutomatedTriggerEntity.getDateDeactivated());
                        array.put(jsonObject2);
                        jsonObject.put("data", array);
                        jsonObject.put("message", MessageConstants.SUCCESS);
                        jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.STATUS_NOT_UPDATED,
                            MessageConstants.FAILED_CODE);
                }
            } else {
                jsonObject.put("error", UUID.randomUUID().toString());
                if (atmEntity == null) {
                    jsonObject.put("resString", MessageConstants.EMPTY_DATA);
                } else if (StringUtil.isNullOrEmpty(atmEntity.getStatus())) {
                    jsonObject.put("resString", MessageConstants.STATUS_NULL);
                } else if (StringUtil.isNullOrEmpty(atmEntity.getCreatedBy())) {
                    jsonObject.put("resString", MessageConstants.CREATED_BY_NULL);
                } else {
                    jsonObject.put("resString", MessageConstants.ID_NULL);
                }
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - updateATMStatus");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to Delete Automated Trigger detail
     * 
     * @author Pradeep Ravichandran
     * @param atmEntity
     * @return
     */
    public ResponseEntity<String> deleteATM(AutomatedTriggerEntity atmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int count = 0;
        try {
            if (atmEntity != null && StringUtil.isNotNullOrEmpty(atmEntity.getCreatedBy()) && atmEntity.getId() != null
                    && atmEntity.getId() > 0) {
                Optional<AutomatedTriggerEntity> entity = automatedTriggerRepository.findById(atmEntity.getId());
                if (entity != null && StringUtil.isNotNullOrEmpty(entity.get().getStatus())
                        && !entity.get().getStatus().equalsIgnoreCase("active")) {
                    count = automatedTriggerRepository.deleteATM(atmEntity.getId(), atmEntity.getCreatedBy());
                    if (count > 0) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("response", MessageConstants.DELETE_SUCCESS);
                        array.put(jsonObject2);
                        jsonObject.put("data", array);
                        jsonObject.put("message", MessageConstants.SUCCESS);
                        jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                                MessageConstants.FAILED_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ACTIVE_STATUS_NOT_DELETE,
                            MessageConstants.FAILED_CODE);
                }
            } else {
                jsonObject.put("error", UUID.randomUUID().toString());
                if (atmEntity == null) {
                    jsonObject.put("resString", MessageConstants.EMPTY_DATA);
                } else if (StringUtil.isNullOrEmpty(atmEntity.getCreatedBy())) {
                    jsonObject.put("resString", MessageConstants.CREATED_BY_NULL);
                } else {
                    jsonObject.put("resString", MessageConstants.ID_NULL);
                }
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - deleteATM");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save Automated Trigger Details
     * 
     * @author Pradeep
     * @param ftmEntity
     * @return
     */
    public ResponseEntity<String> saveAutomatedTriggerDetail(AutomatedTriggerEntity atmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            AutomatedTriggerEntity oldEntityList = automatedTriggerRepository
                    .findByTriggerName(atmEntity.getTriggerNameEnglish());
            if (oldEntityList == null) {
                int devPercent = 20;
                if (!atmEntity.isConditionalFunction()) {
                    devPercent += 20;
                }
                if (!atmEntity.isCategoryAlignment()) {
                    devPercent += 20;
                }
                atmEntity.setDevelopmentProgress(devPercent);
                atmEntity.setStatus(MessageConstants.CONST_STATUS_UD);
                AutomatedTriggerEntity newEntity = automatedTriggerRepository.save(atmEntity);
                if (newEntity != null) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("resultString", MessageConstants.SAVE_OBJECT);
                    jsonObject2.put("id", newEntity.getId());
                    jsonObject2.put("triggerId", newEntity.getAutomatedTriggerId());
                    jsonObject2.put("triggerId", newEntity.getAutomatedTriggerId());
                    jsonObject2.put("triggerNameEnglish", newEntity.getTriggerNameEnglish());
                    jsonObject2.put("triggerNameGerman", newEntity.getTriggerNameGerman());
                    array.put(jsonObject2);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                            MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.AUTOMATED_TRIGGER_NAME_EXIST,
                        MessageConstants.ERROR_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " ATM Service - saveAutomatedTriggerDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to update Automated Trigger Details
     * 
     * @author Pradeep
     * @param ftmEntity
     * @return
     */
    public ResponseEntity<String> updateAutomatedTriggerDetail(AutomatedTriggerEntity atmEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            AutomatedTriggerEntity oldRecord = automatedTriggerRepository.findByIdValue(atmEntity.getId());
            if (oldRecord != null) {
                atmEntity = populateATMStatus(atmEntity);
                int updateDetails = automatedTriggerRepository.updateATMDetails(atmEntity.getDescription(),
                        atmEntity.isConditionalFunction(), atmEntity.isCategoryAlignment(),
                        atmEntity.isLeadTimeAvailable(), atmEntity.getEasyName(), atmEntity.getTableName(),
                        atmEntity.getFieldName(), atmEntity.getCreatedBy(), atmEntity.getId(), atmEntity.getStatus(),
                        atmEntity.getDevelopmentProgress(), atmEntity.getTriggerNameEnglish(),
                        atmEntity.getTriggerNameGerman());
                if (updateDetails > 0) {
                    jsonObject = populateATMDataForId(atmEntity.getId());
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_UPDATING_DATA,
                            MessageConstants.ERROR_CODE);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + atmEntity.getId(),
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " ATM Service - updateAutomatedTriggerDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AutomatedTriggerEntity populateATMStatus(AutomatedTriggerEntity atmEntity) {
        DevlopmentProgressModel oldRecord = automatedTriggerRepository.findDataForDevProgress(atmEntity.getId());
        if (oldRecord != null) {
            int devpercent = 20;
            if (atmEntity.isCategoryAlignment()) {
                if (oldRecord.getIsCategory() > 0) {
                    devpercent += 20;
                }
            } else {
                devpercent += 20;
            }

            if (atmEntity.isConditionalFunction()) {
                if (oldRecord.getIsCond() > 0) {
                    devpercent += 20;
                }
            } else {
                devpercent += 20;
            }
            if (oldRecord.getIsHdr() > 0 || oldRecord.getIsCdPos() > 0 || oldRecord.getIsCdObj() > 0
                    || oldRecord.getIsTab() > 0) {
                devpercent += 20;
            }
            if (oldRecord.getIsVariable() > 0) {
                devpercent += 20;
            }
            atmEntity.setDevelopmentProgress(devpercent);
            atmEntity.setStatus(oldRecord.getStatus());
        }
        return atmEntity;
    }

    /**
     * Method to get Automated Trigger Data Field By id
     *
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    public ResponseEntity<String> getATMDetailsById(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (id > 0) {
                jsonObject = populateATMDataForId(Integer.toUnsignedLong(id));
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL, MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - getATMDetailsById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to populate ATM by passing parameter id
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    public JSONObject populateATMDataForId(Long id) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        Optional<AutomatedTriggerEntity> newData = automatedTriggerRepository.findById(id);
        if (newData != null) {
            AutomatedTriggerEntity oldEntity = newData.get();
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("id", oldEntity.getId());
            jsonObject2.put("triggerId", StringUtil.checkObjectNull(oldEntity.getAutomatedTriggerId()));
            jsonObject2.put("triggerNameEnglish", StringUtil.checkObjectNull(oldEntity.getTriggerNameEnglish()));
            jsonObject2.put("triggerNameGerman", StringUtil.checkObjectNull(oldEntity.getTriggerNameGerman()));
            jsonObject2.put("description", StringUtil.checkObjectNull(oldEntity.getDescription()));
            jsonObject2.put("conditionFunctions", oldEntity.isConditionalFunction());
            jsonObject2.put("categoryAlignment", oldEntity.isCategoryAlignment());
            jsonObject2.put("leadTimeAvailable", oldEntity.isLeadTimeAvailable());
            if (oldEntity.isLeadTimeAvailable()) {
                jsonObject2.put("easyName", StringUtil.checkObjectNull(oldEntity.getEasyName()));
                jsonObject2.put("tableName", StringUtil.checkObjectNull(oldEntity.getTableName()));
                jsonObject2.put("fieldName", StringUtil.checkObjectNull(oldEntity.getFieldName()));
            }
            AutomatedTriggerCDTableEntity atCDTableEntity = automatedTriggerCDTableRepository
                    .findAllByTriggerID(oldEntity.getAutomatedTriggerId());
            if (atCDTableEntity != null) {
                jsonObject2.put("hdrList",
                        atCDTableEntity.getCdhdrJson() != null
                                && StringUtil.isListNotNullOrEmpty(atCDTableEntity.getCdhdrJson().getCdhdrJson())
                                        ? atCDTableEntity.getCdhdrJson().getCdhdrJson()
                                        : "");
                jsonObject2.put("posList",
                        atCDTableEntity.getCdposJson() != null
                                && StringUtil.isListNotNullOrEmpty(atCDTableEntity.getCdposJson().getCdposJson())
                                        ? atCDTableEntity.getCdposJson().getCdposJson()
                                        : "");
                jsonObject2.put("objectList",
                        atCDTableEntity.getObjectsJson() != null
                                && StringUtil.isListNotNullOrEmpty(atCDTableEntity.getObjectsJson().getObjectsJson())
                                        ? atCDTableEntity.getObjectsJson().getObjectsJson()
                                        : "");
                jsonObject2.put("tabList",
                        atCDTableEntity.getTabKeyJson() != null
                                && StringUtil.isListNotNullOrEmpty(atCDTableEntity.getTabKeyJson().getTabKeyJson())
                                        ? atCDTableEntity.getTabKeyJson().getTabKeyJson()
                                        : "");
            }
            AutomatedConditionFunctionEntity atCondFuncEntity = automatedConditionFunctionRepository
                    .findAllByTriggerID(oldEntity.getAutomatedTriggerId());
            if (atCondFuncEntity != null) {
                List<ConditionFunctionModel> list = null;
                if (atCondFuncEntity.getConditionArray() != null
                        && StringUtil.isListNotNullOrEmpty(atCondFuncEntity.getConditionArray().getConditionArray())) {
                    list = new ArrayList<>();
                    for (OdataConditionModel conditionModel : atCondFuncEntity.getConditionArray()
                            .getConditionArray()) {
                        if (conditionModel != null) {
                            list.addAll(conditionModel.getCondition());
                        }
                    }
                }
                jsonObject2.put("condition", StringUtil.isListNotNullOrEmpty(list) ? list : "");
            }
            ATMCategoryAlignmentEntity atmCatAlignEntity = atmCategoryAlignmentRepository
                    .findAllByTriggerID(oldEntity.getAutomatedTriggerId());
            if (atmCatAlignEntity != null) {
                jsonObject2.put("categories",
                        atmCatAlignEntity.getCategoryJson() != null ? atmCatAlignEntity.getCategoryJson() : "");
            }
            ATMHeaderMessageEntity atmHeaderMessageEntity = atmHeaderMessageRepository
                    .findAllByTriggerID(oldEntity.getAutomatedTriggerId());
            if (atmCatAlignEntity != null) {
                jsonObject2.put("headerText", StringUtil.checkObjectNull(atmHeaderMessageEntity.getHeaderText()));
                jsonObject2.put("variables",
                        atmHeaderMessageEntity.getMessageHeaderJson() != null
                                ? atmHeaderMessageEntity.getMessageHeaderJson()
                                : "");
            }
            array.put(jsonObject2);
            jsonObject.put("data", array);
            jsonObject.put("message", MessageConstants.SUCCESS);
            jsonObject.put("status", MessageConstants.SUCCESS_CODE);
        } else {
            jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + id,
                    MessageConstants.FAILED_CODE);
        }
        return jsonObject;
    }

    /**
     * Method To get All Condition list
     * 
     * @author Pradeep Ravichandran
     * @return List of Condition list
     */
    public ResponseEntity<String> getAllConditionList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<ConditionListEntity> entityList = conditionListRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(entityList)) {
                entityList.forEach(enlist -> {
                    JSONObject parseObj = new JSONObject();
                    parseObj.put("id", enlist.getCId());
                    parseObj.put("name", StringUtil.checkObjectNull(enlist.getCName()));
                    parseObj.put("fieldName", StringUtil.checkObjectNull(enlist.getTableFieldName()));
                    array.put(parseObj);
                });
                jsonObject.put("data", array);
                jsonObject.put("resString", "Accessed Automated Trigger List.");
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - getAllConditionList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get All Field list
     * 
     * @author Pradeep Ravichandran
     * @return List of Field list
     */
    public ResponseEntity<String> getAllFieldList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<FieldListEntity> entityList = fieldListRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(entityList)) {
                entityList.forEach(enlist -> {
                    JSONObject parseObj = new JSONObject();
                    parseObj.put("id", enlist.getFId());
                    parseObj.put("name", StringUtil.checkObjectNull(enlist.getFName()));
                    array.put(parseObj);
                });
                jsonObject.put("data", array);
                jsonObject.put("resString", "Accessed Automated Trigger List.");
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - getAllFieldList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save CDHDR and CDPOS
     * 
     * @author Pradeep
     * @param atmCDPOSEntity
     * @return
     */
    public ResponseEntity<String> saveCdhdrAndCdpos(AutomatedTriggerCDTableEntity atmCDPOSEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            AutomatedTriggerCDTableEntity oldEntity = automatedTriggerCDTableRepository
                    .findAllByTriggerID(atmCDPOSEntity.getAutomatedTriggerId());
            if (oldEntity != null) {
                oldEntity.setAutomatedTriggerName(atmCDPOSEntity.getAutomatedTriggerName());

                OdataCdhdrJsonModel hdr = new OdataCdhdrJsonModel();
                hdr.setCdhdrJson(atmCDPOSEntity.getHdrList());
                oldEntity.setCdhdrJson(hdr);

                OdataCdposJsonModel cdpos = new OdataCdposJsonModel();
                cdpos.setCdposJson(atmCDPOSEntity.getPosList());
                oldEntity.setCdposJson(cdpos);

                OdataCdposObjJsonModel obj = new OdataCdposObjJsonModel();
                obj.setObjectsJson(atmCDPOSEntity.getObjectList());
                oldEntity.setObjectsJson(obj);

                OdataCdposTabkeyJsonModel tab = new OdataCdposTabkeyJsonModel();
                tab.setTabKeyJson(atmCDPOSEntity.getTabList());
                oldEntity.setTabKeyJson(tab);

                oldEntity.setUpdatedOn(new Date());
                AutomatedTriggerCDTableEntity newEntity = automatedTriggerCDTableRepository.save(oldEntity);
                if (newEntity != null) {
                    AutomatedTriggerEntity automatedTriggerEntity = automatedTriggerRepository
                            .findByTriggerId(newEntity.getAutomatedTriggerId());
                    AutomatedTriggerEntity statusEntity = populateATMStatus(automatedTriggerEntity);
                    automatedTriggerRepository.updateStatusAndDevProgress(automatedTriggerEntity.getId(),
                            statusEntity.getStatus(), statusEntity.getDevelopmentProgress());
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("resultString", MessageConstants.SAVE_OBJECT);
                    jsonObject2.put("triggerId", automatedTriggerEntity.getAutomatedTriggerId());
                    jsonObject2.put("triggerNameEnglish", automatedTriggerEntity.getTriggerNameEnglish());
                    jsonObject2.put("triggerNameGerman", automatedTriggerEntity.getTriggerNameGerman());
                    array.put(jsonObject2);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                            MessageConstants.ERROR_CODE);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - saveCdhdrAndCdpos");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save conditional function
     * 
     * @author Pradeep
     * @param atmCondFuncEntity
     * @return
     */
    public ResponseEntity<String> saveCondFunc(AutomatedConditionFunctionEntity atmCondFuncEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            AutomatedConditionFunctionEntity oldCondFuncEntity = automatedConditionFunctionRepository
                    .findAllByTriggerID(atmCondFuncEntity.getAutomatedTriggerId());
            if (oldCondFuncEntity != null) {
                if (StringUtil.isListNotNullOrEmpty(atmCondFuncEntity.getConditionFunction())) {
                    oldCondFuncEntity.setAutomatedTriggerName(atmCondFuncEntity.getAutomatedTriggerName());
                    List<OdataConditionModel> conditionArray = populateConditionFunctionJson(atmCondFuncEntity,
                            oldCondFuncEntity);
                    OdataCondFuncModel condFunc = new OdataCondFuncModel();
                    condFunc.setConditionArray(conditionArray);
                    oldCondFuncEntity.setConditionArray(condFunc);
                    oldCondFuncEntity.setUpdatedBy(atmCondFuncEntity.getCreatedBy());
                    oldCondFuncEntity.setUpdatedOn(new Date());
                    AutomatedConditionFunctionEntity newEntity = automatedConditionFunctionRepository
                            .save(oldCondFuncEntity);
                    if (newEntity != null) {
                        AutomatedTriggerEntity automatedTriggerEntity = automatedTriggerRepository
                                .findByTriggerId(newEntity.getAutomatedTriggerId());
                        AutomatedTriggerEntity statusEntity = populateATMStatus(automatedTriggerEntity);
                        automatedTriggerRepository.updateStatusAndDevProgress(automatedTriggerEntity.getId(),
                                statusEntity.getStatus(), statusEntity.getDevelopmentProgress());
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("resultString", MessageConstants.SAVE_OBJECT);
                        jsonObject2.put("triggerId", automatedTriggerEntity.getAutomatedTriggerId());
                        jsonObject2.put("triggerNameEnglish", automatedTriggerEntity.getTriggerNameEnglish());
                        jsonObject2.put("triggerNameGerman", automatedTriggerEntity.getTriggerNameGerman());
                        array.put(jsonObject2);
                        jsonObject.put("data", array);
                        jsonObject.put("message", MessageConstants.SUCCESS);
                        jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                                MessageConstants.ERROR_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.MISSING_MANDATORY_FIELDS,
                            MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - saveCondFunc");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<OdataConditionModel> populateConditionFunctionJson(AutomatedConditionFunctionEntity atmCondFuncEntity,
            AutomatedConditionFunctionEntity oldCondFuncEntity) {
        List<ConditionFunctionModel> conditionFunctionModels = atmCondFuncEntity.getConditionFunction();
        List<OdataConditionModel> conditionModelList = new ArrayList<>();
        if (StringUtil.isListNotNullOrEmpty(conditionFunctionModels)) {
            Map<String, List<ConditionFunctionModel>> connectMap = new HashMap<>();
            for (ConditionFunctionModel model : conditionFunctionModels) {
                if (model != null) {
                    if (connectMap.containsKey(model.getName())) {
                        connectMap.get(model.getName()).add(model);
                    } else {
                        connectMap.put(model.getName(), new ArrayList<>());
                        connectMap.get(model.getName()).add(model);
                    }
                }
            }
            for (Map.Entry<String, List<ConditionFunctionModel>> entry : connectMap.entrySet()) {
                List<ConditionFunctionModel> values = entry.getValue();
                OdataConditionModel conditionModel = new OdataConditionModel();
                if (StringUtil.isListNotNullOrEmpty(conditionModel.getCondition())) {
                    conditionModel.getCondition().addAll(values);
                } else {
                    conditionModel.setCondition(values);
                }
                conditionModelList.add(conditionModel);
            }
        }
        return conditionModelList;
    }

    /**
     * Method to save category Alignment
     * 
     * @author Pradeep
     * @param atmCatAlignEntity
     * @return
     */
    public ResponseEntity<String> saveCatAlignment(ATMCategoryAlignmentEntity atmCatAlignEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            ATMCategoryAlignmentEntity oldCatAlignEntity = atmCategoryAlignmentRepository
                    .findAllByTriggerID(atmCatAlignEntity.getAutomatedTriggerId());
            if (oldCatAlignEntity != null) {
                if (StringUtil.isListNotNullOrEmpty(atmCatAlignEntity.getCategoryJson())) {
                    oldCatAlignEntity.setAutomatedTriggerName(atmCatAlignEntity.getAutomatedTriggerName());
                    oldCatAlignEntity.setCategoryJson(atmCatAlignEntity.getCategoryJson());
                    oldCatAlignEntity.setUpdatedBy(atmCatAlignEntity.getCreatedBy());
                    oldCatAlignEntity.setUpdatedOn(new Date());
                    ATMCategoryAlignmentEntity newEntity = atmCategoryAlignmentRepository.save(oldCatAlignEntity);
                    if (newEntity != null) {
                        AutomatedTriggerEntity automatedTriggerEntity = automatedTriggerRepository
                                .findByTriggerId(newEntity.getAutomatedTriggerId());
                        AutomatedTriggerEntity statusEntity = populateATMStatus(automatedTriggerEntity);
                        automatedTriggerRepository.updateStatusAndDevProgress(automatedTriggerEntity.getId(),
                                statusEntity.getStatus(), statusEntity.getDevelopmentProgress());
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("resultString", MessageConstants.SAVE_OBJECT);
                        jsonObject2.put("triggerId", automatedTriggerEntity.getAutomatedTriggerId());
                        jsonObject2.put("triggerNameEnglish", automatedTriggerEntity.getTriggerNameEnglish());
                        jsonObject2.put("triggerNameGerman", automatedTriggerEntity.getTriggerNameGerman());
                        array.put(jsonObject2);
                        jsonObject.put("data", array);
                        jsonObject.put("message", MessageConstants.SUCCESS);
                        jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                                MessageConstants.ERROR_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.MISSING_MANDATORY_FIELDS,
                            MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - saveCatAlignment");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save Variable Messages
     * 
     * @author Pradeep
     * @param atmVariableEntity
     * @return
     */
    public ResponseEntity<String> saveVariableMessage(ATMHeaderMessageEntity atmVariableEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            ATMHeaderMessageEntity oldVariableMessageEntity = atmHeaderMessageRepository
                    .findAllByTriggerID(atmVariableEntity.getAutomatedTriggerId());
            if (oldVariableMessageEntity != null) {
                if (StringUtil.isListNotNullOrEmpty(atmVariableEntity.getMessageHeaderJson())) {
                    oldVariableMessageEntity.setAutomatedTriggerName(atmVariableEntity.getAutomatedTriggerName());
                    oldVariableMessageEntity.setMessageHeaderJson(atmVariableEntity.getMessageHeaderJson());
                    oldVariableMessageEntity.setHeaderText(atmVariableEntity.getHeaderText());
                    oldVariableMessageEntity.setUpdatedBy(atmVariableEntity.getCreatedBy());
                    oldVariableMessageEntity.setUpdatedOn(new Date());
                    ATMHeaderMessageEntity newEntity = atmHeaderMessageRepository.save(oldVariableMessageEntity);
                    if (newEntity != null) {
                        AutomatedTriggerEntity automatedTriggerEntity = automatedTriggerRepository
                                .findByTriggerId(newEntity.getAutomatedTriggerId());
                        AutomatedTriggerEntity statusEntity = populateATMStatus(automatedTriggerEntity);
                        automatedTriggerRepository.updateStatusAndDevProgress(automatedTriggerEntity.getId(),
                                statusEntity.getStatus(), statusEntity.getDevelopmentProgress());
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("resultString", MessageConstants.SAVE_OBJECT);
                        jsonObject2.put("triggerId", automatedTriggerEntity.getAutomatedTriggerId());
                        jsonObject2.put("triggerNameEnglish", automatedTriggerEntity.getTriggerNameEnglish());
                        jsonObject2.put("triggerNameGerman", automatedTriggerEntity.getTriggerNameGerman());
                        array.put(jsonObject2);
                        jsonObject.put("data", array);
                        jsonObject.put("message", MessageConstants.SUCCESS);
                        jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                                MessageConstants.ERROR_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.MISSING_MANDATORY_FIELDS,
                            MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - saveVariableMessage");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get All Extracted Data Field List
     * 
     * @author Pradeep Ravichandran
     * @return List of Extracted Data Field List
     */
    public ResponseEntity<String> getExtractedDataFieldList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<ExtractedDataFieldEntity> extractedDataEntityList = extractedDataFieldRepository.findAllDateType();
            if (StringUtil.isListNotNullOrEmpty(extractedDataEntityList)) {
                for (ExtractedDataFieldEntity extractedDataEntity : extractedDataEntityList) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", extractedDataEntity.getId());
                    jsonObject2.put("tableName",
                            StringUtil.isNotNullOrEmpty(extractedDataEntity.getTableName())
                                    ? extractedDataEntity.getTableName()
                                    : "");
                    jsonObject2.put("fieldName",
                            StringUtil.isNotNullOrEmpty(extractedDataEntity.getFieldName())
                                    ? extractedDataEntity.getFieldName()
                                    : "");
                    jsonObject2.put("oDataServiceName",
                            StringUtil.isNotNullOrEmpty(extractedDataEntity.getOdataService())
                                    ? extractedDataEntity.getOdataService()
                                    : "");
                    jsonObject2.put("sapFieldName",
                            StringUtil.isNotNullOrEmpty(extractedDataEntity.getSapFieldName())
                                    ? extractedDataEntity.getSapFieldName()
                                    : "");
                    jsonObject2.put("easyName",
                            StringUtil.isNotNullOrEmpty(extractedDataEntity.getEasyName())
                                    ? extractedDataEntity.getEasyName()
                                    : "");
                    jsonObject2.put("dateType",
                            StringUtil.isNotNullOrEmpty(extractedDataEntity.getDateType())
                                    ? extractedDataEntity.getDateType()
                                    : "");
                    jsonObject2.put("messageRelevant",
                            StringUtil.isNotNullOrEmpty(extractedDataEntity.getMessageRelevant())
                                    ? extractedDataEntity.getMessageRelevant()
                                    : "");
                    array.put(jsonObject2);
                }
                jsonObject.put("data", array);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " ATM Service - getExtractedDataFieldList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
