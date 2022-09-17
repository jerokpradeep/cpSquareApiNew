package com.codifi.cp2.helper;

import java.util.ArrayList;
import java.util.List;

import com.codifi.cp2.entity.FieldListCategoryEntity;
import com.codifi.cp2.repository.FieldListCategoryRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldListCategoryHelper {
    @Autowired
    FieldListCategoryRepository fieldListCategoryRepository;

    public List<String> validateFieldListCategory(FieldListCategoryEntity flcEntity) {
        List<String> validationResult = new ArrayList<>();
        FieldListCategoryEntity entity = fieldListCategoryRepository
                .findByCategoryTechnicalName(flcEntity.getCategoryTechnicalName());
        if (entity != null && StringUtil.isNotNullOrEmpty(entity.getCategoryTechnicalName())) {
            validationResult.add(MessageConstants.CATEGORY_TECHNOLOGY_NAME_EXIST);
        }
        return validationResult;
    }
}