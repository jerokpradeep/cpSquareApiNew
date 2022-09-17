package com.codifi.cp2.helper;

import java.util.ArrayList;
import java.util.List;

import com.codifi.cp2.entity.FreeTextMaintenanceEntity;
import com.codifi.cp2.repository.FreeTextMaintenanceRepository;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreeTextMaintenanceHelper {
    @Autowired
    FreeTextMaintenanceRepository freeTextMaintenanceRepository;

    public List<String> validateDetailUpdate(FreeTextMaintenanceEntity ftmEntity) {
        List<String> errorMessage = new ArrayList<>();
        FreeTextMaintenanceEntity freeTextTriggerEntityList = freeTextMaintenanceRepository
                .findByTriggerName(ftmEntity.getTriggerNameEnglish());
        if (freeTextTriggerEntityList != null && freeTextTriggerEntityList.getActiveStatus() != 0) {
            errorMessage.add(MessageConstants.FREE_TEXT_TRIGGER_NAME_EXIST);
        }
        return errorMessage;
    }

}
