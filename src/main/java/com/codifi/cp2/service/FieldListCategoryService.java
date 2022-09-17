package com.codifi.cp2.service;

import java.util.*;
import com.codifi.cp2.entity.FieldListCategoryEntity;
import com.codifi.cp2.entity.TriggerFieldListEntity;
import com.codifi.cp2.helper.FieldListCategoryHelper;
import com.codifi.cp2.repository.ExtractedDataFieldRepository;
import com.codifi.cp2.repository.FieldListCategoryRepository;
import com.codifi.cp2.repository.TriggerFieldListRepository;
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
public class FieldListCategoryService {
    @Autowired
    FieldListCategoryRepository fieldListCategoryRepository;
    @Autowired
    TriggerFieldListRepository triggerFieldListRepository;
    @Autowired
    FieldListCategoryHelper fieldListCategoryHelper;
    @Autowired
    ExtractedDataFieldRepository extractedDataFieldRepository;

    /**
     * Method To get All Field List Category List
     * 
     * @author Pradeep Ravichandran
     * @return List of Field List Category List
     */
    public ResponseEntity<String> getFieldListCategoryList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<FieldListCategoryEntity> fieldListCategoryEntityList = fieldListCategoryRepository.findAllActive();
            if (StringUtil.isListNotNullOrEmpty(fieldListCategoryEntityList)) {
                for (FieldListCategoryEntity DataEntity : fieldListCategoryEntityList) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", DataEntity.getId());
                    jsonObject2.put("categoryTechnicalName",
                            StringUtil.isNotNullOrEmpty(DataEntity.getCategoryTechnicalName())
                                    ? DataEntity.getCategoryTechnicalName()
                                    : "");
                    jsonObject2.put("categoryNameOnMessage",
                            StringUtil.isNotNullOrEmpty(DataEntity.getCategoryNameOnMessage())
                                    ? DataEntity.getCategoryNameOnMessage()
                                    : "");
                    jsonObject2.put("fieldNameOnMessage",
                            StringUtil.isNotNullOrEmpty(DataEntity.getFieldNameOnMessage())
                                    ? DataEntity.getFieldNameOnMessage()
                                    : "");
                    jsonObject2.put("createdBy",
                            StringUtil.isNotNullOrEmpty(DataEntity.getCreatedBy()) ? DataEntity.getCreatedBy() : "");
                    jsonObject2.put("createdDate", DateUtil.getEpochTimeFromDate(DataEntity.getCreatedOn()));
                    jsonObject2.put("triggerEvent",
                            StringUtil.isNotNullOrEmpty(DataEntity.getTriggerEvent()) ? DataEntity.getTriggerEvent()
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FieldListCat Service - getFieldListCategoryList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to Delete Field List Category Record
     * 
     * @author Pradeep Ravichandran
     * @param fieldListCategoryEntity
     * @return
     */
    public ResponseEntity<String> deleteFieldListCategory(FieldListCategoryEntity fieldListCategoryEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int count = 0;
        try {
            if (fieldListCategoryEntity != null && StringUtil.isNotNullOrEmpty(fieldListCategoryEntity.getCreatedBy())
                    && fieldListCategoryEntity.getId() != null && fieldListCategoryEntity.getId() > 0) {
                count = fieldListCategoryRepository.deleteFieldListCategory(fieldListCategoryEntity.getId(),
                        fieldListCategoryEntity.getCreatedBy());
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
                jsonObject.put("error", UUID.randomUUID().toString());
                if (fieldListCategoryEntity == null) {
                    jsonObject.put("resString", MessageConstants.EMPTY_DATA);
                } else if (StringUtil.isNullOrEmpty(fieldListCategoryEntity.getCreatedBy())) {
                    jsonObject.put("resString", MessageConstants.CREATED_BY_NULL);
                } else {
                    jsonObject.put("resString", MessageConstants.ID_NULL);
                }
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FieldListCat Service - deleteFieldListCategory");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get All Trigger Field List
     * 
     * @author Pradeep Ravichandran
     * @return List of Trigger Field List
     */
    public ResponseEntity<String> getAllTriggerFieldList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int i = 1;
        try {
            List<String> triggerFieldList = extractedDataFieldRepository.findAllECNames();
            if (StringUtil.isListNotNullOrEmpty(triggerFieldList)) {
                triggerFieldList.forEach(tfl -> {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("name", StringUtil.checkObjectNull(tfl));
                    array.put(jsonObject2);
                });
                jsonObject.put("data", array);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FieldListCat Service - getAllTriggerFieldList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save Field List Category Detail
     * 
     * @author Pradeep
     * @param flcEntity
     * @return
     */
    public ResponseEntity<String> saveFieldListCategoryDetail(FieldListCategoryEntity flcEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<String> validateSaveMessages = fieldListCategoryHelper.validateFieldListCategory(flcEntity);
            if (StringUtil.isListNullOrEmpty(validateSaveMessages)) {
                FieldListCategoryEntity newData = fieldListCategoryRepository.save(flcEntity);
                if (newData != null) {
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("id", newData.getId());
                    dataObj.put("categoryNameOnMessage",
                            StringUtil.checkObjectNull(newData.getCategoryNameOnMessage()));
                    dataObj.put("categoryTechnicalName",
                            StringUtil.checkObjectNull(newData.getCategoryTechnicalName()));
                    dataObj.put("triggerEvent", StringUtil.checkObjectNull(newData.getTriggerEvent()));
                    dataObj.put("fieldListNames",
                            StringUtil.isListNotNullOrEmpty(newData.getFieldListing()) ? newData.getFieldListing()
                                    : "");
                    dataObj.put("createdBy", StringUtil.checkObjectNull(newData.getCreatedBy()));
                    dataObj.put("createdDate", DateUtil.getEpochTimeFromDate(newData.getCreatedOn()));
                    array.put(dataObj);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                            MessageConstants.ERROR_CODE);
                }
            } else {
                jsonObject.put("error", UUID.randomUUID().toString());
                jsonObject.put("data", validateSaveMessages);
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FieldListCat Service - saveFieldListCategoryDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to update Field List Category
     * 
     * @author Pradeep
     * @param flcEntity
     * @return
     */
    public ResponseEntity<String> updateFieldListCategoryDetail(FieldListCategoryEntity flcEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        List<String> validateSaveMessages = null;
        try {
            Optional<FieldListCategoryEntity> oldNewData = fieldListCategoryRepository.findById(flcEntity.getId());
            if (oldNewData != null
                    && !oldNewData.get().getCategoryTechnicalName().equals(flcEntity.getCategoryTechnicalName())) {
                validateSaveMessages = fieldListCategoryHelper.validateFieldListCategory(flcEntity);
            }
            if (StringUtil.isListNullOrEmpty(validateSaveMessages)) {
                FieldListCategoryEntity updatingEntity = oldNewData.get();
                updatingEntity.setCategoryTechnicalName(flcEntity.getCategoryTechnicalName());
                updatingEntity.setCategoryNameOnMessage(flcEntity.getCategoryNameOnMessage());
                updatingEntity.setTriggerEvent(flcEntity.getTriggerEvent());
                updatingEntity.setUpdatedBy(flcEntity.getCreatedBy());
                FieldListCategoryEntity updatedEntity = fieldListCategoryRepository.save(updatingEntity);
                if (updatedEntity != null) {
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("id", updatedEntity.getId());
                    dataObj.put("categoryNameOnMessage",
                            StringUtil.checkObjectNull(updatedEntity.getCategoryNameOnMessage()));
                    dataObj.put("categoryTechnicalName",
                            StringUtil.checkObjectNull(updatedEntity.getCategoryTechnicalName()));
                    dataObj.put("fieldListNames",
                            StringUtil.isListNotNullOrEmpty(updatedEntity.getFieldListing())
                                    ? updatedEntity.getFieldListing()
                                    : "");
                    dataObj.put("triggerEvent", StringUtil.checkObjectNull(updatedEntity.getTriggerEvent()));
                    dataObj.put("createdBy", StringUtil.checkObjectNull(updatedEntity.getCreatedBy()));
                    dataObj.put("createdDate", DateUtil.getEpochTimeFromDate(updatedEntity.getCreatedOn()));
                    array.put(dataObj);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_UPDATING_DATA,
                            MessageConstants.ERROR_CODE);
                }
            } else {
                jsonObject.put("error", UUID.randomUUID().toString());
                jsonObject.put("data", validateSaveMessages);
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FieldListCat Service - updateFieldListCategoryDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save Field List Name
     * 
     * @author Pradeep
     * @param flcEntity
     * @return
     */
    public ResponseEntity<String> saveFieldListName(FieldListCategoryEntity flcEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (StringUtil.isListNotNullOrEmpty(flcEntity.getFieldListing()) && flcEntity.getId() > 0
                    && StringUtil.isNotNullOrEmpty(flcEntity.getCreatedBy())) {
                Optional<FieldListCategoryEntity> newData = fieldListCategoryRepository.findById(flcEntity.getId());
                if (newData != null) {
                    FieldListCategoryEntity oldEntity = newData.get();
                    oldEntity.setFieldListing(flcEntity.getFieldListing());
                    oldEntity.setUpdatedBy(flcEntity.getCreatedBy());
                    oldEntity.setCreatedOn(new Date());
                    FieldListCategoryEntity updatedEntity = fieldListCategoryRepository.save(oldEntity);
                    if (updatedEntity != null) {
                        JSONObject dataObj = new JSONObject();
                        dataObj.put("id", oldEntity.getId());
                        dataObj.put("categoryNameOnMessage",
                                StringUtil.checkObjectNull(oldEntity.getCategoryNameOnMessage()));
                        dataObj.put("categoryTechnicalName",
                                StringUtil.checkObjectNull(oldEntity.getCategoryTechnicalName()));
                        dataObj.put("triggerEvent", StringUtil.checkObjectNull(oldEntity.getTriggerEvent()));
                        dataObj.put("fieldListNames",
                                StringUtil.isListNotNullOrEmpty(oldEntity.getFieldListing())
                                        ? oldEntity.getFieldListing()
                                        : "");
                        dataObj.put("createdDate", DateUtil.getEpochTimeFromDate(oldEntity.getCreatedOn()));
                        dataObj.put("createdBy", StringUtil.checkObjectNull(oldEntity.getCreatedBy()));
                        dataObj.put("createdDate", DateUtil.getEpochTimeFromDate(oldEntity.getCreatedOn()));
                        array.put(dataObj);
                        jsonObject.put("data", array);
                        jsonObject.put("message", MessageConstants.SUCCESS);
                        jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_UPDATING_DATA,
                                MessageConstants.ERROR_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                            MessageConstants.FAILED_CODE);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.MISSING_MANDATORY_FIELDS,
                        MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FieldListCat Service - saveFieldListName");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Field List Category By id
     *
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    public ResponseEntity<String> getFLCDetailsById(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (id > 0) {
                jsonObject = populateFLCDataForId(Integer.toUnsignedLong(id));
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL, MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FieldListCat Service - getFLCDetailsById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to populate FLC by passing parameter id
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    private JSONObject populateFLCDataForId(long id) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        Optional<FieldListCategoryEntity> newData = fieldListCategoryRepository.findById(id);
        if (newData != null) {
            FieldListCategoryEntity oldEntity = newData.get();
            JSONObject dataObj = new JSONObject();
            dataObj.put("id", oldEntity.getId());
            dataObj.put("categoryNameOnMessage", StringUtil.checkObjectNull(oldEntity.getCategoryNameOnMessage()));
            dataObj.put("categoryTechnicalName", StringUtil.checkObjectNull(oldEntity.getCategoryTechnicalName()));
            dataObj.put("triggerEvent", StringUtil.checkObjectNull(oldEntity.getTriggerEvent()));
            dataObj.put("fieldListNames",
                    StringUtil.isListNotNullOrEmpty(oldEntity.getFieldListing()) ? oldEntity.getFieldListing() : "");
            array.put(dataObj);
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
     * Method to get All Category List by Trigger Events
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getCategoryListByTriggerEvent(String triggerEvent) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (StringUtil.isNotNullOrEmpty(triggerEvent) && triggerEvent.equalsIgnoreCase("at")
                    || triggerEvent.equalsIgnoreCase("ft")) {
                List<String> triggerEventList = new ArrayList<>();
                triggerEventList.add("FT,AT");
                triggerEventList.add(triggerEvent.toUpperCase());
                List<FieldListCategoryEntity> categoryEntities = fieldListCategoryRepository
                        .getCategoryListByTriggerEvent(triggerEventList);
                if (StringUtil.isListNotNullOrEmpty(categoryEntities)) {
                    for (FieldListCategoryEntity value : categoryEntities) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("id", value.getId());
                        jsonObject2.put("name", value.getCategoryTechnicalName());
                        jsonObject2.put("nameOnMessage", value.getCategoryNameOnMessage());
                        array.put(jsonObject2);
                    }
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                            MessageConstants.FAILED_CODE);
                }
            } else {
                jsonObject.put("error", UUID.randomUUID().toString());
                if (StringUtil.isNullOrEmpty(triggerEvent)) {
                    jsonObject.put("resString", MessageConstants.TRIGGER_EVENT_NULL);
                } else {
                    jsonObject.put("resString", MessageConstants.INVALID_TRIGGER_EVENT);
                }
                jsonObject.put("data", "[]");
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " FieldListCat Service - getCategoryListByTriggerEvent");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
