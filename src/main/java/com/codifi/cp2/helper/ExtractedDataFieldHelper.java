package com.codifi.cp2.helper;

import java.util.*;
import com.codifi.cp2.entity.ExtractedDataFieldEntity;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

import org.springframework.stereotype.Service;

@Service
public class ExtractedDataFieldHelper {
    /**
     * Method to validate Extracted Data Field List for save
     * 
     * @author Pradeep Ravichandran
     * @param extractedDataFieldEntity
     * @return
     */
    public List<String> validateExtractedDataFieldList(ExtractedDataFieldEntity extractedDataFieldEntity) {
        List<String> errorMessage = new ArrayList<>();
        if (extractedDataFieldEntity != null) {
            if (StringUtil.isNullOrEmpty(extractedDataFieldEntity.getTableName())) {
                errorMessage.add(MessageConstants.TABLE_NAME_NULL);
            }
            if (StringUtil.isNullOrEmpty(extractedDataFieldEntity.getFieldName())) {
                errorMessage.add(MessageConstants.FIELD_NAME_NULL);
            }
            if (StringUtil.isNullOrEmpty(extractedDataFieldEntity.getSapFieldName())) {
                errorMessage.add(MessageConstants.SAP_FIELD_NAME_NULL);
            }
            if (StringUtil.isNullOrEmpty(extractedDataFieldEntity.getEasyName())) {
                errorMessage.add(MessageConstants.EASY_NAME_NULL);           
            }
            if (StringUtil.isNullOrEmpty(extractedDataFieldEntity.getDateType())) {
                errorMessage.add(MessageConstants.DATE_TYPE_NULL);            
            }
            if (StringUtil.isNullOrEmpty(extractedDataFieldEntity.getMessageRelevant())) {
                errorMessage.add(MessageConstants.MESSAGE_RELEVANT_NULL);            
            }
        } else {
            errorMessage.add(MessageConstants.DATA_NULL);
        }
        return errorMessage;
    }

}
