package com.codifi.cp2.helper;

import java.util.*;
import com.codifi.cp2.entity.UserInformationEntity;
import com.codifi.cp2.repository.UserInformationRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsHelper {
    @Autowired
    UserInformationRepository userDetailRepository;

    /**
     * Method to validate User Details for save
     * 
     * @author Sandeep Govindaraj
     * @param userDetailsEntity
     * @return
     */
    public List<String> validateUserDetails(UserInformationEntity userDetailsEntity) {
        List<String> validationResult = new ArrayList<>();
        if (userDetailsEntity != null) {
            if (!StringUtil.isNullOrEmpty(userDetailsEntity.getUserId())) {
                UserInformationEntity userEntity = userDetailRepository.findByUserId(userDetailsEntity.getUserId());
                if (userEntity != null) {
                    validationResult.add(MessageConstants.USER_ID_EXIST);
                }
            } else {
                validationResult.add(MessageConstants.USER_ID_NULL);
            }
        }
        return validationResult;
    }
}
