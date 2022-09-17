package com.codifi.cp2.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.PageDetailsEntity;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class PageDetailsHelper {
	/**
	 * Method to validate Page Details while storing data
	 * 
	 * @author pradeep ravichandran
	 * @param pageDetailsEntity
	 * @return
	 */
	public List<String> validatePageDetails(PageDetailsEntity pageDetailsEntity) {
		List<String> validationResult = new ArrayList<>();
		Pattern stringPattern = Pattern.compile(MessageConstants.STRING_PATTERN);
		if (StringUtil.isNullOrEmpty(pageDetailsEntity.getPageName())) {
			validationResult.add(MessageConstants.PAGE_NAME_NULL);
		}
		if (StringUtil.isNullOrEmpty(pageDetailsEntity.getDescription())) {
			validationResult.add(MessageConstants.PAGE_DESC_NULL);
		}
		if (StringUtil.isNotNullOrEmpty(pageDetailsEntity.getPageName())) {
			Matcher matcher = stringPattern.matcher(pageDetailsEntity.getPageName());
			if (matcher.find()) {
				validationResult.add(MessageConstants.SPECIAL_CHAR_PAGE_NAME);
			}
		}
		if (StringUtil.isNotNullOrEmpty(pageDetailsEntity.getDescription())) {
			Matcher matcher = stringPattern.matcher(pageDetailsEntity.getDescription());
			if (matcher.find()) {
				validationResult.add(MessageConstants.SPECIAL_CHAR_PAGE_DESC);
			}
		}
		return validationResult;
	}

}
