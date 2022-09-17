package com.codifi.cp2.service;

import java.time.Instant;
import java.util.*;

import com.codifi.cp2.entity.FreeTextMaintenanceEntity;
import com.codifi.cp2.entity.ReceiverGroupMaintenanceEntity;
import com.codifi.cp2.entity.TemplateFieldListEntity;
import com.codifi.cp2.helper.FreeTextMaintenanceHelper;
import com.codifi.cp2.repository.FreeTextMaintenanceRepository;
import com.codifi.cp2.repository.ReceiverGroupMaintenanceRepository;
import com.codifi.cp2.repository.TemplateFieldListRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;
import com.codifi.cp2.util.CommonMethods;
import com.codifi.cp2.util.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FreeTextMaintenanceService {
    @Autowired
    FreeTextMaintenanceRepository freeTextMaintenanceRepository;
    @Autowired
    FreeTextMaintenanceHelper freeTextMaintenanceHelper;
    @Autowired
    TemplateFieldListRepository templateFieldListRepository;
    @Autowired
    ReceiverGroupMaintenanceRepository receiverGroupMaintenanceRepository;

    /**
     * Method To get All Free Text Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @return List of Free Text Maintenance Data
     */
    public ResponseEntity<String> getFreeTextTriggerDetail() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<FreeTextMaintenanceEntity> freeTextTriggerEntityList = freeTextMaintenanceRepository.findAllActive();
            if (StringUtil.isListNotNullOrEmpty(freeTextTriggerEntityList)) {
                for (FreeTextMaintenanceEntity freeTextMaintenanceEntity : freeTextTriggerEntityList) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", freeTextMaintenanceEntity.getId());
                    jsonObject2.put("triggerId", freeTextMaintenanceEntity.getFreeTextTriggerId());
                    jsonObject2.put("freeTextName",
                            StringUtil.checkObjectNull(freeTextMaintenanceEntity.getTriggerNameEnglish()));
                    jsonObject2.put("triggerNameGerman",
                            StringUtil.checkObjectNull(freeTextMaintenanceEntity.getTriggerNameGerman()));
                    jsonObject2.put("createdOn",
                            DateUtil.getEpochTimeFromDate(freeTextMaintenanceEntity.getCreatedOn()));
                    jsonObject2.put("createdBy", StringUtil.checkObjectNull(freeTextMaintenanceEntity.getCreatedBy()));
                    jsonObject2.put("activatedOn",
                            StringUtil.checkObjectNull(freeTextMaintenanceEntity.getDateActivated()));
                    jsonObject2.put("activatedBy",
                            StringUtil.checkObjectNull(freeTextMaintenanceEntity.getActivatedBy()));
                    jsonObject2.put("deactivatedOn",
                            StringUtil.checkObjectNull(freeTextMaintenanceEntity.getDateDeactivated()));
                    jsonObject2.put("deactivatedBy",
                            StringUtil.checkObjectNull(freeTextMaintenanceEntity.getDeactivatedBy()));
                    jsonObject2.put("status", StringUtil.checkObjectNull(freeTextMaintenanceEntity.getStatus()));
                    jsonObject2.put("devPercent",
                            (freeTextMaintenanceEntity.getDevelopmentProgress() != 0
                                    && freeTextMaintenanceEntity.getDevelopmentProgress() > 0)
                                            ? freeTextMaintenanceEntity.getDevelopmentProgress()
                                            : 0);
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - getFreeTextTriggerDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to update Free Text Maintenance status
     * 
     * @author Pradeep Ravichandran
     * @param ftmEntity
     * @return
     */
    public ResponseEntity<String> updateFTMStatus(FreeTextMaintenanceEntity ftmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        long epoch = Instant.now().toEpochMilli();
        int count = 0;
        try {
            if (ftmEntity != null && StringUtil.isNotNullOrEmpty(ftmEntity.getStatus())
                    && StringUtil.isNotNullOrEmpty(ftmEntity.getCreatedBy()) && ftmEntity.getId() != null
                    && ftmEntity.getId() > 0) {
                if (ftmEntity.getStatus().equalsIgnoreCase("active")) {
                    count = freeTextMaintenanceRepository.updateStatusActive(ftmEntity.getId(), ftmEntity.getStatus(),
                            String.valueOf(epoch), ftmEntity.getCreatedBy());
                } else {
                    count = freeTextMaintenanceRepository.updateStatusInActiveAndUD(ftmEntity.getId(),
                            ftmEntity.getStatus(), String.valueOf(epoch), ftmEntity.getCreatedBy());
                }
                if (count > 0) {
                    FreeTextMaintenanceEntity entity = freeTextMaintenanceRepository.findByIds(ftmEntity.getId());
                    if (entity != null) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("resultString", MessageConstants.STATUS_UPDATED);
                        jsonObject2.put("triggerId", StringUtil.checkObjectNull(entity.getFreeTextTriggerId()));
                        jsonObject2.put("activatedOn", StringUtil.checkObjectNull(entity.getDateActivated()));
                        jsonObject2.put("activatedBy", StringUtil.checkObjectNull(entity.getActivatedBy()));
                        jsonObject2.put("deActivatedOn", StringUtil.checkObjectNull(entity.getDateDeactivated()));
                        jsonObject2.put("deActivatedBy", StringUtil.checkObjectNull(entity.getDeactivatedBy()));
                        array.put(jsonObject2);
                        jsonObject.put("data", array);
                        jsonObject.put("message", MessageConstants.SUCCESS);
                        jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.STATUS_NOT_UPDATED,
                            MessageConstants.ERROR_CODE);
                }
            } else {
                jsonObject.put("error", UUID.randomUUID().toString());
                if (ftmEntity == null) {
                    jsonObject.put("resString", MessageConstants.EMPTY_DATA);
                } else if (StringUtil.isNullOrEmpty(ftmEntity.getStatus())) {
                    jsonObject.put("resString", MessageConstants.STATUS_NULL);
                } else if (StringUtil.isNullOrEmpty(ftmEntity.getCreatedBy())) {
                    jsonObject.put("resString", MessageConstants.CREATED_BY_NULL);
                } else {
                    jsonObject.put("resString", MessageConstants.ID_NULL);
                }
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - updateFTMStatus");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to Delete Free Text Maintenance
     * 
     * @author Pradeep Ravichandran
     * @param ftmEntity
     * @return
     */
    public ResponseEntity<String> deleteFTM(FreeTextMaintenanceEntity ftmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int count = 0;
        try {
            if (ftmEntity != null && StringUtil.isNotNullOrEmpty(ftmEntity.getCreatedBy()) && ftmEntity.getId() != null
                    && ftmEntity.getId() > 0) {
                FreeTextMaintenanceEntity entity = freeTextMaintenanceRepository.findByIds(ftmEntity.getId());
                if (entity != null && StringUtil.isNotNullOrEmpty(entity.getStatus())
                        && !entity.getStatus().equalsIgnoreCase("active")) {
                    count = freeTextMaintenanceRepository.deleteFTM(ftmEntity.getId(), ftmEntity.getCreatedBy());
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
                if (ftmEntity == null) {
                    jsonObject.put("resString", MessageConstants.EMPTY_DATA);
                } else if (StringUtil.isNullOrEmpty(ftmEntity.getCreatedBy())) {
                    jsonObject.put("resString", MessageConstants.CREATED_BY_NULL);
                } else {
                    jsonObject.put("resString", MessageConstants.ID_NULL);
                }
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - deleteFTM");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save Free Text Trigger Details
     * 
     * @author Pradeep
     * @param ftmEntity
     * @return
     */
    public ResponseEntity<String> saveFreeTextTriggerDetail(FreeTextMaintenanceEntity ftmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            FreeTextMaintenanceEntity freeTextTriggerEntityList = freeTextMaintenanceRepository
                    .findByTriggerName(ftmEntity.getTriggerNameEnglish());
            if (freeTextTriggerEntityList == null
                    || freeTextTriggerEntityList != null && freeTextTriggerEntityList.getActiveStatus() == 0) {
                ftmEntity.setStatus(MessageConstants.CONST_STATUS_UD);
                ftmEntity.setDevelopmentProgress(33);
                FreeTextMaintenanceEntity newEntity = freeTextMaintenanceRepository.save(ftmEntity);
                if (newEntity != null) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("resultString", MessageConstants.SAVE_OBJECT);
                    jsonObject2.put("id", newEntity.getId());
                    jsonObject2.put("triggerNameEnglish",
                            StringUtil.checkObjectNull(newEntity.getTriggerNameEnglish()));
                    jsonObject2.put("triggerNameGerman", StringUtil.checkObjectNull(newEntity.getTriggerNameGerman()));
                    jsonObject2.put("triggerId", StringUtil.checkObjectNull(newEntity.getFreeTextTriggerId()));
                    jsonObject2.put("status", StringUtil.checkObjectNull(newEntity.getStatus()));
                    array.put(jsonObject2);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SAVE_OBJECT);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                            MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.FREE_TEXT_TRIGGER_NAME_EXIST,
                        MessageConstants.ERROR_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - saveFreeTextTriggerDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to udpate Free Text Trigger Detail Screen
     * 
     * @author Pradeep
     * @param ftmEntity
     * @return
     */
    public ResponseEntity<String> updateFreeTextTriggerDetail(FreeTextMaintenanceEntity ftmEntity) {
        JSONObject jsonObject = new JSONObject();
        List<String> validateFreeTextDetailUpdate = null;
        try {
            FreeTextMaintenanceEntity oldEntity = freeTextMaintenanceRepository.findByIds(ftmEntity.getId());
            if (oldEntity != null) {
                if (StringUtil.isNotEqual(oldEntity.getTriggerNameEnglish(), ftmEntity.getTriggerNameEnglish())) {
                    validateFreeTextDetailUpdate = freeTextMaintenanceHelper.validateDetailUpdate(ftmEntity);
                }
                if (StringUtil.isListNullOrEmpty(validateFreeTextDetailUpdate)) {
                    oldEntity.setDescription(ftmEntity.getDescription());
                    oldEntity.setTemplate(ftmEntity.getTemplate());
                    oldEntity.setUpdatedBy(ftmEntity.getCreatedBy());
                    if (StringUtil.isNotEqual(oldEntity.getTriggerNameEnglish(), ftmEntity.getTriggerNameEnglish())) {
                        oldEntity.setTriggerNameEnglish(ftmEntity.getTriggerNameEnglish());
                    }
                    oldEntity.setTriggerNameGerman(ftmEntity.getTriggerNameGerman());
                    FreeTextMaintenanceEntity updatedData = freeTextMaintenanceRepository.save(oldEntity);
                    if (updatedData != null && updatedData.getId() != null && updatedData.getId() > 0) {
                        jsonObject = populateFTMDataForId(updatedData.getId());
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_UPDATING_DATA,
                                MessageConstants.ERROR_CODE);
                        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    jsonObject.put("error", UUID.randomUUID().toString());
                    jsonObject.put("resString", validateFreeTextDetailUpdate.get(0));
                    jsonObject.put("data", validateFreeTextDetailUpdate);
                    jsonObject.put("message", MessageConstants.FAILED);
                    jsonObject.put("status", MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + ftmEntity.getId(),
                        MessageConstants.ERROR_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FTM Service - updateFreeTextTriggerDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Free Text Maintenance Data Field By id
     *
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    public ResponseEntity<String> getFTMDataById(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (id > 0) {
                jsonObject = populateFTMDataForId(Integer.toUnsignedLong(id));
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL, MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - getFTMDataById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to populate FTM Data by passing parameter id
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    private JSONObject populateFTMDataForId(long id) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        FreeTextMaintenanceEntity oldEntity = freeTextMaintenanceRepository.findByIds(id);
        if (oldEntity != null) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("id", oldEntity.getId());
            jsonObject2.put("triggerId", StringUtil.checkObjectNull(oldEntity.getFreeTextTriggerId()));
            jsonObject2.put("triggerNameEnglish", StringUtil.checkObjectNull(oldEntity.getTriggerNameEnglish()));
            jsonObject2.put("triggerNameGerman", StringUtil.checkObjectNull(oldEntity.getTriggerNameGerman()));
            jsonObject2.put("description", StringUtil.checkObjectNull(oldEntity.getDescription()));
            jsonObject2.put("Status", StringUtil.checkObjectNull(oldEntity.getStatus()));
            jsonObject2.put("template", StringUtil.checkObjectNull(oldEntity.getTemplate()));
            jsonObject2.put("distributionStreams",
                    oldEntity.getDistributionName() != null ? oldEntity.getDistributionName() : "");
            jsonObject2.put("isDistributionList", oldEntity.isDistributionList());
            jsonObject2.put("isCategoryAlignment", oldEntity.isCategoryAlignment());
            jsonObject2.put("categories", oldEntity.getDistributionName() != null ? oldEntity.getCategoryJson() : "");
            array.put(jsonObject2);
            jsonObject.put("data", array);
            jsonObject.put("message", MessageConstants.SUCCESS);
            jsonObject.put("status", MessageConstants.SUCCESS_CODE);
        } else {
            jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + id,
                    MessageConstants.ERROR_CODE);
        }
        return jsonObject;
    }

    /**
     * Method to get All Template and Field List
     * 
     * @author Pradeep
     * @return
     */
    public ResponseEntity<String> getAllTemplateList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray list = new JSONArray();
        try {
            List<TemplateFieldListEntity> templateList = templateFieldListRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(templateList)) {
                templateList.forEach(tl -> {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("templateName", StringUtil.checkObjectNull(tl.getTemplateName()));
                    jsonObject1.put("templateDesc", StringUtil.checkObjectNull(tl.getFieldName()));
                    list.put(jsonObject1);
                });
                jsonObject.put("data", list);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - getAllTemplateList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get All Distribution List
     * 
     * @author Pradeep
     * @return
     */
    public ResponseEntity<String> getAllDistributionList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray list = new JSONArray();
        List<String> names = new ArrayList<>();
        try {
            List<ReceiverGroupMaintenanceEntity> distributionLists = receiverGroupMaintenanceRepository.findAllActive();
            if (StringUtil.isListNotNullOrEmpty(distributionLists)) {
                distributionLists.forEach(dl -> {
                    if (StringUtil.isNotNullOrEmpty(dl.getReceiverName()) && !names.contains(dl.getReceiverName())) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", StringUtil.checkObjectNull(dl.getReceiverId()));
                        jsonObject1.put("name", StringUtil.checkObjectNull(dl.getReceiverName()));
                        names.add(dl.getReceiverName());
                        list.put(jsonObject1);
                    }
                });
                jsonObject.put("data", list);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - getAllDistributionList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save Stream Distribution List
     * 
     * @author Pradeep
     * @param freeTextTriggerCriteria
     * @return
     */
    public ResponseEntity<String> saveDistributionList(FreeTextMaintenanceEntity freeTextTriggerCriteria) {
        JSONObject jsonObject = new JSONObject();
        try {
            FreeTextMaintenanceEntity oldData = freeTextMaintenanceRepository
                    .findByTriggerId(freeTextTriggerCriteria.getFreeTextTriggerId());
            if (oldData != null) {
                if (StringUtil.isListNotNullOrEmpty(freeTextTriggerCriteria.getDistributionName())) {
                    oldData.setDistributionList(true);
                }
                if (!oldData.isCategoryAlignment()) {
                    oldData.setDevelopmentProgress(66);
                }
                oldData.setDistributionName(freeTextTriggerCriteria.getDistributionName());
                oldData.setUpdatedBy(freeTextTriggerCriteria.getCreatedBy());
                oldData.setUpdatedOn(new Date());
                FreeTextMaintenanceEntity newData = freeTextMaintenanceRepository.save(oldData);
                if (newData != null && newData.getId() != null && newData.getId() > 0) {
                    jsonObject = populateFTMDataForId(newData.getId());
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                            MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - saveDistributionList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save category Alignment
     * 
     * @author Pradeep
     * @param atmCatAlignEntity
     * @return
     */
    public ResponseEntity<String> saveCatAlignment(FreeTextMaintenanceEntity freeTextTriggerCriteria) {
        JSONObject jsonObject = new JSONObject();
        try {
            FreeTextMaintenanceEntity oldData = freeTextMaintenanceRepository
                    .findByTriggerId(freeTextTriggerCriteria.getFreeTextTriggerId());
            if (oldData != null) {
                if (StringUtil.isListNotNullOrEmpty(freeTextTriggerCriteria.getCategoryJson())) {
                    oldData.setCategoryAlignment(true);
                    if (oldData.isDistributionList()) {
                        oldData.setDevelopmentProgress(100);
                    }
                } else {
                    oldData.setCategoryAlignment(false);
                    oldData.setDevelopmentProgress(66);
                }
                oldData.setCategoryJson(freeTextTriggerCriteria.getCategoryJson());
                oldData.setUpdatedBy(freeTextTriggerCriteria.getCreatedBy());
                oldData.setUpdatedOn(new Date());
                FreeTextMaintenanceEntity newData = freeTextMaintenanceRepository.save(oldData);
                if (newData != null && newData.getId() != null && newData.getId() > 0) {
                    jsonObject = populateFTMDataForId(newData.getId());
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                            MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTM Service - saveCatAlignment");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
