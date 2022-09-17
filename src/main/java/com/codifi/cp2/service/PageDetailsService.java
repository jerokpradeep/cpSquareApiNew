package com.codifi.cp2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.PageDetailsEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.helper.PageDetailsHelper;
import com.codifi.cp2.repository.PageDetailsRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class PageDetailsService {
    @Autowired
    PageDetailsHelper pageDetailsHelper;
    @Autowired
    PageDetailsRepository pageDetailsRepository;

    /**
     * method to get all Page Details
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity getAllPageList() {
        ResponseEntity responseEntity = new ResponseEntity();
        List<PageDetailsEntity> pageDetailsEntities = pageDetailsRepository.findAll();
        if (StringUtil.isListNotNullOrEmpty(pageDetailsEntities)) {
            responseEntity.setResult(pageDetailsEntities);
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
     * method to save and Update page Details
     * 
     * @author Pradeep Ravichandran
     * @param pageDetailsEntity
     * @return
     */
    public ResponseEntity saveAndUpdatePageDetails(PageDetailsEntity pageDetailsEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<String> validatePageDetails = pageDetailsHelper.validatePageDetails(pageDetailsEntity);
        if (StringUtil.isListNullOrEmpty(validatePageDetails)) {
            PageDetailsEntity newEntity = pageDetailsRepository.save(pageDetailsEntity);
            if (newEntity != null) {
                responseEntity.setResult(newEntity);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            }
        } else {
            responseEntity.setResult(validatePageDetails);
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }
}
