package com.codifi.cp2.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.HelpEntity;
import com.codifi.cp2.entity.PageDetailsEntity;
import com.codifi.cp2.repository.PageDetailsRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class HelpDetailsHelper {
	@Autowired
	PageDetailsRepository pageDetailsRepository;

	/**
	 * Method to validate Help Details while storing data
	 * 
	 * @author pradeep ravichandran
	 * @param helpEntity
	 * @return
	 */
	public List<String> validateHelpDetails(HelpEntity helpEntity) {
		List<String> validationResult = new ArrayList<>();
		if (helpEntity != null) {
			// validate page Number
			if (helpEntity.getPageId() != null && helpEntity.getPageId() > 0) {
				PageDetailsEntity pageDetailsEntity = pageDetailsRepository.findByPageId(helpEntity.getPageId());
				if (pageDetailsEntity != null && pageDetailsEntity.getActiveStatus() != 1) {
					validationResult.add(MessageConstants.PAGE_NOT_ACTIVATED);
				}
			} else {
				validationResult.add(MessageConstants.PAGEID_WRONG);
			}
			if (StringUtil.isNullOrEmpty(helpEntity.getDescription())) {
				validationResult.add(MessageConstants.TEXT_DESCRIPTION_NULL);
			}
			if (helpEntity.getLanguageId() == null || helpEntity.getLanguageId() <= 0) {
				validationResult.add(MessageConstants.LANGID_NULL);
			}
		} else {
			validationResult = new ArrayList<String>();
			validationResult.add(MessageConstants.DATA_NULL);
		}
		return validationResult;
	}

}
