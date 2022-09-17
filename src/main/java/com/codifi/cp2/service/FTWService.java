package com.codifi.cp2.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.codifi.cp2.entity.FreeTextMaintenanceEntity;
import com.codifi.cp2.entity.FreeTextMessageEntity;
import com.codifi.cp2.entity.NoteListEntity;
import com.codifi.cp2.entity.ReceiverGroupUserEntity;
import com.codifi.cp2.helper.FTWHelper;
import com.codifi.cp2.model.request.DistributionListModel;
import com.codifi.cp2.model.request.FTWDistributionListModel;
import com.codifi.cp2.model.request.FTWReceiverGroupModel;
import com.codifi.cp2.model.request.FTWReceiverJsonModel;
import com.codifi.cp2.model.response.FTWAdditionalFieldsModel;
import com.codifi.cp2.model.response.FTWAttachmentModel;
import com.codifi.cp2.model.response.FTWTemplateDataModel;
import com.codifi.cp2.model.response.GetAllTriggerEvent;
import com.codifi.cp2.model.response.TriggerEventResponseModel;
import com.codifi.cp2.repository.FreeTextMaintenanceRepository;
import com.codifi.cp2.repository.FreeTextMessageRepository;
import com.codifi.cp2.repository.NoteListRepository;
import com.codifi.cp2.repository.ReceiverGroupUserRepository;
import com.codifi.cp2.repository.TemplateFieldListRepository;
import com.codifi.cp2.util.CommonMethods;
import com.codifi.cp2.util.DateUtil;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FTWService {
    @Autowired
    FTWHelper ftwHelper;
    @Autowired
    FreeTextMessageRepository freeTextMessageRepository;
    @Autowired
    FreeTextMaintenanceRepository freeTextMaintenanceRepository;
    @Autowired
    TemplateFieldListRepository templateFieldListRepository;
    @Autowired
    NoteListRepository noteListRepository;
    @Autowired
    ReceiverGroupUserRepository groupUserRepository;

    /**
     * Method to get All Status list
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getAllStatusList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<String> statusList = freeTextMessageRepository.getDistinctStatusList();
            if (StringUtil.isListNotNullOrEmpty(statusList)) {
                statusList.forEach(sl -> {
                    JSONObject dataObject = new JSONObject();
                    dataObject.put("name", StringUtil.checkObjectNull(sl));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - getAllStatusList");
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
            List<String> displayIdList = freeTextMessageRepository.getAllDisplayIdList();
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - getAllDisplayIdList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get All Created By List
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getAllCreatedByList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<String> displayIdList = freeTextMessageRepository.getAllCreatedByList();
            if (StringUtil.isListNotNullOrEmpty(displayIdList)) {
                displayIdList.forEach(createdBy -> {
                    JSONObject dataObject = new JSONObject();
                    dataObject.put("name", StringUtil.checkObjectNull(createdBy));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - getAllCreatedByList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get All Approved By List
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getAllApprovedByList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<String> displayIdList = freeTextMessageRepository.getAllApprovedByList();
            if (StringUtil.isListNotNullOrEmpty(displayIdList)) {
                displayIdList.forEach(approveBy -> {
                    JSONObject dataObject = new JSONObject();
                    dataObject.put("name", StringUtil.checkObjectNull(approveBy));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - getAllApprovedByList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get All FTW Trigger Events
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getFTWTriggerList() {
        JSONObject jsonObject = new JSONObject();
        try {
            List<GetAllTriggerEvent> ftmRecords = freeTextMessageRepository.findAllTriggerEvents();
            List<TriggerEventResponseModel> resultModel = new ArrayList<>();
            if (StringUtil.isListNotNullOrEmpty(ftmRecords)) {
                ftmRecords.forEach(ftm -> {
                    int ftmTotalMsgcount = freeTextMessageRepository.findFTMTotalMessageCount(ftm.getTriggerId());
                    TriggerEventResponseModel model = new TriggerEventResponseModel();
                    model.setTriggerId(StringUtil.checkObjectNull(ftm.getTriggerId()));
                    model.setTriggerName(StringUtil.checkObjectNull(ftm.getTriggerName()));
                    model.setFilteredMessageCount(ftm.getfilteredMessageCount());
                    model.setTotalMessageCount(ftmTotalMsgcount);
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - getFTWTriggerList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Messages by trigger id.
     * 
     * @author Pradeep Ravichandran
     * @param triggerId // FTM id
     * @return message
     * 
     */
    public ResponseEntity<String> getMessageById(String triggerId, String senderStatus) {
        JSONObject jsonObject = new JSONObject();
        try {
            FTWTemplateDataModel fieldListEntity = templateFieldListRepository.findTemplateType(triggerId);
            if (fieldListEntity != null && StringUtil.isNotNullOrEmpty(fieldListEntity.getFieldName())
                    && StringUtil.isNotNullOrEmpty(fieldListEntity.getTemplateName())) {
                JSONArray dataArray = ftwHelper.populateFTWData(fieldListEntity.getFieldName(), triggerId,
                        senderStatus);
                List<FTWAdditionalFieldsModel> additionalFieldsModels = ftwHelper
                        .constructAdditionalData(fieldListEntity.getFieldName());
                jsonObject.put("additionalFields", additionalFieldsModels);
                jsonObject.put("data", dataArray);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.DATA_NOT_FOUND,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - getMessageById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to get Messages by trigger id.
     * 
     * @author Pradeep Ravichandran
     * @param triggerId // FTM id
     * @return message
     * 
     */

    public ResponseEntity<String> searchMessage(String triggerId, String searchText) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            List<FreeTextMessageEntity> triggerdataModels = freeTextMessageRepository.findByTriggerId(triggerId);
            if (StringUtil.isListNotNullOrEmpty(triggerdataModels)) {
                triggerdataModels.forEach(rgd -> {
                    boolean isContain = false;
                    JSONObject value = new JSONObject();
                    if (StringUtil.checkObjectNull(rgd.getMessageNumber()).toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getApprovedBy()).toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(Integer.toString(rgd.getDisplayId())).contains(searchText)
                            || StringUtil.checkObjectNull(Integer.toString(rgd.getSupplierId())).contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getPoNumber()).toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getPoItemNumber()).toLowerCase().contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getOutlineAgreementNumber()).toLowerCase()
                                    .contains(searchText)
                            || StringUtil.checkObjectNull(rgd.getOaItemNumber()).toLowerCase().contains(searchText)) {
                        isContain = true;
                    }
                    if (isContain) {
                        value.put("messageNumber", StringUtil.checkObjectNull(rgd.getMessageNumber()));
                        value.put("approvedBy", StringUtil.checkObjectNull(rgd.getApprovedBy()));
                        value.put("status", StringUtil.checkObjectNull("rgd.getDisplayId()"));
                        value.put("displayId", StringUtil.checkObjectNull(Integer.toString(rgd.getDisplayId())));
                        value.put("supplierId", StringUtil.checkObjectNull(Integer.toString(rgd.getSupplierId())));
                        value.put("poNumber", StringUtil.checkObjectNull(rgd.getPoNumber()));
                        value.put("poItemNumber", StringUtil.checkObjectNull(rgd.getPoItemNumber()));
                        value.put("outlineAgreementNumber",
                                StringUtil.checkObjectNull(rgd.getOutlineAgreementNumber()));
                        value.put("oaItemNumber", StringUtil.checkObjectNull(rgd.getOaItemNumber()));
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

        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " RGW Service - getMessageById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    /**
     * Method to update send for Approvals
     * 
     * @author Pradeep Ravichandran
     * @param ftwEntity
     * @return
     */
    public ResponseEntity<String> sendForApproval(FreeTextMessageEntity ftwEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int updateStatus = 0;
        long epoch = Instant.now().toEpochMilli();
        try {
            if (StringUtil.isListNotNullOrEmpty(ftwEntity.getMessageIds())) {
                for (String messageId : ftwEntity.getMessageIds()) {
                    if (StringUtil.isEqual(ftwEntity.getAction(), MessageConstants.CONST_STATUS_AWAITING_APPROVAL)) {
                        updateStatus = freeTextMessageRepository.updateSendApprove(
                                MessageConstants.CONST_STATUS_AWAITING_APPROVAL, ftwEntity.getCreatedBy(), new Date(),
                                messageId);
                    } else if (StringUtil.isEqual(ftwEntity.getAction(), MessageConstants.CONST_STATUS_APPROVED)) {
                        updateStatus = freeTextMessageRepository.updateApprove(MessageConstants.CONST_STATUS_APPROVED,
                                ftwEntity.getCreatedBy(), ftwEntity.getApprovedBy(), String.valueOf(epoch), new Date(),
                                messageId);
                    } else if (StringUtil.isEqual(ftwEntity.getAction(), MessageConstants.CONST_STATUS_REJECTED)) {
                        updateStatus = freeTextMessageRepository.updateApprove(MessageConstants.CONST_STATUS_REJECTED,
                                ftwEntity.getCreatedBy(), ftwEntity.getApprovedBy(), String.valueOf(epoch), new Date(),
                                messageId);
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - sendForApproval");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to delete Messages
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    public ResponseEntity<String> deleteMessage(String messageId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (StringUtil.isNotNullOrEmpty(messageId)) {
                int count = freeTextMessageRepository.deleteMessage(messageId);
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
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ID_NULL,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - deleteMessage");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to clone Message by message Id
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    public ResponseEntity<String> cloneMessage(String messageId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            FreeTextMessageEntity freeTextMessageEntity = freeTextMessageRepository.getMsgByMsgId(messageId);
            if (freeTextMessageEntity != null) {
                FreeTextMessageEntity newMsgEntity = new FreeTextMessageEntity();
                BeanUtils.copyProperties(newMsgEntity, freeTextMessageEntity);
                newMsgEntity.setId(null);
                newMsgEntity
                        .setMessageNumber(ftwHelper.generateMessageNumber(freeTextMessageEntity.getFreeTextEvent()));
                newMsgEntity.setSenderApprovedStatus(MessageConstants.CONST_STATUS_UD);
                newMsgEntity.setApprovedBy(null);
                newMsgEntity.setApprovedOn(null);
                FreeTextMessageEntity newMessage = freeTextMessageRepository.save(newMsgEntity);
                if (newMessage != null) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("messageNumber", StringUtil.checkObjectNull(newMessage.getMessageNumber()));
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
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + messageId,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - cloneMessage");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to View Message by message Id
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    public ResponseEntity<String> viewMessage(String messageId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            FreeTextMessageEntity freeTextMessageEntity = freeTextMessageRepository.getMsgByMsgId(messageId);
            if (freeTextMessageEntity != null) {
                String templateType = freeTextMaintenanceRepository
                        .findTemplateTypeByTriggerId(freeTextMessageEntity.getFreeTextEvent());
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("templateType", StringUtil.checkObjectNull(templateType));
                List<String> templates = StringUtil.splitOnlyNonEmpty(templateType, ",");
                ftwHelper.constructTemplateValues(templates, freeTextMessageEntity, jsonObject2);
                jsonObject2.put("messageNumber", StringUtil.checkObjectNull(freeTextMessageEntity.getMessageNumber()));
                jsonObject2.put("status", StringUtil.checkObjectNull(freeTextMessageEntity.getSenderApprovedStatus()));
                jsonObject2.put("approvedBy", StringUtil.checkObjectNull(freeTextMessageEntity.getApprovedBy()));
                jsonObject2.put("createdBy", StringUtil.checkObjectNull(freeTextMessageEntity.getCreatedBy()));
                jsonObject2.put("postDate", StringUtil.checkObjectNull(freeTextMessageEntity.getPostedDate()));
                if (freeTextMessageEntity.getApprovedOn() != null
                        && !StringUtils.isEmpty(freeTextMessageEntity.getApprovedOn())) {
                    DateFormat simple = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
                    long milliSeconds = Long.parseLong(freeTextMessageEntity.getApprovedOn());
                    Date result = new Date(milliSeconds);
                    jsonObject2.put("approvedOn", simple.format(result));
                } else {
                    jsonObject2.put("approvedOn", "");
                }
                jsonObject2.put("dcS", freeTextMessageEntity.getDc());
                jsonObject2.put("supplierId", StringUtil.checkObjectNull(freeTextMessageEntity.getMessageNumber()));
                jsonObject2.put("memo", StringUtil.checkObjectNull(freeTextMessageEntity.getMemo()));
                jsonObject2.put("attachments", StringUtil.checkObjectNull(freeTextMessageEntity.getAttachments()));
                jsonObject2.put("attachmentName",
                        StringUtil.checkObjectNull(freeTextMessageEntity.getAttachmentName()));
                array.put(jsonObject2);
                jsonObject.put("data", array);
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + messageId,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - viewMessage");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to Save Notes in FTW
     * 
     * @author Pradeep Ravichandran
     * @param noteListEntity
     * @return
     */
    public ResponseEntity<String> saveNotes(NoteListEntity noteListEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            noteListEntity.setType(MessageConstants.NOTE_TYPE_FTW);
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - saveNotes");
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - deleteNotes");
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
            List<NoteListEntity> noteLists = noteListRepository.findByMsgId(messageId, MessageConstants.NOTE_TYPE_FTW);
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - getNotesById");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to edit messages
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    public ResponseEntity<String> editMessage(String messageId, String screen) {
        JSONObject jsonObject = new JSONObject();
        try {
            FreeTextMessageEntity freeTextMessageEntity = freeTextMessageRepository.getMsgByMsgId(messageId);
            if (freeTextMessageEntity != null) {
                jsonObject = populateMsgDetails(freeTextMessageEntity, screen);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + messageId,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - editMessage");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public JSONObject populateMsgDetails(FreeTextMessageEntity freeTextMessageEntity, String screen) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        if (freeTextMessageEntity != null) {
            JSONObject jsonObject2 = new JSONObject();
            FreeTextMaintenanceEntity freeTextMaintainence = freeTextMaintenanceRepository
                    .findByTriggerId(freeTextMessageEntity.getFreeTextEvent());
            if (StringUtil.isEqual(MessageConstants.SCREEN_TRIGGER, screen)) {
                jsonObject2.put("triggerId", StringUtil.checkObjectNull(freeTextMaintainence.getFreeTextTriggerId()));
                jsonObject2.put("triggerNameEng",
                        StringUtil.checkObjectNull(freeTextMaintainence.getTriggerNameEnglish()));
                jsonObject2.put("triggerNameGerman",
                        StringUtil.checkObjectNull(freeTextMaintainence.getTriggerNameGerman()));
            } else if (StringUtil.isEqual(MessageConstants.SCREEN_DETAIL, screen)) {
                List<String> templates = StringUtil.splitOnlyNonEmpty(freeTextMaintainence.getTemplate(), ",");
                ftwHelper.constructTemplateValues(templates, freeTextMessageEntity, jsonObject2);
                jsonObject2.put("templateType", templates);
                jsonObject2.put("postDate", StringUtil.checkObjectNull(freeTextMessageEntity.getPostedDate()));
                jsonObject2.put("dcS",
                        StringUtil.isListNotNullOrEmpty(freeTextMessageEntity.getDc()) ? freeTextMessageEntity.getDc()
                                : "");
                jsonObject2.put("supplierId", freeTextMessageEntity.getSupplierId());
                jsonObject2.put("memo", StringUtil.checkObjectNull(freeTextMessageEntity.getMemo()));
            } else if (StringUtil.isEqual(MessageConstants.SCREEN_DISTRIBUTIONLIST, screen)) {
                jsonObject2.put("receiverStream", freeTextMessageEntity.getReceiverGroupJson().getDistributionList());
                jsonObject2.put("receiverUsers",
                        freeTextMessageEntity.getReceiverGroupJson().getFtwReceiverGroupModels());
                // ftwHelper.constructDistributionList(freeTextMessageEntity, jsonObject2);
                // jsonObject2.put("multipleSelect",
                // StringUtil.isListNotNullOrEmpty(freeTextMessageEntity.getDistributionList())
                // ? freeTextMessageEntity.getDistributionList()
                // : "");
            }
            jsonObject2.put("messageNumber", StringUtil.checkObjectNull(freeTextMessageEntity.getMessageNumber()));
            jsonObject2.put("status", StringUtil.checkObjectNull(freeTextMessageEntity.getSenderApprovedStatus()));
            jsonObject2.put("approvedBy", StringUtil.checkObjectNull(freeTextMessageEntity.getApprovedBy()));
            jsonObject2.put("createdBy", StringUtil.checkObjectNull(freeTextMessageEntity.getCreatedBy()));
            array.put(jsonObject2);
            jsonObject.put("data", array);
            jsonObject.put("message", MessageConstants.SUCCESS);
            jsonObject.put("status", MessageConstants.SUCCESS_CODE);
        }
        return jsonObject;
    }

    /**
     * method to get Trigger Id and Trigger Name
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity<String> getTriggerIdAndName() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<FreeTextMaintenanceEntity> freeTextMaintainence = freeTextMaintenanceRepository.findAllActive();
            if (StringUtil.isListNotNullOrEmpty(freeTextMaintainence)) {
                freeTextMaintainence.forEach(ftw -> {
                    if (StringUtil.isNotEqual(ftw.getStatus(), MessageConstants.CONST_STATUS_UD)) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("id", ftw.getId());
                        jsonObject1.put("triggerId", StringUtil.checkObjectNull(ftw.getFreeTextTriggerId()));
                        jsonObject1.put("triggerNameEng", StringUtil.checkObjectNull(ftw.getTriggerNameEnglish()));
                        jsonObject1.put("triggerNameGerman", StringUtil.checkObjectNull(ftw.getTriggerNameGerman()));
                        array.put(jsonObject1);
                    }
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - getTriggerIdAndName");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to save message Details
     * 
     * @author Pradeep Ravichandran
     * @param freeTextMessageEntity
     * @return
     */
    public ResponseEntity<String> saveEditMessageDetails(MultipartFile file, String ftmEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            ObjectMapper mapper = new ObjectMapper();
            FreeTextMessageEntity freeTextMessageEntity = mapper.readValue(ftmEntity, FreeTextMessageEntity.class);
            if (freeTextMessageEntity != null
                    && StringUtil.isNotNullOrEmpty(freeTextMessageEntity.getMessageNumber())) {
                FreeTextMessageEntity oldEntity = freeTextMessageRepository
                        .getMsgByMsgId(freeTextMessageEntity.getMessageNumber());
                if (oldEntity != null) {
                    FreeTextMaintenanceEntity entity = freeTextMaintenanceRepository
                            .findByTriggerId(oldEntity.getFreeTextEvent());
                    String fileAttachment = "";
                    String fileName = "";
                    if (file != null && !file.isEmpty()) {
                        file.getInputStream();
                        byte[] byteArr = file.getBytes();
                        fileAttachment = Base64.getEncoder().encodeToString(byteArr);
                        fileName = file.getOriginalFilename();
                    }
                    FreeTextMessageEntity updatedEntity = new FreeTextMessageEntity();
                    BeanUtils.copyProperties(updatedEntity, oldEntity);
                    updatedEntity.setDisplayId(freeTextMessageEntity.getDisplayId());
                    updatedEntity.setSupplierId(freeTextMessageEntity.getSupplierId());
                    updatedEntity.setDc(freeTextMessageEntity.getDc());
                    updatedEntity.setOutlineAgreementNumber(freeTextMessageEntity.getOutlineAgreementNumber());
                    updatedEntity.setOaItemNumber(freeTextMessageEntity.getOaItemNumber());
                    updatedEntity.setPoNumber(freeTextMessageEntity.getPoNumber());
                    updatedEntity.setPoItemNumber(freeTextMessageEntity.getPoItemNumber());
                    updatedEntity.setPostedDate(freeTextMessageEntity.getPostedDate());
                    updatedEntity.setMemo(freeTextMessageEntity.getMemo());
                    updatedEntity.setAttachments(fileAttachment);
                    updatedEntity.setAttachmentName(fileName);
                    updatedEntity.setUpdatedBy(freeTextMessageEntity.getCreatedBy());
                    FTWReceiverJsonModel jsonModel = new FTWReceiverJsonModel();
                    if (StringUtil.isListNotNullOrEmpty(entity.getDistributionName())) {
                        jsonModel.setDistributionList(entity.getDistributionName());
                        List<FTWReceiverGroupModel> userList = new ArrayList<>();
                        for (DistributionListModel dn : entity.getDistributionName()) {
                            ReceiverGroupUserEntity groupUserEntity = groupUserRepository
                                    .findAllByTriggerID(dn.getId());
                            if (groupUserEntity != null
                                    && StringUtil.isListNotNullOrEmpty(groupUserEntity.getUserJson())) {
                                FTWReceiverGroupModel userObject = new FTWReceiverGroupModel();
                                groupUserEntity.getUserJson().forEach(user -> {
                                    userObject.setReceiverGroup(
                                            groupUserEntity.getReceiverId() + "-" + groupUserEntity.getReceiverLong());
                                    userObject.setSapLoginId(user.getSapLoginId());
                                    userObject.setFirstName(user.getFirstName());
                                    userObject.setLastName(user.getLastName());
                                    userObject.setEmail(user.getEmail());
                                    userObject.setCreatedOn(user.getCreatedOn());
                                    userList.add(userObject);
                                });
                            }
                        }
                        jsonModel.setFtwReceiverGroupModels(userList);
                    }
                    updatedEntity.setReceiverGroupJson(jsonModel);
                    updatedEntity.setUpdatedOn(new Date());
                    FreeTextMessageEntity newUpdatedEntity = freeTextMessageRepository.save(updatedEntity);
                    if (newUpdatedEntity != null) {
                        FreeTextMessageEntity newEntity = freeTextMessageRepository
                                .getMsgByMsgId(freeTextMessageEntity.getMessageNumber());
                        if (newEntity != null) {
                            jsonObject = populateMsgDetails(newEntity, MessageConstants.SCREEN_DETAIL);
                        } else {
                            jsonObject = CommonMethods.failedResponseMessage(
                                    MessageConstants.RECORD_NOT_FOUND + freeTextMessageEntity.getMessageNumber(),
                                    MessageConstants.FAILED_CODE);
                        }
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_UPDATE_RECORD,
                                MessageConstants.FAILED_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(
                            MessageConstants.RECORD_NOT_FOUND + freeTextMessageEntity.getMessageNumber(),
                            MessageConstants.FAILED_CODE);
                }
            } else {
                if (freeTextMessageEntity == null) {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.EMPTY_DATA,
                            MessageConstants.FAILED_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.MESSAGE_ID_NULL,
                            MessageConstants.FAILED_CODE);
                }
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - saveEditMessageDetails");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to save Trigger
     * 
     * @author Pradeep Ravichandran
     * @param freeTextMessageEntity
     * @return
     */
    public ResponseEntity<String> saveTrigger(FreeTextMessageEntity ftmEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (ftmEntity != null && StringUtil.isNotNullOrEmpty(ftmEntity.getFreeTextEvent())) {
                FreeTextMaintenanceEntity entity = freeTextMaintenanceRepository
                        .findByTriggerId(ftmEntity.getFreeTextEvent());
                if (entity != null) {
                    ftmEntity.setMessageNumber(ftwHelper.generateMessageNumber(ftmEntity.getFreeTextEvent()));
                    ftmEntity.setSenderApprovedStatus(MessageConstants.CONST_STATUS_UD);

                    // ftmEntity.set
                    FreeTextMessageEntity savedEntity = freeTextMessageRepository.save(ftmEntity);
                    if (savedEntity != null) {
                        FreeTextMessageEntity newEntity = freeTextMessageRepository
                                .getMsgByMsgId(savedEntity.getMessageNumber());
                        if (newEntity != null) {
                            jsonObject = populateMsgDetails(newEntity, MessageConstants.SCREEN_TRIGGER);
                        } else {
                            jsonObject = CommonMethods.failedResponseMessage(
                                    MessageConstants.RECORD_NOT_FOUND + savedEntity.getMessageNumber(),
                                    MessageConstants.FAILED_CODE);
                        }
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                                MessageConstants.FAILED_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(
                            MessageConstants.RECORD_NOT_FOUND + ftmEntity.getFreeTextEvent(),
                            MessageConstants.FAILED_CODE);
                }
            } else {
                if (ftmEntity == null) {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.EMPTY_DATA,
                            MessageConstants.FAILED_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.TRIGGER_EVENT_NULL,
                            MessageConstants.FAILED_CODE);
                }
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - saveTrigger");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // /**
    // * method to get Distribution List in RGW for trigger id
    // *
    // * @author Pradeep Ravichandran
    // * @param triggerID
    // * @return
    // */
    // public ResponseEntity<String> getDistributionList(String triggerID) {
    // JSONObject jsonObject = new JSONObject();
    // JSONArray jsonArray = new JSONArray();
    // JSONArray userArray = new JSONArray();
    // List<String> ids = new ArrayList<>();
    // try {
    // FreeTextMaintenanceEntity freeTextMaintainence =
    // freeTextMaintenanceRepository.findByTriggerId(triggerID);
    // if (freeTextMaintainence != null
    // &&
    // StringUtil.isListNotNullOrEmpty(freeTextMaintainence.getDistributionName()))
    // {
    // freeTextMaintainence.getDistributionName().forEach(dn -> {
    // if (StringUtil.isNotNullOrEmpty(dn.getId()) &&
    // StringUtil.isNotNullOrEmpty(dn.getName())
    // && !ids.contains(dn.getId())) {
    // JSONObject jsonObject2 = new JSONObject();
    // jsonObject2.put("id", StringUtil.checkObjectNull(dn.getId()));
    // jsonObject2.put("name", StringUtil.checkObjectNull(dn.getName()));
    // ids.add(dn.getId());
    // jsonArray.put(jsonObject2);
    // ReceiverGroupUserEntity groupUserEntity =
    // groupUserRepository.findAllByTriggerID(dn.getId());
    // if (groupUserEntity != null &&
    // StringUtil.isListNotNullOrEmpty(groupUserEntity.getUserJson())) {
    // JSONObject userObject = new JSONObject();
    // groupUserEntity.getUserJson().forEach(user -> {
    // userObject.put("ReceiverGroup",
    // groupUserEntity.getReceiverId() + "-" + groupUserEntity.getReceiverLong());
    // userObject.put("EmPowerId", user.getSapLoginId());
    // userObject.put("firstName", user.getFirstName());
    // userObject.put("lastName", user.getLastName());
    // userObject.put("EmailId", user.getEmail());
    // userArray.put(userObject);
    // });
    // }
    // }
    // });
    // jsonObject.put("data", jsonArray);
    // jsonObject.put("userData", userArray);
    // jsonObject.put("message", MessageConstants.SUCCESS);
    // jsonObject.put("status", MessageConstants.SUCCESS_CODE);
    // } else {
    // jsonObject =
    // CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND +
    // triggerID,
    // MessageConstants.FAILED_CODE);
    // }
    // return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    // } catch (Exception e) {
    // jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service
    // - getDistributionList");
    // return new ResponseEntity<>(jsonObject.toString(),
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    /**
     * method to save Distribution List
     * 
     * @author Pradeep Ravichandran
     * @param freeTextMessageEntity
     * @return
     */
    public ResponseEntity<String> saveDistributionList(FreeTextMessageEntity ftmEntity) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (ftmEntity != null && StringUtil.isNotNullOrEmpty(ftmEntity.getMessageNumber())) {
                FreeTextMessageEntity oldEntity = freeTextMessageRepository.getMsgByMsgId(ftmEntity.getMessageNumber());
                if (oldEntity != null) {
                    FreeTextMessageEntity updatedEntity = new FreeTextMessageEntity();
                    BeanUtils.copyProperties(updatedEntity, oldEntity);
                    FTWReceiverJsonModel jsonModel = new FTWReceiverJsonModel();
                    jsonModel.setDistributionList(ftmEntity.getRgmData());
                    jsonModel.setFtwReceiverGroupModels(ftmEntity.getRgmUserJson());
                    updatedEntity.setReceiverGroupJson(jsonModel);
                    List<FTWDistributionListModel> distributionListModels = new ArrayList<>();
                    if (StringUtil.isListNotNullOrEmpty(ftmEntity.getRgmData())) {
                        for (DistributionListModel distributionListModel : ftmEntity.getRgmData()) {
                            FTWDistributionListModel model = new FTWDistributionListModel();
                            model.setId(distributionListModel.getId());
                            distributionListModels.add(model);
                        }
                    }
                    updatedEntity.setDistributionList(distributionListModels);
                    updatedEntity.setUpdatedBy(ftmEntity.getCreatedBy());
                    updatedEntity.setUpdatedOn(new Date());
                    updatedEntity.setSenderApprovedStatus(MessageConstants.CONST_STATUS_RFA);
                    FreeTextMessageEntity newUpdatedEntity = freeTextMessageRepository.save(updatedEntity);
                    if (newUpdatedEntity != null) {
                        FreeTextMessageEntity newEntity = freeTextMessageRepository
                                .getMsgByMsgId(ftmEntity.getMessageNumber());
                        if (newEntity != null) {
                            jsonObject = populateMsgDetails(newEntity, MessageConstants.SCREEN_DISTRIBUTIONLIST);
                        } else {
                            jsonObject = CommonMethods.failedResponseMessage(
                                    MessageConstants.RECORD_NOT_FOUND + ftmEntity.getMessageNumber(),
                                    MessageConstants.FAILED_CODE);
                        }
                    } else {
                        jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_UPDATE_RECORD,
                                MessageConstants.FAILED_CODE);
                    }
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(
                            MessageConstants.RECORD_NOT_FOUND + ftmEntity.getMessageNumber(),
                            MessageConstants.FAILED_CODE);
                }
            } else {
                if (ftmEntity == null) {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.EMPTY_DATA,
                            MessageConstants.FAILED_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.MESSAGE_ID_NULL,
                            MessageConstants.FAILED_CODE);
                }
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - saveDistributionList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to get attachment by message ID
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    public ResponseEntity<String> getAttachment(String messageId) {
        JSONObject jsonObject = new JSONObject();
        try {
            FTWAttachmentModel attachment = freeTextMessageRepository.getAttachment(messageId);
            if (attachment != null) {
                jsonObject.put("data", StringUtil.checkObjectNull(attachment.getAttachments()));
                jsonObject.put("fileName", StringUtil.checkObjectNull(attachment.getAttachmentName()));
                jsonObject.put("message", MessageConstants.SUCCESS);
                jsonObject.put("status", MessageConstants.SUCCESS_CODE);
            } else {
                jsonObject = CommonMethods.failedResponseMessage(MessageConstants.RECORD_NOT_FOUND + messageId,
                        MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " FTW Service - editMessage");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}