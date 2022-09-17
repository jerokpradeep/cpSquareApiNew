package com.codifi.cp2.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.codifi.cp2.entity.ArticleHierarchyEntity;
import com.codifi.cp2.entity.CommodityGroupEntity;
import com.codifi.cp2.entity.NoteListEntity;
import com.codifi.cp2.entity.OrderAreaListEntity;
import com.codifi.cp2.entity.ProductHierarchyEntity;
import com.codifi.cp2.entity.ReceiverGroupDataEntity;
import com.codifi.cp2.helper.RGWHelper;
import com.codifi.cp2.model.request.RGWFilterModel;
import com.codifi.cp2.model.response.GetAllTriggerEvent;
import com.codifi.cp2.model.response.TriggerEventResponseModel;
import com.codifi.cp2.repository.ArticleHierarchyRepository;
import com.codifi.cp2.repository.AutomatedTriggerRepository;
import com.codifi.cp2.repository.CommodityGroupRepository;
import com.codifi.cp2.repository.FreeTextMaintenanceRepository;
import com.codifi.cp2.repository.NoteListRepository;
import com.codifi.cp2.repository.OrderAreaListRepository;
import com.codifi.cp2.repository.ProductHierarchyRepository;
import com.codifi.cp2.repository.ReceiverGroupDataRepository;
import com.codifi.cp2.util.CommonMethods;
import com.codifi.cp2.util.DateUtil;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RGWService {

    @Autowired
    FreeTextMaintenanceRepository freeTextMaintenanceRepository;
    @Autowired
    AutomatedTriggerRepository automatedTriggerRepository;
    @Autowired
    ReceiverGroupDataRepository receiverGroupDataRepository;
    @Autowired
    RGWHelper rgwHelper;
    @Autowired
    NoteListRepository noteListRepository;
    @Autowired
    ArticleHierarchyRepository articleHierarchyRepository;
    @Autowired
    CommodityGroupRepository commodityGroupRepository;
    @Autowired
    OrderAreaListRepository orderAreaListRepository;
    @Autowired
    ProductHierarchyRepository productHierarchyRepository;

    /**
     * Method to get All trigger Events by trigger Event type
     * 
     * @author Pradeep Ravichandran
     * @param triggerType // All, ATM, FTM
     * @return
     */
    public ResponseEntity<String> getTriggerEvents(String triggerType) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<GetAllTriggerEvent> atmRecords = null;
            List<GetAllTriggerEvent> ftmRecords = null;
            List<TriggerEventResponseModel> resultModel = new ArrayList<>();
            if (StringUtil.isEqual(triggerType, "ATM") || StringUtil.isEqual(triggerType, "ALL")) {
                atmRecords = automatedTriggerRepository.findAllTriggerEvents();
            }
            if (StringUtil.isEqual(triggerType, "FTM") || StringUtil.isEqual(triggerType, "ALL")) {
                ftmRecords = freeTextMaintenanceRepository.findAllTriggerEvents();
            }
            if (StringUtil.isListNotNullOrEmpty(atmRecords)) {
                // int atmTotalMsgcount =
                // receiverGroupDataRepository.findATMTotalMessageCount();
                atmRecords.forEach(atm -> {
                    TriggerEventResponseModel model = new TriggerEventResponseModel();
                    model.setTriggerId(StringUtil.checkObjectNull(atm.getTriggerId()));
                    model.setTriggerName(StringUtil.checkObjectNull(atm.getTriggerName()));
                    model.setFilteredMessageCount(0);
                    model.setTotalMessageCount(atm.getfilteredMessageCount());
                    resultModel.add(model);

                });
            }
            if (StringUtil.isListNotNullOrEmpty(ftmRecords)) {
                // int ftmTotalMsgcount =
                // receiverGroupDataRepository.findFTMTotalMessageCount();
                ftmRecords.forEach(ftm -> {
                    TriggerEventResponseModel model = new TriggerEventResponseModel();
                    model.setTriggerId(StringUtil.checkObjectNull(ftm.getTriggerId()));
                    model.setTriggerName(StringUtil.checkObjectNull(ftm.getTriggerName()));
                    model.setFilteredMessageCount(0);
                    model.setTotalMessageCount(ftm.getfilteredMessageCount());
                    resultModel.add(model);
                });
            }
            if (StringUtil.isListNotNullOrEmpty(resultModel)) {
                jsonObject.put("data", resultModel);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getTriggerEvents");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Messages by trigger id.
     * 
     * @author Pradeep Ravichandran
     * @param triggerId // ATM id or FTM id
     * @return message
     * 
     */
    public ResponseEntity<String> getMessageById(String triggerId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            List<ReceiverGroupDataEntity> dataEntities = receiverGroupDataRepository.findByTriggerId(triggerId);
            if (StringUtil.isListNotNullOrEmpty(dataEntities)) {
                dataEntities.forEach(rgd -> {
                    JSONObject value = new JSONObject();
                    value.put("aldiCommodityGroup", StringUtil.checkObjectNull(rgd.getAldiCommodityGroup()));
                    value.put("assignedTo", StringUtil.checkObjectNull(rgd.getAssignedTo()));
                    value.put("claimed", rgd.isClaimed());
                    value.put("displayIdDescription", StringUtil.checkObjectNull(rgd.getDisplayIdDescription()));
                    value.put("messageId", StringUtil.checkObjectNull(rgd.getMessageNumber()));
                    value.put("postedOn", StringUtil.checkObjectNull(rgd.getPostedDate()));
                    value.put("postedTime", StringUtil.checkObjectNull(rgd.getPostedDate()));
                    value.put("productHierarchy", StringUtil.checkObjectNull(rgd.getProductHierarchyCode()));
                    value.put("read", rgd.isRead());
                    value.put("status", StringUtil.checkObjectNull(rgd.getStatusMessage()));
                    jsonArray.put(value);
                });
                jsonObject.put("data", jsonArray);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getMessageById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to Search the Messages
     * 
     * @author Jayaprakash Ponnusamy
     * @param triggerId // ATM id or FTM id
     * @return message
     * 
     */
    public ResponseEntity<String> searchMessage(String triggerId, String searchText) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            List<ReceiverGroupDataEntity> dataEntities = receiverGroupDataRepository.findByTriggerId(triggerId);
            if (StringUtil.isListNotNullOrEmpty(dataEntities)) {
                dataEntities.forEach(rgd -> {
                    boolean isContain = false;
                    JSONObject value = new JSONObject();
                    if (StringUtil.checkObjectNull(rgd.getAldiCommodityGroup()).toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getAssignedTo()).toLowerCase().contains(searchText)
                            || new Boolean(rgd.isClaimed()).toString().toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getDisplayIdDescription()).toLowerCase()
                                    .contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getMessageNumber()).toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getPostedDate()).toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getProductHierarchyCode()).toLowerCase()
                                    .contains(searchText)
                            || new Boolean(rgd.isRead()).toString().toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getStatusMessage()).toLowerCase().contains(searchText)) {
                        isContain = true;
                    }
                    if (isContain) {
                        value.put("aldiCommodityGroup", StringUtil.checkObjectNull(rgd.getAldiCommodityGroup()));
                        value.put("assignedTo", StringUtil.checkObjectNull(rgd.getAssignedTo()));
                        value.put("claimed", rgd.isClaimed());
                        value.put("displayIdDescription", StringUtil.checkObjectNull(rgd.getDisplayIdDescription()));
                        value.put("messageId", StringUtil.checkObjectNull(rgd.getMessageNumber()));
                        value.put("postedOn", StringUtil.checkObjectNull(rgd.getPostedDate()));
                        value.put("postedTime", StringUtil.checkObjectNull(rgd.getPostedDate()));
                        value.put("productHierarchy", StringUtil.checkObjectNull(rgd.getProductHierarchyCode()));
                        value.put("read", rgd.isRead());
                        value.put("status", StringUtil.checkObjectNull(rgd.getStatusMessage()));
                        jsonArray.put(value);
                    }
                });
                jsonObject.put("data", jsonArray);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }

            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getMessageById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // /**
    // * Method to get the list for the filter
    // *
    // * @author Jayaprakash Ponnusamy
    // * @param triggerId // ATM id or FTM id
    // * @return message
    // *
    // */
    // public ResponseEntity<String> filterMessage(String triggerId, String
    // searchText) {
    // JSONObject jsonObject = new JSONObject();
    // JSONArray jsonArray = new JSONArray();
    // try {
    // List<ReceiverGroupDataEntity> dataEntities =
    // receiverGroupDataRepository.findByTriggerId(triggerId);
    // if (StringUtil.isListNotNullOrEmpty(dataEntities)) {
    // dataEntities.forEach(rgd -> {
    // boolean isContain = false;
    // JSONObject value = new JSONObject();
    // if
    // (StringUtil.checkObjectNull(rgd.getAldiCommodityGroup()).contains(searchText)
    // || StringUtil.checkObjectNull(rgd.getAssignedTo()).contains(searchText)
    // || new Boolean(rgd.isClaimed()).toString().contains(searchText)
    // ||
    // StringUtil.checkObjectNull(rgd.getDisplayIdDescription()).contains(searchText)
    // || StringUtil.checkObjectNull(rgd.getMessageNumber()).contains(searchText)
    // || StringUtil.checkObjectNull(rgd.getPostedDate()).contains(searchText)
    // ||
    // StringUtil.checkObjectNull(rgd.getProductHierarchyCode()).contains(searchText)
    // || new Boolean(rgd.isRead()).toString().contains(searchText)
    // || StringUtil.checkObjectNull(rgd.getStatusMessage()).contains(searchText)) {
    // isContain = true;
    // }
    // if (isContain) {
    // value.put("aldiCommodityGroup",
    // StringUtil.checkObjectNull(rgd.getAldiCommodityGroup()));
    // value.put("assignedTo", StringUtil.checkObjectNull(rgd.getAssignedTo()));
    // value.put("claimed", rgd.isClaimed());
    // value.put("displayIdDescription",
    // StringUtil.checkObjectNull(rgd.getDisplayIdDescription()));
    // value.put("messageId", StringUtil.checkObjectNull(rgd.getMessageNumber()));
    // value.put("postedOn", StringUtil.checkObjectNull(rgd.getPostedDate()));
    // value.put("postedTime", StringUtil.checkObjectNull(rgd.getPostedDate()));
    // value.put("productHierarchy",
    // StringUtil.checkObjectNull(rgd.getProductHierarchyCode()));
    // value.put("read", rgd.isRead());
    // value.put("status", StringUtil.checkObjectNull(rgd.getStatusMessage()));
    // jsonArray.put(value);
    // }
    // });
    // jsonObject.put("data", jsonArray);
    // jsonObject.put("message", MessageConstants.SUCCESS);
    // jsonObject.put("status", MessageConstants.SUCCESS_CODE);
    // } else {
    // jsonObject =
    // CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
    // MessageConstants.FAILED_CODE);
    // }

    // return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    // } catch (Exception e) {
    // jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service
    // - getMessageById");
    // return new ResponseEntity<>(jsonObject.toString(),
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    /**
     * Method to get Messages details by message id.
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return message
     * 
     */

    public ResponseEntity<String> getMessageDetailsById(String messageId) {
        JSONObject jsonObject = new JSONObject();
        try {
            ReceiverGroupDataEntity messageEntity = receiverGroupDataRepository.isdataByMessageId(messageId);
            if (messageEntity != null) {
                jsonObject = rgwHelper.populateMessageData(messageEntity);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.MESSAGE_ID_NOT_EXSITS,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getMessageDetailsById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to udpate status in RGW
     * 
     * @author Pradeep Ravichandran
     * @param helpEntity
     * @return
     */
    public ResponseEntity<String> updateStatus(ReceiverGroupDataEntity rgdEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int updateStatus = 0;
        try {
            if (StringUtil.isListNotNullOrEmpty(rgdEntity.getMessageIds())) {
                for (String messageId : rgdEntity.getMessageIds()) {
                    System.out.println(messageId);
                    if (StringUtil.isEqual(rgdEntity.getAction(), MessageConstants.CONST_STATUS_ASSIGN)) {
                        updateStatus = receiverGroupDataRepository.updateStatusAssign(rgdEntity.getAssignedTo(),
                                messageId, rgdEntity.getCreatedBy(), new Date());
                    } else if (StringUtil.isEqual(rgdEntity.getAction(), MessageConstants.CONST_STATUS_CLAIM)) {
                        updateStatus = receiverGroupDataRepository.updateStatusClaim(messageId,
                                rgdEntity.getCreatedBy(), new Date());
                    } else if (StringUtil.isEqual(rgdEntity.getAction(), MessageConstants.CONST_STATUS_RESET)) {
                        updateStatus = receiverGroupDataRepository.updateStatusReset(messageId,
                                rgdEntity.getCreatedBy(), new Date());
                    } else if (StringUtil.isEqual(rgdEntity.getAction(), MessageConstants.CONST_STATUS_POSTPONED)) {
                        updateStatus = receiverGroupDataRepository.updateStatusPostponed(messageId,
                                rgdEntity.getCreatedBy(), rgdEntity.getPostponeDate(), rgdEntity.getPostponeTime(),
                                new Date());
                    } else if (StringUtil.isEqual(rgdEntity.getAction(), MessageConstants.CONST_STATUS_START)) {
                        updateStatus = receiverGroupDataRepository.updateStartAndComplete(
                                MessageConstants.CONST_IN_PROGRESS, messageId, rgdEntity.getCreatedBy(), new Date());
                    } else if (StringUtil.isEqual(rgdEntity.getAction(), MessageConstants.CONST_STATUS_COMPLETED)) {
                        updateStatus = receiverGroupDataRepository.updateStartAndComplete(
                                MessageConstants.CONST_STATUS_COMPLETED, messageId, rgdEntity.getCreatedBy(),
                                new Date());
                    }
                }
            }
            if (updateStatus > 0) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("response", MessageConstants.EDIT_OBJECT);
                array.put(jsonObject2);
                jsonObject.put("data", array);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - updateStatus");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get All Assign user to assign the messages
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getAllAssignUsers() {
        JSONObject jsonObject = new JSONObject();
        try {
            List<String> userList = receiverGroupDataRepository.userList();
            if (StringUtil.isListNotNullOrEmpty(userList)) {
                jsonObject.put("data", userList);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getAllAssignUsers");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get All Display ID List
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getAllDisplayIdList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<String> displayIdList = receiverGroupDataRepository.getAllDisplayIdList();
            if (StringUtil.isListNotNullOrEmpty(displayIdList)) {
                displayIdList.forEach(disId -> {
                    JSONObject dataObject = new JSONObject();
                    dataObject.put("name", StringUtil.checkObjectNull(disId));
                    array.put(dataObject);
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getAllDisplayIdList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to Save Notes in RGW
     * 
     * @author Pradeep Ravichandran
     * @param noteListEntity
     * @return
     */
    public ResponseEntity<String> saveNotes(NoteListEntity noteListEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            noteListEntity.setType(MessageConstants.NOTE_TYPE_RGW);
            NoteListEntity savedNoteList = noteListRepository.save(noteListEntity);
            if (savedNoteList != null) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("resultString", MessageConstants.SAVE_OBJECT);
                jsonObject2.put("id", savedNoteList.getId());
                jsonObject2.put("notes", StringUtil.checkObjectNull(savedNoteList.getNote()));
                jsonObject2.put("MessageId", StringUtil.checkObjectNull(savedNoteList.getMessageId()));
                array.put(jsonObject2);
                jsonObject.put("data", array);
                jsonObject.put("message", MessageConstants.SAVE_OBJECT);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                        MessageConstants.ERROR_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - saveNotes");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to delete Notes in RGW
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    public ResponseEntity<String> deleteNotes(int notesId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (notesId > 0) {
                noteListRepository.deleteById(Integer.toUnsignedLong(notesId));
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("response", MessageConstants.DELETE_SUCCESS);
                array.put(jsonObject2);
                jsonObject.put("data", array);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL, MessageConstants.ERROR_CODE);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - deleteNotes");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to get Notes in RGW for Message id
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    public ResponseEntity<String> getNotesById(String messageId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<NoteListEntity> noteLists = noteListRepository.findByMsgId(messageId, MessageConstants.NOTE_TYPE_RGW);
            if (StringUtil.isListNotNullOrEmpty(noteLists)) {
                for (NoteListEntity disId : noteLists) {
                    JSONObject dataObject = new JSONObject();
                    dataObject.put("id", disId.getId());
                    dataObject.put("note", StringUtil.checkObjectNull(disId.getNote()));
                    dataObject.put("createdBy", StringUtil.checkObjectNull(disId.getCreatedBy()));
                    dataObject.put("createdOn", DateUtil.getEpochTimeFromDate(disId.getCreatedOn()));
                    dataObject.put("messageId", StringUtil.checkObjectNull(disId.getMessageId()));
                    array.put(dataObject);
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getNotesById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to edit messages in RGW
     * 
     * @author Pradeep Ravichandran
     * @param rgdEntity
     * @return
     */
    public ResponseEntity<String> editMessages(MultipartFile file, String rgdEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            ObjectMapper mapper = new ObjectMapper();
            ReceiverGroupDataEntity parseData = mapper.readValue(rgdEntity, ReceiverGroupDataEntity.class);
            ReceiverGroupDataEntity messageEntity = receiverGroupDataRepository
                    .isdataByMessageId(parseData.getMessageNumber());
            if (messageEntity != null) {
                boolean isContainAttachment = false;
                String fileAttachment = "";
                String fileName = "";
                if (file != null && !file.isEmpty()) {
                    file.getInputStream();
                    byte[] byteArr = file.getBytes();
                    fileAttachment = Base64.getEncoder().encodeToString(byteArr);
                    isContainAttachment = true;
                    fileName = file.getOriginalFilename();
                    System.out.println(file.getOriginalFilename());
                }
                int updateCount = receiverGroupDataRepository.editMessage(parseData.getMessageNumber(),
                        parseData.getCreatedBy(), parseData.getPostponeDate(), parseData.getPostponeTime(),
                        parseData.getAssignedTo(), fileAttachment, isContainAttachment,fileName);
                if (updateCount > 0) {
                    ReceiverGroupDataEntity updatedEntity = receiverGroupDataRepository
                            .isdataByMessageId(parseData.getMessageNumber());
                    jsonObject = rgwHelper.populateMessageData(updatedEntity);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_UPDATING_DATA,
                            MessageConstants.ERROR_CODE);
                    return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.MESSAGE_ID_NOT_EXSITS,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - editMessages");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to get all filter datas
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getFilterDatas() {
        JSONObject jsonObject = new JSONObject();
        JSONArray articlehierarchyList = new JSONArray();
        JSONArray orderAreaList = new JSONArray();
        JSONArray CommodityGroupList = new JSONArray();
        JSONArray productHierarchyList = new JSONArray();
        try {
            JSONObject filterObject = new JSONObject();

            List<ArticleHierarchyEntity> articleHierarchyEntities = articleHierarchyRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(articleHierarchyEntities)) {
                articleHierarchyEntities.forEach(ahe -> {
                    if (StringUtil.isNotNullOrEmpty(ahe.getName())) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", ahe.getId());
                        jsonObject1.put("name", StringUtil.checkObjectNull(ahe.getName()));
                        articlehierarchyList.put(jsonObject1);
                    }
                });
            }
            filterObject.put("articleTypes", articlehierarchyList);
            List<OrderAreaListEntity> areaListEntities = orderAreaListRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(areaListEntities)) {
                areaListEntities.forEach(ale -> {
                    if (StringUtil.isNotNullOrEmpty(ale.getOrderAreaName())) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", ale.getOrderAreaId());
                        jsonObject1.put("name", ale.getOrderAreaName());
                        orderAreaList.put(jsonObject1);
                    }
                });
            }
            filterObject.put("orderAreaList", orderAreaList);
            List<CommodityGroupEntity> commodityGroupEntities = commodityGroupRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(commodityGroupEntities)) {
                commodityGroupEntities.forEach(cge -> {
                    if (StringUtil.isNotNullOrEmpty(cge.getCommodityGroupName())) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", cge.getCgId());
                        jsonObject1.put("name", cge.getCommodityGroupName());
                        CommodityGroupList.put(jsonObject1);
                    }
                });
            }
            filterObject.put("commodityGroups", CommodityGroupList);
            List<ProductHierarchyEntity> productHierarchyEntities = productHierarchyRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(productHierarchyEntities)) {
                productHierarchyEntities.forEach(phe -> {
                    if (StringUtil.isNotNullOrEmpty(phe.getProductHierarchyName())) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", phe.getProductHierarchyId());
                        jsonObject1.put("name", phe.getProductHierarchyName());
                        productHierarchyList.put(jsonObject1);
                    }
                });
            }
            filterObject.put("productHierarchies", productHierarchyList);
            jsonObject.put("data", filterObject);
            jsonObject.put("message", MessageConstants.SUCCESS);
            jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getFilterDatas");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getRGWFilteredData(RGWFilterModel model) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            formateRequest(model);
            List<ReceiverGroupDataEntity> dataEntities = receiverGroupDataRepository.findAllByFilters(
                    model.getTriggerId(), model.getDisplayId(), model.getAssignedTo(), model.getProductHierarchy(),
                    model.getCommodityGroup(), model.getArticleType(), model.getArticleStatus(),
                    model.getStockPlannerList(), model.getOrderAreaList(), model.getStatus(),
                    model.getListingDateFrom(), model.getListingDateTo(), model.getPostDateFrom(),
                    model.getPostDateTo());

            if (StringUtil.isListNotNullOrEmpty(dataEntities)) {
                dataEntities.forEach(rgd -> {
                    JSONObject value = new JSONObject();
                    value.put("aldiCommodityGroup", StringUtil.checkObjectNull(rgd.getAldiCommodityGroup()));
                    value.put("assignedTo", StringUtil.checkObjectNull(rgd.getAssignedTo()));
                    value.put("claimed", rgd.isClaimed());
                    value.put("displayIdDescription", StringUtil.checkObjectNull(rgd.getDisplayIdDescription()));
                    value.put("messageId", StringUtil.checkObjectNull(rgd.getMessageNumber()));
                    value.put("postedOn", StringUtil.checkObjectNull(rgd.getPostedDate()));
                    value.put("postedTime", StringUtil.checkObjectNull(rgd.getPostedDate()));
                    value.put("productHierarchy", StringUtil.checkObjectNull(rgd.getProductHierarchyCode()));
                    value.put("read", rgd.isRead());
                    value.put("status", StringUtil.checkObjectNull(rgd.getStatusMessage()));
                    jsonArray.put(value);
                });
                jsonObject.put("data", jsonArray);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getMessageById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void formateRequest(RGWFilterModel model) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (model.getPostDateFrom() != null && model.getPostDateTo() != null) {
                Date postDateFrom = sdf.parse(model.getPostDateFrom());
                Date postDateTo = sdf.parse(model.getPostDateTo());
                long postDateFromMillis = postDateFrom.getTime();
                long postDateToMillis = postDateTo.getTime();
                model.setPostDateFrom(String.valueOf(postDateFromMillis));
                model.setPostDateTo(String.valueOf(postDateToMillis));
            }
            if (model.getListingDateFrom() != null && model.getListingDateTo() != null) {
                Date listingDateFrom = sdf.parse(model.getListingDateFrom());
                Date listingDateTo = sdf.parse(model.getListingDateTo());
                long listingDateFromMillis = listingDateFrom.getTime();
                long listingDateToMillis = listingDateTo.getTime();
                model.setListingDateFrom(String.valueOf(listingDateFromMillis));
                model.setListingDateTo(String.valueOf(listingDateToMillis));
            }
            if (model.getDcList() != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("DC", model.getDcList());
                model.setDc(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
