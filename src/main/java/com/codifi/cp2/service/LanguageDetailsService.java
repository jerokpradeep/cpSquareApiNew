package com.codifi.cp2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.LanguageDetailsEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.helper.LanguageDetailsHelper;
import com.codifi.cp2.repository.LanguageDetailsRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class LanguageDetailsService {

    @Autowired
    LanguageDetailsRepository languageDetailsRepository;
    @Autowired
    LanguageDetailsHelper languageDetailsHelper;

    /**
     * method to get all languages Details
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity getAllLanguages() {
        ResponseEntity responseEntity = new ResponseEntity();
        List<LanguageDetailsEntity> languageDetailsEntities = languageDetailsRepository.findAll();
        if (StringUtil.isListNotNullOrEmpty(languageDetailsEntities)) {
            responseEntity.setResult(languageDetailsEntities);
            responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
            responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
        } else {
            responseEntity.setReason(MessageConstants.DATA_NULL);
            responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
            responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
        }
        return responseEntity;
    }

    /**
     * method to save and Update language Details
     * 
     * @author Pradeep Ravichandran
     * @param countryDetailsEntity
     * @return
     */
    public ResponseEntity saveAndUpdateLanguageDetails(LanguageDetailsEntity languageDetailsEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<String> validatelanguageDetails = languageDetailsHelper.validateCountryDetails(languageDetailsEntity);
        if (StringUtil.isListNullOrEmpty(validatelanguageDetails)) {
            LanguageDetailsEntity newEntity = languageDetailsRepository.save(languageDetailsEntity);
            if (newEntity != null) {
                responseEntity.setResult(newEntity);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            }
        } else {
            responseEntity.setResult(validatelanguageDetails);
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }

}
