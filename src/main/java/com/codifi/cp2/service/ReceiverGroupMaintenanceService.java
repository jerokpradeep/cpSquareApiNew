package com.codifi.cp2.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.codifi.cp2.entity.ArticleHierarchyEntity;
import com.codifi.cp2.entity.ArticleTypeEntity;
import com.codifi.cp2.entity.AutomatedTriggerEntity;
import com.codifi.cp2.entity.DcListEntity;
import com.codifi.cp2.entity.FreeTextMaintenanceEntity;
import com.codifi.cp2.entity.ReceiverGroupMaintenanceEntity;
import com.codifi.cp2.entity.ReceiverGroupUserEntity;
import com.codifi.cp2.entity.StreamTriggerEventEntity;
import com.codifi.cp2.entity.UserInformationEntity;
import com.codifi.cp2.model.response.ReceiverFreeTextModel;
import com.codifi.cp2.repository.ArticleHierarchyRepository;
import com.codifi.cp2.repository.ArticleTypeRepository;
import com.codifi.cp2.repository.AutomatedTriggerRepository;
import com.codifi.cp2.repository.DcListRepository;
import com.codifi.cp2.repository.FreeTextMaintenanceRepository;
import com.codifi.cp2.repository.ReceiverGroupMaintenanceRepository;
import com.codifi.cp2.repository.ReceiverGroupUserRepository;
import com.codifi.cp2.repository.StreamTriggerEventRepository;
import com.codifi.cp2.repository.UserInformationRepository;
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
public class ReceiverGroupMaintenanceService {
    @Autowired
    UserInformationRepository userInformationRepository;
    @Autowired
    ReceiverGroupMaintenanceRepository receiverGroupMaintenanceRepository;
    @Autowired
    ArticleHierarchyRepository articleHierarchyRepository;
    @Autowired
    DcListRepository dcListRepository;
    @Autowired
    AutomatedTriggerRepository automatedTriggerRepository;
    @Autowired
    FreeTextMaintenanceRepository freeTextMaintenanceRepository;
    @Autowired
    ReceiverGroupUserRepository receiverGroupUserRepository;
    @Autowired
    StreamTriggerEventRepository streamTriggerEventRepository;
    @Autowired
    ArticleTypeRepository articleTypeRepository;

    /**
     * Method To get All Receiver Group Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @return List of Receiver Group Maintenance List
     */
    public ResponseEntity<String> getReceiverGroupDetails() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<ReceiverGroupMaintenanceEntity> rgmDetailList = receiverGroupMaintenanceRepository.findAllActive();
            if (StringUtil.isListNotNullOrEmpty(rgmDetailList)) {
                for (ReceiverGroupMaintenanceEntity receiverGroupEntity : rgmDetailList) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", receiverGroupEntity.getId());
                    jsonObject2.put("receiverGroupId", StringUtil.checkObjectNull(receiverGroupEntity.getReceiverId()));
                    jsonObject2.put("receiverGroupName",
                            StringUtil.checkObjectNull(receiverGroupEntity.getReceiverName()));
                    if (StringUtil.isNotNullOrEmpty(receiverGroupEntity.getStreamName())
                            && receiverGroupEntity.getStreamName().equalsIgnoreCase("None")) {
                        jsonObject2.put("stream", "");
                    } else {
                        jsonObject2.put("stream", StringUtil.checkObjectNull(receiverGroupEntity.getStreamName()));
                    }
                    jsonObject2.put("createdOn", DateUtil.getEpochTimeFromDate(receiverGroupEntity.getCreatedOn()));
                    jsonObject2.put("createdBy", StringUtil.checkObjectNull(receiverGroupEntity.getCreatedBy()));
                    jsonObject2.put("status", StringUtil.checkObjectNull(receiverGroupEntity.getStatus()));
                    jsonObject2.put("contactPerson",
                            StringUtil.checkObjectNull(receiverGroupEntity.getContactPerson()));
                    jsonObject2.put("email", StringUtil.checkObjectNull(receiverGroupEntity.getContactEmail()));
                    jsonObject2.put("devPercent",
                            (receiverGroupEntity.getDevelopmentProgress() != 0
                                    || receiverGroupEntity.getDevelopmentProgress() > 0)
                                            ? receiverGroupEntity.getDevelopmentProgress()
                                            : 0);
                    array.put(jsonObject2);
                }
                jsonObject.put("data", array);
                jsonObject.put("resString", "Accessed Receiver Group List.");
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getReceiverGroupDetails");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to update Receiver Group Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @param rgmEntity
     * @return
     */
    public ResponseEntity<String> updateRGMStatus(ReceiverGroupMaintenanceEntity rgmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        long epoch = Instant.now().toEpochMilli();
        int count = 0;
        try {
            if (rgmEntity != null && StringUtil.isNotNullOrEmpty(rgmEntity.getStatus())
                    && StringUtil.isNotNullOrEmpty(rgmEntity.getCreatedBy()) && rgmEntity.getId() != null
                    && rgmEntity.getId() > 0) {
                if (rgmEntity.getStatus().equalsIgnoreCase("active")) {
                    count = receiverGroupMaintenanceRepository.updateStatusActive(rgmEntity.getId(),
                            rgmEntity.getStatus(), String.valueOf(epoch), rgmEntity.getCreatedBy());
                } else {
                    count = receiverGroupMaintenanceRepository.updateStatusInActiveAndUD(rgmEntity.getId(),
                            rgmEntity.getStatus(), String.valueOf(epoch), rgmEntity.getCreatedBy());
                }
                if (count > 0) {
                    Optional<ReceiverGroupMaintenanceEntity> entity = receiverGroupMaintenanceRepository
                            .findById(rgmEntity.getId());
                    if (entity != null) {
                        ReceiverGroupMaintenanceEntity udpatedRGMEntity = entity.get();
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("resultString", MessageConstants.STATUS_UPDATED);
                        jsonObject2.put("receiverGroupId", udpatedRGMEntity.getReceiverId());
                        jsonObject2.put("activatedOn", udpatedRGMEntity.getDateActivated());
                        jsonObject2.put("deActivatedOn", udpatedRGMEntity.getDateDeactivated());
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
                if (rgmEntity == null) {
                    jsonObject.put("resString", MessageConstants.EMPTY_DATA);
                } else if (StringUtil.isNullOrEmpty(rgmEntity.getStatus())) {
                    jsonObject.put("resString", MessageConstants.STATUS_NULL);
                } else if (StringUtil.isNullOrEmpty(rgmEntity.getCreatedBy())) {
                    jsonObject.put("resString", MessageConstants.CREATED_BY_NULL);
                } else {
                    jsonObject.put("resString", MessageConstants.ID_NULL);
                }
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - updateRGMStatus");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to Delete Receiver Group Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @param rgmEntity
     * @return
     */
    public ResponseEntity<String> deleteRGM(ReceiverGroupMaintenanceEntity rgmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (rgmEntity != null && StringUtil.isNotNullOrEmpty(rgmEntity.getCreatedBy()) && rgmEntity.getId() != null
                    && rgmEntity.getId() > 0) {
                Optional<ReceiverGroupMaintenanceEntity> entity = receiverGroupMaintenanceRepository
                        .findById(rgmEntity.getId());
                if (entity != null && StringUtil.isNotNullOrEmpty(entity.get().getStatus())
                        && !entity.get().getStatus().equalsIgnoreCase("active")
                        && StringUtil.isNotNullOrEmpty(entity.get().getReceiverId())) {
                    StreamTriggerEventEntity streamTriggerEventEntity = streamTriggerEventRepository
                            .findAllByTriggerID(entity.get().getReceiverId());
                    if (streamTriggerEventEntity != null && streamTriggerEventEntity.getId() != null
                            && streamTriggerEventEntity.getId() > 0) {
                        streamTriggerEventRepository.deleteById(streamTriggerEventEntity.getId());
                    }
                    ReceiverGroupUserEntity receiverGroupUserEntity = receiverGroupUserRepository
                            .findAllByTriggerID(entity.get().getReceiverId());
                    if (receiverGroupUserEntity != null && receiverGroupUserEntity.getId() != null
                            && receiverGroupUserEntity.getId() > 0) {
                        receiverGroupUserRepository.deleteById(receiverGroupUserEntity.getId());
                    }
                    receiverGroupMaintenanceRepository.deleteById(rgmEntity.getId());
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("response", MessageConstants.DELETE_SUCCESS);
                    array.put(jsonObject2);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ACTIVE_STATUS_NOT_DELETE,
                            MessageConstants.FAILED_CODE);
                }
            } else {
                jsonObject.put("error", UUID.randomUUID().toString());
                if (rgmEntity == null) {
                    jsonObject.put("resString", MessageConstants.EMPTY_DATA);
                } else if (StringUtil.isNullOrEmpty(rgmEntity.getCreatedBy())) {
                    jsonObject.put("resString", MessageConstants.CREATED_BY_NULL);
                } else {
                    jsonObject.put("resString", MessageConstants.ID_NULL);
                }
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - deleteRGM");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save Receiver Group Details
     * 
     * @author Pradeep Ravichandran
     * @param rgmEntity
     * @return
     */
    public ResponseEntity<String> saveReceiverGroupDetail(ReceiverGroupMaintenanceEntity rgmEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            rgmEntity.setActiveStatus(1);
            rgmEntity.setStatus(MessageConstants.CONST_STATUS_UD);
            if (StringUtil.isNotNullOrEmpty(rgmEntity.getReceiverName())) {
                ReceiverGroupMaintenanceEntity newEntity = receiverGroupMaintenanceRepository.save(rgmEntity);
                if (newEntity != null) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("resultString", MessageConstants.SAVE_OBJECT);
                    if (StringUtil.isNotNullOrEmpty(newEntity.getStreamName())
                            && newEntity.getStreamName().equalsIgnoreCase("None")) {// Ticket No 59509
                        jsonObject2.put("streamName", "");
                    } else {
                        jsonObject2.put("streamName", newEntity.getStreamName());
                    }
                    jsonObject2.put("id", newEntity.getId());
                    jsonObject2.put("receiverGroupName", newEntity.getReceiverName());
                    jsonObject2.put("receiverGroupId", newEntity.getReceiverId());
                    array.put(jsonObject2);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                            MessageConstants.ERROR_CODE);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECEIVER_TRIGGER_NAME_NULL,
                        MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - saveReceiverGroupDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to update Receiver Group Details
     * 
     * @author Pradeep Ravichandran
     * @param rgmEntity
     * @return
     */
    public ResponseEntity<String> updateReceiverGroupDetail(ReceiverGroupMaintenanceEntity rgmEntity) {
        JSONObject jsonObject = new JSONObject();
        boolean isValidName = true;
        try {
            if (StringUtil.isNotNullOrEmpty(rgmEntity.getReceiverName())) {
                ReceiverGroupMaintenanceEntity oldData = receiverGroupMaintenanceRepository
                        .findDataById(rgmEntity.getId());
                if (oldData != null && !oldData.getReceiverName().equals(rgmEntity.getReceiverName())) {
                    ReceiverGroupMaintenanceEntity namePresentData = receiverGroupMaintenanceRepository
                            .getDataByTriggerName(rgmEntity.getReceiverName());
                    if (namePresentData != null && StringUtil.isNotNullOrEmpty(namePresentData.getReceiverName())) {
                        isValidName = false;
                    }
                }
                if (isValidName) {
                    System.out.println("Valid Name : yes");
                    int updateDetails = receiverGroupMaintenanceRepository.updateReceiverGroupDetail(
                        rgmEntity.getReceiverName(), 
                            rgmEntity.getReceiverGroupEmail(), rgmEntity.getContactPerson(),
                            rgmEntity.getContactEmail(), rgmEntity.getMessageDelivery(),
                            rgmEntity.getExcelConsolidation(), rgmEntity.getWeekday(), rgmEntity.getHousekeepingTime(),
                            rgmEntity.getCreatedBy(),rgmEntity.getId());
                    if (updateDetails > 0) {
                        ReceiverGroupMaintenanceEntity updatedEntity = receiverGroupMaintenanceRepository
                                .findDataById(rgmEntity.getId());
                        updatedEntity.setDcListing(rgmEntity.getDcListing());
                        receiverGroupMaintenanceRepository.save(updatedEntity);
                        jsonObject = populateRGMDataForId(rgmEntity.getId());
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_UPDATING_DATA,
                                MessageConstants.ERROR_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECEIVER_TRIGGER_NAME_EXIST,
                            MessageConstants.ERROR_CODE);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECEIVER_TRIGGER_NAME_NULL,
                        MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - updateReceiverGroupDetail");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Receiver Group Maintenance Data Field By id
     *
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    public ResponseEntity<String> getRGMDetailsById(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (id > 0) {
                jsonObject = populateRGMDataForId(Integer.toUnsignedLong(id));
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL, MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getRGMDetailsById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Receiver Group Maintenance User Details By id
     *
     * @author Jayaprakash Ponnusamy
     * @param id
     * @return
     */
    public ResponseEntity<String> getRGMUserDetailsById(int id) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (id > 0) {
                jsonObject = populateRGMUserDataForId(Integer.toUnsignedLong(id));
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL, MessageConstants.ERROR_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getRGMDetailsById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Method to populate RGM Data by passing parameter id
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    private JSONObject populateRGMDataForId(long id) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        Optional<ReceiverGroupMaintenanceEntity> newData = receiverGroupMaintenanceRepository.findById(id);
        if (newData != null) {
            ReceiverGroupMaintenanceEntity oldEntity = newData.get();
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("id", oldEntity.getId());
            jsonObject2.put("receiverGroupId", StringUtil.checkObjectNull(oldEntity.getReceiverId()));
            jsonObject2.put("receiverGroupName", StringUtil.checkObjectNull(oldEntity.getReceiverName()));
            jsonObject2.put("receiverGroupEmail", StringUtil.checkObjectNull(oldEntity.getReceiverGroupEmail()));
            jsonObject2.put("contactPerson", StringUtil.checkObjectNull(oldEntity.getContactPerson()));
            jsonObject2.put("email", StringUtil.checkObjectNull(oldEntity.getContactEmail()));
            jsonObject2.put("dcListing",
                    StringUtil.isListNotNullOrEmpty(oldEntity.getDcListing()) ? oldEntity.getDcListing() : "");
            jsonObject2.put("messageDelivery", StringUtil.checkObjectNull(oldEntity.getMessageDelivery()));
            jsonObject2.put("weekday", StringUtil.checkObjectNull(oldEntity.getWeekday()));
            jsonObject2.put("excelConsolidation", oldEntity.getExcelConsolidation());
            jsonObject2.put("houseKeepingTime", oldEntity.getHousekeepingTime());
            ReceiverGroupUserEntity receiverGroupData = receiverGroupUserRepository
                    .findAllByTriggerID(oldEntity.getReceiverId());
            if (receiverGroupData != null) {
                jsonObject2.put("users",
                        receiverGroupData.getUserJson() != null ? receiverGroupData.getUserJson() : "");
            }
            StreamTriggerEventEntity streamTriggerEventEntity = streamTriggerEventRepository
                    .findAllByTriggerID(oldEntity.getReceiverId());
            if (streamTriggerEventEntity != null) {
                jsonObject2.put("eventJson",
                        streamTriggerEventEntity.getEventJson() != null ? streamTriggerEventEntity.getEventJson() : "");
                jsonObject2.put("freeTextJson",
                        streamTriggerEventEntity.getFreeTextJson() != null ? streamTriggerEventEntity.getFreeTextJson()
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
     * Method to populate RGM Users Data by passing parameter id
     * 
     * @author Jayaprakash Ponnusamy
     * @param id
     * @return
     */
    private JSONObject populateRGMUserDataForId(long id) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        Optional<ReceiverGroupMaintenanceEntity> newData = receiverGroupMaintenanceRepository.findById(id);
        if (newData != null) {
            ReceiverGroupMaintenanceEntity oldEntity = newData.get();
            JSONObject jsonObject2 = new JSONObject();
            
            ReceiverGroupUserEntity receiverGroupData = receiverGroupUserRepository
                    .findAllByTriggerID(oldEntity.getReceiverId());
            if (receiverGroupData != null) {
                jsonObject2.put("users",
                        receiverGroupData.getUserJson() != null ? receiverGroupData.getUserJson() : "");
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
     * Method To get All Article Name List
     * 
     * @author Pradeep Ravichandran
     * @return List of Article Name List
     */
    public ResponseEntity<String> getAllArticleNameList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray list = new JSONArray();
        try {
            List<ArticleHierarchyEntity> articleHierarchyEntities = articleHierarchyRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(articleHierarchyEntities)) {
                articleHierarchyEntities.forEach(ahe -> {
                    if (StringUtil.isNotNullOrEmpty(ahe.getName())) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", ahe.getId());
                        jsonObject1.put("name", StringUtil.checkObjectNull(ahe.getName()));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getAllArticleNameList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    /**
     * Method To get All Dc List
     * 
     * @author Pradeep Ravichandran
     * @return List of Article Name List
     */
    public ResponseEntity<String> getAllDCList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray list = new JSONArray();
        try {
            List<DcListEntity> dcListEntities = dcListRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(dcListEntities)) {
                dcListEntities.forEach(dle -> {
                    if (StringUtil.isNotNullOrEmpty(dle.getDcName())) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", dle.getDcId());
                        jsonObject1.put("name", StringUtil.checkObjectNull(dle.getDcName()));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getAllDCList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get All Trigger Name List
     * 
     * @author JP
     * @return List of Article Name List
     */
    public ResponseEntity<String> getAllTriggerNameList(String triggerType) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        boolean containData = false;
        try {
            // construct for both object
            if (triggerType.equalsIgnoreCase("FT")) {
                List<FreeTextMaintenanceEntity> freeTextMaintenanceEntities = freeTextMaintenanceRepository
                        .findAllActive();
                if (StringUtil.isListNotNullOrEmpty(freeTextMaintenanceEntities)) {
                    containData = true;
                    freeTextMaintenanceEntities.forEach(ftm -> {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("triggerId", ftm.getFreeTextTriggerId());
                        jsonObject2.put("triggerNameEnglish", StringUtil.checkObjectNull(ftm.getTriggerNameEnglish()));
                        jsonObject2.put("triggerNameGerman", StringUtil.checkObjectNull(ftm.getTriggerNameGerman()));
                        array.put(jsonObject2);
                    });
                }
            } else {
                List<AutomatedTriggerEntity> automatedTriggerDetailList = automatedTriggerRepository.findAllActive();
                if (StringUtil.isListNotNullOrEmpty(automatedTriggerDetailList)) {
                    containData = true;
                    automatedTriggerDetailList.forEach(atm -> {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("triggerId", atm.getAutomatedTriggerId());
                        jsonObject2.put("triggerNameEnglish", StringUtil.checkObjectNull(atm.getTriggerNameEnglish()));
                        jsonObject2.put("triggerNameGerman", StringUtil.checkObjectNull(atm.getTriggerNameGerman()));
                        array.put(jsonObject2);
                    });
                }
            }
            if (containData) {
                jsonObject.put("data", array);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getAllTriggerNameList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to save User Details
     * 
     * @author Pradeep
     * @param receiverGroupUserEntity
     * @return
     */
    public ResponseEntity<String> saveReceiverUser(ReceiverGroupUserEntity receiverGroupUserEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            ReceiverGroupUserEntity oldEntity = receiverGroupUserRepository
                    .findAllByTriggerID(receiverGroupUserEntity.getReceiverId());
            if (oldEntity != null) {
                oldEntity.setReceiverLong(receiverGroupUserEntity.getReceiverLong());
                oldEntity.setUserJson(receiverGroupUserEntity.getUserJson());
                oldEntity.setUpdatedBy(receiverGroupUserEntity.getCreatedBy());
                oldEntity.setUpdatedOn(new Date());
                ReceiverGroupUserEntity newEntity = receiverGroupUserRepository.save(oldEntity);
                if (newEntity != null) {
                    int id = receiverGroupMaintenanceRepository.getIdByTriggerID(newEntity.getReceiverId());
                    jsonObject = populateRGMDataForId(Integer.toUnsignedLong(id));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - saveReceiverUser");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // /**
    //  * Method to save User Details
    //  * 
    //  * @author Jayaprakash Ponnusamy
    //  * @param receiverGroupUserEntity
    //  * @return
    //  */
    // public ResponseEntity<String> saveUserDetails(UserInformationEntity userInformationEntity) {
    //     JSONObject jsonObject = new JSONObject();
    //     try {
            
    //         UserInformationEntity entity = userInformationRepository.findByUserId(userInformationEntity.getUserId());
    //         if (entity != null) {
    //             oldEntity.setReceiverLong(receiverGroupUserEntity.getReceiverLong());
    //             oldEntity.setUserJson(receiverGroupUserEntity.getUserJson());
    //             oldEntity.setUpdatedBy(receiverGroupUserEntity.getCreatedBy());
    //             oldEntity.setUpdatedOn(new Date());
    //             ReceiverGroupUserEntity newEntity = receiverGroupUserRepository.save(oldEntity);
    //             if (newEntity != null) {
    //                 int id = receiverGroupMaintenanceRepository.getIdByTriggerID(newEntity.getReceiverId());
    //                 jsonObject = populateRGMDataForId(Integer.toUnsignedLong(id));
    //             } else {
    //                 jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
    //                         MessageConstants.ERROR_CODE);
    //             }
    //         } else {
    //             jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
    //                     MessageConstants.FAILED_CODE);
    //         }
    //         return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    //     } catch (Exception e) {
    //         jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - saveUserDetails");
    //         return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    /**
     * Method to save Trigger Details
     * 
     * @author Pradeep
     * @param streamTriggerEventEntity
     * @return
     */
    public ResponseEntity<String> saveTriggerEvent(StreamTriggerEventEntity streamTriggerEventEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            StreamTriggerEventEntity oldEntity = streamTriggerEventRepository
                    .findAllByTriggerID(streamTriggerEventEntity.getReceiverId());
            if (oldEntity != null) {
                oldEntity.setReceiverLong(streamTriggerEventEntity.getReceiverLong());
                if (streamTriggerEventEntity.getEventType().equalsIgnoreCase("FT")) {
                    oldEntity.setFreeTextJson(streamTriggerEventEntity.getFreeTextJson());
                } else {
                    oldEntity.setEventJson(streamTriggerEventEntity.getEventJson());
                }
                oldEntity.setUpdatedBy(streamTriggerEventEntity.getCreatedBy());
                oldEntity.setUpdatedOn(new Date());
                StreamTriggerEventEntity newEntity = streamTriggerEventRepository.save(oldEntity);
                if (newEntity != null) {
                    int id = receiverGroupMaintenanceRepository.getIdByTriggerID(newEntity.getReceiverId());
                    jsonObject = populateRGMDataForId(Integer.toUnsignedLong(id));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - saveTriggerEvent");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get All Activated ATM trigger names
     * 
     * @author Pradeep Ravichandran
     * @return List of ATM trigger names
     */
    public ResponseEntity<String> getAutomatedTriggerDetails() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<AutomatedTriggerEntity> automatedTriggerEntities = automatedTriggerRepository
                    .findActiveTriggerDetails();
            if (StringUtil.isListNotNullOrEmpty(automatedTriggerEntities)) {
                automatedTriggerEntities.forEach(atd -> {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", atd.getId());
                    jsonObject2.put("triggerID", StringUtil.checkObjectNull(atd.getAutomatedTriggerId()));
                    jsonObject2.put("triggerNameEnglish", StringUtil.checkObjectNull(atd.getTriggerNameEnglish()));
                    jsonObject2.put("triggerNameGerman", StringUtil.checkObjectNull(atd.getTriggerNameGerman()));
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
        } catch (

        Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(),
                    " RGM Service - getAutomatedTriggerDetails");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get All Article Type List
     * 
     * @author Pradeep Ravichandran
     * @return List of Article Type List
     */
    public ResponseEntity<String> getArticleTypeLists() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<ArticleTypeEntity> articleTypeEntities = articleTypeRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(articleTypeEntities)) {
                articleTypeEntities.forEach(atl -> {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", atl.getId());
                    jsonObject2.put("code", StringUtil.checkObjectNull(atl.getCode()));
                    jsonObject2.put("field", StringUtil.checkObjectNull(atl.getField()));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getArticleTypeLists");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get Mapped Free Text Details
     * 
     * @author Pradeep Ravichandran
     * @return List of Free Text List
     */
    public ResponseEntity<String> getMappedFreeTextList(String receiverID) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (StringUtil.isNotNullOrEmpty(receiverID)) {
                List<ReceiverFreeTextModel> freeTextModels = freeTextMaintenanceRepository
                        .getMappedFreeTextList(receiverID);
                if (StringUtil.isListNotNullOrEmpty(freeTextModels)) {
                    freeTextModels.forEach(models -> {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("triggerId", StringUtil.checkObjectNull(models.getTriggerId()));
                        jsonObject2.put("triggerNameEnglish", StringUtil.checkObjectNull(models.getEngName()));
                        jsonObject2.put("triggerNameGerman", StringUtil.checkObjectNull(models.getGermanName()));
                        jsonObject2.put("Status", StringUtil.checkObjectNull(models.getStatus()));
                        jsonObject2.put("devPercent",
                                (models.getdevProgress() != 0 && models.getdevProgress() > 0) ? models.getdevProgress()
                                        : 0);
                        array.put(jsonObject2);
                    });
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject.put("error", UUID.randomUUID().toString());
                    jsonObject.put("resString", MessageConstants.RECORD_NOT_FOUND + receiverID);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.FAILED);
                    jsonObject.put("status", MessageConstants.FAILED_CODE);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECEIVER_TRIGGER_ID_NULL,
                        MessageConstants.FAILED_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getMappedFreeTextList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get Receiver Details by userID
     * 
     * @author Pradeep Ravichandran
     * @return List of Receiver Group List
     */
    public ResponseEntity<String> getRecieverListByUser(String loginId) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (StringUtil.isNotNullOrEmpty(loginId)) {
                List<String> receiverList = receiverGroupUserRepository.findReceiverGroupList(loginId);
                if (StringUtil.isListNotNullOrEmpty(receiverList)) {
                    jsonObject.put("data", receiverList);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + loginId,
                            MessageConstants.FAILED_CODE);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL,
                        MessageConstants.FAILED_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGM Service - getRecieverListByUser");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
