package com.codifi.cp2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.LanguageDetailsEntity;
import com.codifi.cp2.entity.PageDetailsEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.entity.TranslateEntity;
import com.codifi.cp2.model.response.TranslateResponseModel;
import com.codifi.cp2.model.response.TranslateResultModel;
import com.codifi.cp2.helper.TranslateHelper;
import com.codifi.cp2.repository.LanguageDetailsRepository;
import com.codifi.cp2.repository.PageDetailsRepository;
import com.codifi.cp2.repository.TranslateRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class TranslateService {
    @Autowired
    PageDetailsRepository pageDetailsRepository;
    @Autowired
    LanguageDetailsRepository languageDetailsRepository;
    @Autowired
    TranslateRepository repository;
    @Autowired
    TranslateHelper tansHelper;

    /**
     * method to get translate Keys By page Id and Language ID
     * 
     * @author Pradeep Ravichandran
     * @param helpEntity
     * @return
     */
    public ResponseEntity getByPageAndLangIds(TranslateEntity translateEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (translateEntity != null && translateEntity.getPageId() != null && translateEntity.getPageId() > 0
                && translateEntity.getLanguageId() != null && translateEntity.getLanguageId() > 0) {
            PageDetailsEntity pageDetailsEntity = pageDetailsRepository.findByPageId(translateEntity.getPageId());
            if (pageDetailsEntity != null && pageDetailsEntity.getActiveStatus() == 1) {
                LanguageDetailsEntity countryDetailsEntity = languageDetailsRepository
                        .findByLangId(translateEntity.getLanguageId());
                if (countryDetailsEntity != null && countryDetailsEntity.getActiveStatus() == 1) {
                    List<TranslateEntity> translateEntities = repository.findByLangId(translateEntity.getPageId(),
                            translateEntity.getLanguageId());
                    List<TranslateResponseModel> responseEntities = tansHelper.populateOutputData(translateEntities);
                    if (StringUtil.isListNotNullOrEmpty(responseEntities)) {
                        responseEntity.setResult(responseEntities);
                        responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                        responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
                    } else {
                        responseEntity.setReason(MessageConstants.DATA_NULL);
                        responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                        responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
                    }
                } else {
                    if (countryDetailsEntity == null) {
                        responseEntity.setReason(MessageConstants.LANGID_WRONG);
                    } else {
                        responseEntity.setReason(MessageConstants.LANGUAGE_NOT_ACTIVATED);
                    }
                    responseEntity.setStatus(MessageConstants.FAILED_STATUS);
                    responseEntity.setMessage(MessageConstants.FAILED_MSG);
                }
            } else {
                if (pageDetailsEntity == null) {
                    responseEntity.setReason(MessageConstants.PAGEID_WRONG);
                } else {
                    responseEntity.setReason(MessageConstants.PAGE_NOT_ACTIVATED);
                }
                responseEntity.setStatus(MessageConstants.FAILED_STATUS);
                responseEntity.setMessage(MessageConstants.FAILED_MSG);
            }
        } else {
            if (translateEntity == null) {
                responseEntity.setReason(MessageConstants.DATA_NULL);
            } else if (translateEntity != null && translateEntity.getPageId() == null
                    || translateEntity.getPageId() < 0) {
                responseEntity.setReason(MessageConstants.PAGEID_NULL);
            } else if (translateEntity != null && translateEntity.getLanguageId() == null
                    || translateEntity.getLanguageId() < 0) {
                responseEntity.setReason(MessageConstants.LANGID_NULL);
            }
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }

    /**
     * method to save and Update Translate Details
     * 
     * @author Pradeep Ravichandran
     * @param translateEntity
     * @return
     */
    public ResponseEntity saveTranslateDetails(List<TranslateEntity> translateEntities) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<String> validateTranslateDetails = tansHelper.validateTranslateDetails(translateEntities, false);
        if (StringUtil.isListNullOrEmpty(validateTranslateDetails)) {
            tansHelper.populateTextId(translateEntities);
            List<TranslateEntity> newEntities = repository.saveAll(translateEntities);
            if (StringUtil.isListNotNullOrEmpty(newEntities)) {
                responseEntity.setResult(newEntities);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            }
        } else {
            responseEntity.setResult(validateTranslateDetails);
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }

    /**
     * method to Update Translate Details
     * 
     * @author Pradeep Ravichandran
     * @param translateEntity
     * @return
     */
    public ResponseEntity updateTranslateDetails(List<TranslateEntity> translateEntities) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<String> validateTranslateDetails = tansHelper.validateTranslateDetails(translateEntities, true);
        if (StringUtil.isListNullOrEmpty(validateTranslateDetails)) {
            List<TranslateEntity> newEntities = new ArrayList<>();
            for (TranslateEntity translateEntity : translateEntities) {
                TranslateEntity oldTranslateRecord = repository.findByPageLangAndTextId(translateEntity.getPageId(),
                        translateEntity.getLanguageId(), translateEntity.getTextId());
                translateEntity.setId(oldTranslateRecord.getId());
                TranslateEntity updateEntity = repository.save(translateEntity);
                if (updateEntity != null) {
                    newEntities.add(updateEntity);
                }
            }
            if (StringUtil.isListNotNullOrEmpty(newEntities)) {
                responseEntity.setResult(newEntities);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            } else {
                responseEntity.setReason(MessageConstants.ERROR_UPDATE_RECORD);
                responseEntity.setStatus(MessageConstants.FAILED_STATUS);
                responseEntity.setMessage(MessageConstants.FAILED_MSG);
            }
        } else {
            responseEntity.setResult(validateTranslateDetails);
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }

    /**
     * method to delete the translate record
     * 
     * @author Pradeep Ravichandran
     * @param long id
     * @return
     */
    public ResponseEntity deleteTranslateDetails(TranslateEntity translateEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        int deleteRecord = 0;
        List<String> validateDeleteTranslateDetails = tansHelper.validateDeleteTranslateDetails(translateEntity);
        if (StringUtil.isListNullOrEmpty(validateDeleteTranslateDetails)) {
            List<TranslateEntity> oldEntities = repository.findByTextandPageId(translateEntity.getTextId(),
                    translateEntity.getPageId());
            if (StringUtil.isListNotNullOrEmpty(oldEntities)) {
                for (TranslateEntity oldRecord : oldEntities) {
                    deleteRecord = repository.deleteTranslate(oldRecord.getId(), translateEntity.getCreatedBy());
                }
                if (deleteRecord > 0) {
                    responseEntity.setResult(MessageConstants.DELETE_SUCCESS);
                    responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                    responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
                } else {
                    responseEntity.setResult(MessageConstants.CONTACT_ADMIN);
                    responseEntity.setStatus(MessageConstants.FAILED_STATUS);
                    responseEntity.setMessage(MessageConstants.FAILED_MSG);
                }
            } else {
                responseEntity.setResult(MessageConstants.RECORD_NOT_FOUND + translateEntity.getId());
                responseEntity.setStatus(MessageConstants.FAILED_STATUS);
                responseEntity.setMessage(MessageConstants.FAILED_MSG);
            }
        } else {
            responseEntity.setResult(validateDeleteTranslateDetails);
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }

    /**
     * method to get translate Keys By page Id
     * 
     * @author Pradeep Ravichandran
     * @param translateEntity
     * @return
     */
    public ResponseEntity getByPageId(TranslateEntity translateEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (translateEntity != null && translateEntity.getPageId() != null && translateEntity.getPageId() > 0) {
            PageDetailsEntity pageDetailsEntity = pageDetailsRepository.findByPageId(translateEntity.getPageId());
            if (pageDetailsEntity != null && pageDetailsEntity.getActiveStatus() == 1) {
                List<TranslateEntity> translateEntities = repository.findByPageId(translateEntity.getPageId());
                if (StringUtil.isListNotNullOrEmpty(translateEntities)) {
                    TranslateResultModel responseEntities = tansHelper.populateDataByTextId(translateEntities);
                    responseEntity.setResult(responseEntities);
                    responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                    responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
                } else {
                    responseEntity.setReason(MessageConstants.DATA_NULL);
                    responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                    responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
                }
            } else {
                if (pageDetailsEntity == null) {
                    responseEntity.setReason(MessageConstants.PAGEID_WRONG);
                } else {
                    responseEntity.setReason(MessageConstants.PAGE_NOT_ACTIVATED);
                }
                responseEntity.setStatus(MessageConstants.FAILED_STATUS);
                responseEntity.setMessage(MessageConstants.FAILED_MSG);
            }
        } else {
            if (translateEntity == null) {
                responseEntity.setReason(MessageConstants.DATA_NULL);
            } else if (translateEntity != null && translateEntity.getPageId() == null
                    || translateEntity.getPageId() < 0) {
                responseEntity.setReason(MessageConstants.PAGEID_NULL);
            }
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }
}
