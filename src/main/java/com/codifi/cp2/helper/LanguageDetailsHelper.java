package com.codifi.cp2.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.LanguageDetailsEntity;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class LanguageDetailsHelper {
	/**
	 * Method to validate language Details while storing data
	 * 
	 * @author pradeep ravichandran
	 * @param languageDetailsEntity
	 * @return
	 */
	public List<String> validateCountryDetails(LanguageDetailsEntity languageDetailsEntity) {
		List<String> validationResult = new ArrayList<>();
		Pattern stringPattern = Pattern.compile(MessageConstants.STRING_PATTERN);

		if (StringUtil.isNullOrEmpty(languageDetailsEntity.getLanguage())) {
			validationResult.add(MessageConstants.LANGUAGE_NULL);
		}
		if (StringUtil.isNullOrEmpty(languageDetailsEntity.getShortForm())) {
			validationResult.add(MessageConstants.SHORT_FORM_NULL);
		}
		if (StringUtil.isNotNullOrEmpty(languageDetailsEntity.getLanguage())) {
			Matcher matcher = stringPattern.matcher(languageDetailsEntity.getLanguage());
			if (matcher.find()) {
				validationResult.add(MessageConstants.SPECIAL_CHAR_LANGUAGE);
			}
		}
		if (StringUtil.isNotNullOrEmpty(languageDetailsEntity.getShortForm())) {
			Matcher matcher = stringPattern.matcher(languageDetailsEntity.getShortForm());
			if (matcher.find()) {
				validationResult.add(MessageConstants.SPECIAL_CHAR_SHORT_FORM);
			}
		}
		return validationResult;
	}

}
