package com.codifi.cp2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.entity.TranslateEntity;
import com.codifi.cp2.service.TranslateService;

@RestController
@RequestMapping("/translate")
@CrossOrigin
@Api(tags = "Translations", description = "Translate Catalog")
public class TranslateController {
    @Autowired
    TranslateService service;

    /**
     * method to get translate Keys By page Id and Language ID
     * 
     * @author Pradeep Ravichandran
     * @param translateEntity
     * @return
     */
    @PostMapping(value = "/getByPageAndLangIds", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getByPageAndLangIds(@RequestBody TranslateEntity translateEntity) {
        return service.getByPageAndLangIds(translateEntity);
    }

    /**
     * method to save Translate Details
     * 
     * @author Pradeep Ravichandran
     * @param translateEntity
     * @return
     */
    @PostMapping(value = "/saveTranslateDetails", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity saveTranslateDetails(@RequestBody List<TranslateEntity> translateEntities) {
        return service.saveTranslateDetails(translateEntities);
    }

    /**
     * method to Update Translate Details
     * 
     * @author Pradeep Ravichandran
     * @param translateEntity
     * @return
     */
    @PostMapping(value = "/updateTranslateDetails", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity updateTranslateDetails(@RequestBody List<TranslateEntity> translateEntities) {
        return service.updateTranslateDetails(translateEntities);
    }

    /**
     * method to delete the translate record
     * 
     * @author Pradeep Ravichandran
     * @param long id
     * @return
     */
    @PostMapping(value = "/deleteTranslateDetails", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity deleteTranslateDetails(@RequestBody TranslateEntity translateEntity) {
        return service.deleteTranslateDetails(translateEntity);
    }

    /**
     * method to get translate Keys By page Id
     * 
     * @author Pradeep Ravichandran
     * @param translateEntity
     * @return
     */
    @PostMapping(value = "/getByPageId", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getByPageId(@RequestBody TranslateEntity translateEntity) {
        ResponseEntity responseEntity = service.getByPageId(translateEntity);
        return responseEntity;
    }
}
