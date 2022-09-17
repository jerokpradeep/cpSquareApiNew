package com.codifi.cp2.service;

import java.util.List;
import java.util.UUID;

import com.codifi.cp2.entity.UserInformationEntity;
import com.codifi.cp2.helper.UserDetailsHelper;
import com.codifi.cp2.repository.ReceiverGroupUserRepository;
import com.codifi.cp2.repository.UserInformationRepository;
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
public class UserDetailService {
    @Autowired
    UserInformationRepository userInformationRepository;
    @Autowired
    UserDetailsHelper detailsHelper;
    @Autowired
    ReceiverGroupUserRepository groupUserRepository;

    /**
     * method to login
     *
     * @author Pradeep Ravichandran
     * @param userDetailsEntity
     * @return
     */
    public ResponseEntity<String> login(UserInformationEntity userDetailsEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            if (StringUtil.isNotNullOrEmpty(userDetailsEntity.getUserId())) {
                // String encryptPassword =
                // CommonMethods.PasswordEncryption(userDetailsEntity.getPassword());
                UserInformationEntity entity = userInformationRepository.findByUserId(userDetailsEntity.getUserId());
                if (entity != null) {
                    List<String> receiverList = groupUserRepository.findReceiverGroupList(entity.getUserId());
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("userId", entity.getUserId());
                    jsonObject2.put("email", entity.getEmailAddress());
                    jsonObject2.put("firstName", entity.getFirstName());
                    jsonObject2.put("lastName", entity.getLastName());
                    array.put(jsonObject2);
                    jsonObject.put("data", array);
                    jsonObject.put("receiverList", receiverList);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject.put("error", UUID.randomUUID().toString());
                    jsonObject.put("resString", MessageConstants.WRONG_USER);
                    jsonObject.put("data", "[]");
                    jsonObject.put("message", MessageConstants.FAILED);
                    jsonObject.put("status", MessageConstants.FAILED_CODE);
                }
            } else {
                if (StringUtil.isNotNullOrEmpty(userDetailsEntity.getUserId())) {
                    jsonObject.put("resString", MessageConstants.USER_NAME_NULL);
                }
                jsonObject.put("data", "[]");
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " User Details Service - login");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method To get All User List
     * 
     * @author Pradeep Ravichandran
     * @return List of User Details
     */

    public ResponseEntity<String> getAllUserList() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<UserInformationEntity> userInformationEntities = userInformationRepository.findAll();
            if (StringUtil.isListNotNullOrEmpty(userInformationEntities)) {
                userInformationEntities.forEach(uie -> {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("empowerId", StringUtil.checkObjectNull(uie.getUserId()));
                    jsonObject2.put("FirstName", StringUtil.checkObjectNull(uie.getFirstName()));
                    jsonObject2.put("lastName", StringUtil.checkObjectNull(uie.getLastName()));
                    jsonObject2.put("email", StringUtil.checkObjectNull(uie.getEmailAddress()));
                    jsonObject2.put("language", StringUtil.checkObjectNull(uie.getLanguageId()));
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
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " User Details Service - getAllUserList");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to Save user Details
     * 
     * @author Sandeep Govindaraj
     * @param userDetailsEntity
     * @return
     */
    public ResponseEntity<String> saveUserDetails(UserInformationEntity userDetailsEntity) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            List<String> validateUser = detailsHelper.validateUserDetails(userDetailsEntity);
            if (StringUtil.isListNullOrEmpty(validateUser)) {
                if (StringUtil.isNotNullOrEmpty(userDetailsEntity.getPassword())) {
                    String password = userDetailsEntity.getPassword();
                    userDetailsEntity.setPassword(CommonMethods.PasswordEncryption(password));
                }
                UserInformationEntity newData = userInformationRepository.save(userDetailsEntity);
                if (newData != null) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("id", newData.getId());
                    jsonObject2.put("streamName",
                            StringUtil.isNotNullOrEmpty(newData.getStreamName()) ? newData.getStreamName() : "");
                    jsonObject2.put("userId",
                            StringUtil.isNotNullOrEmpty(newData.getUserId()) ? newData.getUserId() : "");
                    jsonObject2.put("firstName",
                            StringUtil.isNotNullOrEmpty(newData.getFirstName()) ? newData.getFirstName() : "");
                    jsonObject2.put("lastName",
                            StringUtil.isNotNullOrEmpty(newData.getLastName()) ? newData.getLastName() : "");
                    jsonObject2.put("emailAddress",
                            StringUtil.isNotNullOrEmpty(newData.getEmailAddress()) ? newData.getEmailAddress() : "");
                    // jsonObject2.put("password",
                    // StringUtil.isNotNullOrEmpty(newData.getPassword()) ? newData.getPassword() :
                    // "");
                    jsonObject2.put("status",
                            StringUtil.isNotNullOrEmpty(newData.getStatus()) ? newData.getStatus() : "");
                    jsonObject2.put("languageId",
                            StringUtil.isNotNullOrEmpty(newData.getLanguageId()) ? newData.getLanguageId() : "");
                    array.put(jsonObject2);
                    jsonObject.put("data", array);
                    jsonObject.put("message", MessageConstants.SUCCESS);
                    jsonObject.put("status", MessageConstants.SUCCESS_CODE);
                } else {
                    jsonObject = CommonMethods.failedResponseMessage(MessageConstants.ERROR_WHILE_SAVING_DATA,
                            MessageConstants.ERROR_CODE);
                }
            } else {
                jsonObject.put("error", UUID.randomUUID().toString());
                jsonObject.put("data", validateUser);
                jsonObject.put("message", MessageConstants.FAILED);
                jsonObject.put("status", MessageConstants.FAILED_CODE);
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            jsonObject = CommonMethods.ErrorResponseMessage(e.getMessage(), " User Details Service - saveUserDetails");
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}