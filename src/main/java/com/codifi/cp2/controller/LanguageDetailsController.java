package com.codifi.cp2.controller;

import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import com.codifi.cp2.entity.LanguageDetailsEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.service.LanguageDetailsService;

@RestController
@RequestMapping("/language")
@CrossOrigin
@Api(tags = "Languages", description = "Language catalog")
public class LanguageDetailsController {

    @Autowired
    LanguageDetailsService languageDetailsService;

    /**
     * method to get all languages Details
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllLanguages")
    public ResponseEntity getAllLanguages() {
        return languageDetailsService.getAllLanguages();
    }

    /**
     * method to save and Update languages Details
     * 
     * @author Pradeep Ravichandran
     * @param languageDetailsEntity
     * @return
     */
    @PostMapping(value = "/saveLanguageDetails", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity saveLanguageDetails(@RequestBody LanguageDetailsEntity languageDetailsEntity) {
        return languageDetailsService.saveAndUpdateLanguageDetails(languageDetailsEntity);
    }

}
