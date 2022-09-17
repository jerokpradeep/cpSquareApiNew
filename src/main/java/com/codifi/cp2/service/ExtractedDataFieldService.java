package com.codifi.cp2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.codifi.cp2.entity.ExtractedDataFieldEntity;
import com.codifi.cp2.entity.ExtractedMasterDataEntity;
import com.codifi.cp2.helper.ExtractedDataFieldHelper;
import com.codifi.cp2.repository.ExtractedDataFieldRepository;
import com.codifi.cp2.repository.ExtractedMasterDataRepository;
import com.codifi.cp2.util.CommonMethods;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExtractedDataFieldService {

    @Autowired
    ExtractedDataFieldRepository extractedDataFieldRepository;
    @Autowired
    ExtractedDataFieldHelper extractedDataFieldHelper;
    @Autowired
    ExtractedMasterDataRepository extractedMasterDataRepository;

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
            List<ExtractedDataFieldEntity> extractedDataEntityList = extractedDataFieldRepository.findAllActive();
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " ExDataField Service - getExtractedDataFieldList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to Delete Extracted Data Field List Record
     * 
     * @author Pradeep Ravichandran
     * @param extractedDataFieldEntity
     * @return
     */
    public ResponseEntity<String> deleteExtractedDataFieldList(ExtractedDataFieldEntity extractedDataFieldEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int count = 0;
        try {
            if (extractedDataFieldEntity != null && StringUtil.isNotNullOrEmpty(extractedDataFieldEntity.getCreatedBy())
                    && extractedDataFieldEntity.getId() != null && extractedDataFieldEntity.getId() > 0) {
                count = extractedDataFieldRepository.deleteExtractedDataFieldList(extractedDataFieldEntity.getId(),
                        extractedDataFieldEntity.getCreatedBy());
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
                if (extractedDataFieldEntity == null) {
                    jsonObject.put("resString", MessageConstants.EMPTY_DATA);
                } else if (StringUtil.isNullOrEmpty(extractedDataFieldEntity.getCreatedBy())) {
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
                    " ExDataField Service - deleteExtractedDataFieldList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save Extracted data Field List
     * 
     * @author Pradeep Ravichandran
     * @param extractedDataFieldEntity
     * @return
     */
    public ResponseEntity<String> saveExtractedDataFieldList(ExtractedDataFieldEntity extractedDataFieldEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        System.out.println("Extracted Data Field Entity ++++++++++++++" + extractedDataFieldEntity.getMessageRelevant());
        try {
            List<String> validateSaveMessages = extractedDataFieldHelper
                    .validateExtractedDataFieldList(extractedDataFieldEntity);
            if (StringUtil.isListNullOrEmpty(validateSaveMessages)) {
                ExtractedDataFieldEntity newData = extractedDataFieldRepository.save(extractedDataFieldEntity);
                if (newData != null) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", newData.getId());
                    jsonObject2.put("tableName",
                            StringUtil.isNotNullOrEmpty(newData.getTableName()) ? newData.getTableName() : "");
                    jsonObject2.put("fieldName",
                            StringUtil.isNotNullOrEmpty(newData.getFieldName()) ? newData.getFieldName() : "");
                    jsonObject2.put("oDataServiceName",
                            StringUtil.isNotNullOrEmpty(newData.getOdataService()) ? newData.getOdataService() : "");
                    jsonObject2.put("sapFieldName",
                            StringUtil.isNotNullOrEmpty(newData.getSapFieldName()) ? newData.getSapFieldName() : "");
                    jsonObject2.put("easyName",
                            StringUtil.isNotNullOrEmpty(newData.getEasyName()) ? newData.getEasyName() : "");
                    jsonObject2.put("dateType",
                            StringUtil.isNotNullOrEmpty(newData.getDateType()) ? newData.getDateType() : "");
                    jsonObject2.put("messageRelevant",
                            StringUtil.isNotNullOrEmpty(newData.getMessageRelevant()) ? newData.getMessageRelevant() : "");
                    array.put(jsonObject2);
                    System.out.println("Message Relevant ====================" + newData.getMessageRelevant());
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
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " ExDataField Service - saveExtractedDataFieldList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Extracted Data Field By id
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    public ResponseEntity<String> getExtractedDataById(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (id > 0) {
                jsonObject = populateEDFlistForId(Integer.toUnsignedLong(id));
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL, MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " ExDataField Service - getExtractedDataById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to populate Extracted Data Field list by passing parameter id
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    private JSONObject populateEDFlistForId(long id) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        Optional<ExtractedDataFieldEntity> newData = extractedDataFieldRepository.findById(id);
        if (newData != null) {
            ExtractedDataFieldEntity extractedDataEntity = newData.get();
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("id", extractedDataEntity.getId());
            jsonObject2.put("tableName",
                    StringUtil.isNotNullOrEmpty(extractedDataEntity.getTableName()) ? extractedDataEntity.getTableName()
                            : "");
            jsonObject2.put("fieldName",
                    StringUtil.isNotNullOrEmpty(extractedDataEntity.getFieldName()) ? extractedDataEntity.getFieldName()
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
                    StringUtil.isNotNullOrEmpty(extractedDataEntity.getEasyName()) ? extractedDataEntity.getEasyName()
                            : "");
            jsonObject2.put("dateType",
                    StringUtil.isNotNullOrEmpty(extractedDataEntity.getDateType()) ? extractedDataEntity.getDateType()
                            : "");
            array.put(jsonObject2);
            jsonObject.put("data", array);
            jsonObject.put("message", MessageConstants.SUCCESS);
            jsonObject.put("status", MessageConstants.SUCCESS_CODE);
        } else {
            jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                    MessageConstants.FAILED_CODE);
        }
        return jsonObject;
    }

    /**
     * Method to get Extracted Master Data for screen
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getExtractedMasterData() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        Map<String, JSONArray> extractedDataMap = new HashMap<>();
        try {
            List<ExtractedMasterDataEntity> extractedMasterDataEntities = extractedMasterDataRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(extractedMasterDataEntities)) {
                extractedMasterDataEntities.forEach(emd -> {
                    JSONArray array1 = new JSONArray();
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("filedName", emd.getFieldName());
                    jsonObject2.put("oData", emd.getOdataService());
                    jsonObject2.put("sapFieldName", emd.getSapFieldName());
                    jsonObject2.put("dateType", emd.getDateType());
                    array1.put(jsonObject2);
                    if (extractedDataMap.containsKey(emd.getTableName())) {
                        extractedDataMap.get(emd.getTableName()).put(jsonObject2);
                    } else {
                        extractedDataMap.put(emd.getTableName(), array1);
                    }
                });
                extractedDataMap.entrySet().forEach(map -> {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("tableName", map.getKey());
                    jsonObject1.put("data", map.getValue());
                    array.put(jsonObject1);
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
                    " ExDataField Service - getExtractedMasterData");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
