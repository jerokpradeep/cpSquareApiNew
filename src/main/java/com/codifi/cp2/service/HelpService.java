package com.codifi.cp2.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codifi.cp2.entity.HelpEntity;
import com.codifi.cp2.entity.HelperAttachmentEntity;
import com.codifi.cp2.entity.LanguageDetailsEntity;
import com.codifi.cp2.entity.PageDetailsEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.model.response.HelperRespModel;
import com.codifi.cp2.helper.HelpDetailsHelper;
import com.codifi.cp2.repository.HelpRepository;
import com.codifi.cp2.repository.HelperAttachmentRepository;
import com.codifi.cp2.repository.LanguageDetailsRepository;
import com.codifi.cp2.repository.PageDetailsRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class HelpService {
    @Autowired
    HelpRepository helpRepository;
    @Autowired
    PageDetailsRepository pageDetailsRepository;
    @Autowired
    HelpDetailsHelper helpDetailsHelper;
    @Autowired
    LanguageDetailsRepository languageDetailsRepository;
    @Autowired
    HelperAttachmentRepository helperAttachmentRepository;

    /**
     * method to get all help question based on Page Id
     * 
     * @author Pradeep Ravichandran
     * @param helpEntity
     * @return
     */
    public ResponseEntity getByPageId(HelpEntity helpEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (helpEntity.getPageId() != null && helpEntity.getPageId() > 0 && helpEntity.getLanguageId() != null
                && helpEntity.getLanguageId() > 0) {
            PageDetailsEntity pageDetailsEntity = pageDetailsRepository.findByPageId(helpEntity.getPageId());
            if (pageDetailsEntity != null && pageDetailsEntity.getActiveStatus() == 1) {
                LanguageDetailsEntity languageDetailsEntity = languageDetailsRepository
                        .findByLangId(helpEntity.getLanguageId());
                if (languageDetailsEntity != null && languageDetailsEntity.getActiveStatus() == 1) {
                    List<HelpEntity> helpEntities = helpRepository.findByPageId(helpEntity.getPageId(),
                            helpEntity.getLanguageId());
                    if (StringUtil.isListNotNullOrEmpty(helpEntities)) {
                        responseEntity.setResult(helpEntities);
                        responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                        responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
                    } else {
                        responseEntity.setReason(MessageConstants.DATA_NULL);
                        responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                        responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
                    }
                } else {
                    if (languageDetailsEntity == null) {
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
            if (helpEntity.getPageId() == null || helpEntity.getPageId() < 0) {
                responseEntity.setReason(MessageConstants.PAGEID_NULL);
            } else {
                responseEntity.setReason(MessageConstants.LANGID_NULL);
            }
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }

    /**
     * method to save and Update Help Details
     * 
     * @author Pradeep Ravichandran
     * @param helpEntity
     * @return
     */
    public ResponseEntity saveAndUpdateHelpDetails(HelpEntity helpEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<String> validateResults = helpDetailsHelper.validateHelpDetails(helpEntity);
        if (StringUtil.isListNullOrEmpty(validateResults)) {
            HelpEntity newHelpEntity = helpRepository.save(helpEntity);
            if (newHelpEntity != null) {
                responseEntity.setResult(newHelpEntity);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            }
        } else {
            responseEntity.setResult(validateResults);
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }

    /**
     * method to save attachment Help Details
     * 
     * @author nesan
     * @param file
     * @param helpEntity
     * @return
     * @throws IOException
     */

    public ResponseEntity saveAttachmentDetails(MultipartFile file, HelperAttachmentEntity helperAttachmentEntity)
            throws IOException {
        ResponseEntity responseEntity = new ResponseEntity();
        if (!file.isEmpty() && helperAttachmentEntity.getPageId() > 0
                && StringUtil.isNotNullOrEmpty(helperAttachmentEntity.getName())) {

            String name = file.getOriginalFilename();
            String fileType = name.substring(name.length() - 3);
            file.getInputStream();
            byte[] byteArr = file.getBytes();
            String result = Base64.getEncoder().encodeToString(byteArr);
            helperAttachmentEntity.setAttachment(result);
            helperAttachmentEntity.setFileType(fileType);
            HelperAttachmentEntity newhelperAttachmentEntity = helperAttachmentRepository.save(helperAttachmentEntity);
            if (newhelperAttachmentEntity != null) {
                responseEntity.setResult(newhelperAttachmentEntity);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            } else {
                responseEntity.setResult(MessageConstants.ERROR_INSERT_ATTACHMENT);
                responseEntity.setStatus(MessageConstants.FAILED_STATUS);
                responseEntity.setMessage(MessageConstants.FAILED_MSG);
            }
        } else {
            responseEntity.setResult(MessageConstants.EMPTY_DATA);
            responseEntity.setStatus(MessageConstants.FAILED_STATUS);
            responseEntity.setMessage(MessageConstants.FAILED_MSG);
        }
        return responseEntity;
    }

    /**
     * Method to get Attachment details by Id
     * 
     * @author Pradeep Ravichandran
     * @param helperAttachmentEntity
     * @return
     */
    public ResponseEntity getAttachmentDetailsById(HelperAttachmentEntity helperAttachmentEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtil.isNotNullOrEmpty(helperAttachmentEntity.getPageId()) && helperAttachmentEntity.getPageId() > 0
                && StringUtil.isNotNullOrEmpty(helperAttachmentEntity.getLanguageId())
                && helperAttachmentEntity.getLanguageId() > 0) {
            List<HelperRespModel> respHelperAttachmentEntitie = helperAttachmentRepository
                    .findByPageId(helperAttachmentEntity.getPageId(), helperAttachmentEntity.getLanguageId());
            if (StringUtil.isListNotNullOrEmpty(respHelperAttachmentEntitie)) {
                responseEntity.setResult(respHelperAttachmentEntitie);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            } else {
                responseEntity.setReason(MessageConstants.DATA_NULL);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            }
        }
        return responseEntity;
    }

    /**
     * Method to get Attachmenyt file by Id
     * 
     * @author Pradeep Ravichandran
     * @param helperAttachmentEntity
     * @return
     */
    public ResponseEntity getAttachmentId(HelperAttachmentEntity helperAttachmentEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtil.isNotNullOrEmpty(helperAttachmentEntity.getId()) && helperAttachmentEntity.getId() > 0) {
            Optional<HelperAttachmentEntity> respHelperAttachmentEntitie = helperAttachmentRepository
                    .findById(helperAttachmentEntity.getId());
            if (respHelperAttachmentEntitie != null) {
                responseEntity.setResult(respHelperAttachmentEntitie);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            } else {
                responseEntity.setReason(MessageConstants.DATA_NULL);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            }
        }

        return responseEntity;
    }

    /**
     * Method to delete Attachment By Id
     * 
     * @author Pradeep Ravichandran
     * @param helperAttachmentEntity
     * @return
     */
    public ResponseEntity deleteAttachmentById(HelperAttachmentEntity helperAttachmentEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        helperAttachmentRepository.deleteById(helperAttachmentEntity.getId());
        responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
        responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
        return responseEntity;
    }
}
