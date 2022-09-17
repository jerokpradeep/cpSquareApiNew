package com.codifi.cp2.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.LanguageDetailsEntity;
import com.codifi.cp2.entity.PageDetailsEntity;
import com.codifi.cp2.entity.TranslateEntity;
import com.codifi.cp2.model.response.PageTranslateModel;
import com.codifi.cp2.model.response.TranslateResponseModel;
import com.codifi.cp2.model.response.TranslateResultModel;
import com.codifi.cp2.repository.LanguageDetailsRepository;
import com.codifi.cp2.repository.PageDetailsRepository;
import com.codifi.cp2.repository.TranslateRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class TranslateHelper {
    @Autowired
    PageDetailsRepository pageDetailsRepository;
    @Autowired
    LanguageDetailsRepository countryDetailsRepository;
    @Autowired
    TranslateRepository repository;

    /**
     * Method to validate Translate Details while storing data
     * 
     * @author pradeep ravichandran
     * @param translateEntity
     * @return
     */
    public List<String> validateTranslateDetails(List<TranslateEntity> translateEntities, boolean isUpdateRecord) {
        List<String> validationResult = new ArrayList<>();
        boolean pageIdNotNull = true;
        boolean langIdNotNull = true;
        for (TranslateEntity translateEntity : translateEntities) {
            if (translateEntity != null) {
                // validate page Number
                if (translateEntity.getPageId() != null && translateEntity.getPageId() > 0) {
                    PageDetailsEntity pageDetailsEntity = pageDetailsRepository
                            .findByPageId(translateEntity.getPageId());
                    if (pageDetailsEntity != null && pageDetailsEntity.getActiveStatus() != 1) {
                        pageIdNotNull = false;
                        validationResult.add(MessageConstants.PAGE_NOT_ACTIVATED);
                    }
                } else {
                    pageIdNotNull = false;
                    validationResult.add(MessageConstants.PAGEID_WRONG);
                }
                // validate Language Id
                if (translateEntity.getLanguageId() != null && translateEntity.getLanguageId() > 0) {
                    LanguageDetailsEntity countryDetailsEntity = countryDetailsRepository
                            .findByLangId(translateEntity.getLanguageId());
                    if (countryDetailsEntity != null && countryDetailsEntity.getActiveStatus() != 1) {
                        langIdNotNull = false;
                        validationResult.add(MessageConstants.LANGUAGE_NOT_ACTIVATED);
                    }
                } else {
                    langIdNotNull = false;
                    validationResult.add(MessageConstants.LANGID_WRONG);
                }
                if (StringUtil.isNullOrEmpty(translateEntity.getTextName())) {
                    validationResult.add(MessageConstants.TEXT_NAME_NULL);
                }
                if (StringUtil.isNullOrEmpty(translateEntity.getDescription())) {
                    validationResult.add(MessageConstants.TEXT_DESCRIPTION_NULL);
                }
                if (isUpdateRecord) {
                    if (translateEntity.getTextId() != null && translateEntity.getTextId() > 0 && pageIdNotNull
                            && langIdNotNull) {
                        TranslateEntity oldTranslateRecord = repository.findByPageLangAndTextId(
                                translateEntity.getPageId(), translateEntity.getLanguageId(),
                                translateEntity.getTextId());
                        if (oldTranslateRecord == null
                                || oldTranslateRecord != null && oldTranslateRecord.getActiveStatus() != 1) {
                            validationResult.add(MessageConstants.INVALID_TEXT_ID);
                        }
                    } else {
                        validationResult.add(MessageConstants.TEXT_ID_NULL);
                    }
                }
            } else {
                validationResult = new ArrayList<String>();
                validationResult.add(MessageConstants.DATA_NULL);
            }
        }
        return validationResult;
    }

    /**
     * Method to transform the translate output based on UI Requirement
     * 
     * @author pradeep Ravichandran
     * @param translateEntities
     * @return
     */
    public List<TranslateResponseModel> populateOutputData(List<TranslateEntity> translateEntities) {
        List<TranslateResponseModel> responseEntities = null;
        if (StringUtil.isListNotNullOrEmpty(translateEntities)) {
            responseEntities = new ArrayList<>();
            for (TranslateEntity oldEntity : translateEntities) {
                TranslateResponseModel newEntity = new TranslateResponseModel();
                newEntity.setId(oldEntity.getId());
                newEntity.setPageId(oldEntity.getPageId());
                newEntity.setDescription(oldEntity.getDescription());
                newEntity.setTextId(oldEntity.getTextId());
                newEntity.setTextName(oldEntity.getTextName());
                responseEntities.add(newEntity);
            }
        }
        return responseEntities;
    }

    /**
     * Method to generate Text ID
     * 
     * @param translateEntities
     */
    public void populateTextId(List<TranslateEntity> translateEntities) {
        for (TranslateEntity translateEntity : translateEntities) {
            int maxTextId = repository.maxTextID(translateEntity.getPageId());
            if (maxTextId == 0) {
                translateEntity.setTextId(Integer.toUnsignedLong(1));
            } else {
                int newTextId = maxTextId + 1;
                translateEntity.setTextId(Integer.toUnsignedLong(newTextId));
            }
        }
    }

    /**
     * Method to validate delete function
     * 
     * @param translateEntity
     * @return
     */
    public List<String> validateDeleteTranslateDetails(TranslateEntity translateEntity) {
        List<String> validationResult = new ArrayList<>();
        if (StringUtil.isNullOrEmpty(translateEntity.getCreatedBy())) {
            validationResult.add(MessageConstants.TEXT_DESCRIPTION_NULL);
        }
        if (translateEntity.getPageId() != null && translateEntity.getPageId() > 0) {
            PageDetailsEntity pageDetailsEntity = pageDetailsRepository.findByPageId(translateEntity.getPageId());
            if (pageDetailsEntity != null && pageDetailsEntity.getActiveStatus() != 1) {
                validationResult.add(MessageConstants.PAGE_NOT_ACTIVATED);
            }
        } else {
            validationResult.add(MessageConstants.PAGEID_WRONG);
        }
        if (translateEntity.getTextId() != null && translateEntity.getTextId() > 0) {
            List<TranslateEntity> oldEntities = repository.findByTextandPageId(translateEntity.getTextId(),
                    translateEntity.getPageId());
            if (StringUtil.isListNullOrEmpty(oldEntities)) {
                validationResult.add(MessageConstants.INVALID_TEXT_ID);
            }
        } else {
            validationResult.add(MessageConstants.TEXT_ID_NULL);
        }
        return validationResult;
    }

    /**
     * Method to populate Data By Text Id
     * 
     * @author Pradeep Ravichandran
     * @param translateEntities
     * @return
     */
    public TranslateResultModel populateDataByTextId(List<TranslateEntity> translateEntities) {
        TranslateResultModel objOne = new TranslateResultModel();
        List<PageTranslateModel> entities = new ArrayList<PageTranslateModel>();
        JSONArray finalArray = new JSONArray();
        Map<String, List<TranslateEntity>> pageMap = new HashMap<>();
        objOne.setPageId(Long.toString(translateEntities.get(0).getPageId()));
        for (TranslateEntity translateEntity : translateEntities) {
            if (translateEntity != null) {
                if (StringUtil.isListNullOrEmpty(pageMap.get(Long.toString(translateEntity.getTextId())))) {
                    List<TranslateEntity> entity = new ArrayList<>();
                    entity.add(translateEntity);
                    pageMap.put(Long.toString(translateEntity.getTextId()), entity);
                } else {
                    pageMap.get(Long.toString(translateEntity.getTextId())).add(translateEntity);
                }
            }
        }
        for (Map.Entry<String, List<TranslateEntity>> entry : pageMap.entrySet()) {
            PageTranslateModel pageTranslatemodel = new PageTranslateModel();
            pageTranslatemodel.setTextId(entry.getKey());
            List<TranslateResponseModel> output = populateDataByPageId(entry.getValue());
            pageTranslatemodel.setLanguages(output);
            entities.add(pageTranslatemodel);
            finalArray.put(objOne);
        }
        objOne.setPageTranslateModel(entities);
        return objOne;
    }

    /**
     * Method to transform the translate output based on UI Requirement
     * 
     * @author pradeep Ravichandran
     * @param translateEntities
     * @return
     */
    public List<TranslateResponseModel> populateDataByPageId(List<TranslateEntity> translateEntities) {
        List<TranslateResponseModel> responseEntities = null;
        if (StringUtil.isListNotNullOrEmpty(translateEntities)) {
            responseEntities = new ArrayList<>();
            for (TranslateEntity oldEntity : translateEntities) {
                TranslateResponseModel newEntity = new TranslateResponseModel();
                newEntity.setId(oldEntity.getId());
                newEntity.setDescription(oldEntity.getDescription());
                newEntity.setTextName(oldEntity.getTextName());
                newEntity.setLanguageId(oldEntity.getLanguageId());
                responseEntities.add(newEntity);
            }
        }
        return responseEntities;
    }
}
