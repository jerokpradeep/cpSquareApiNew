package com.codifi.cp2.controller;

import com.codifi.cp2.entity.FreeTextMaintenanceEntity;
import com.codifi.cp2.service.FreeTextMaintenanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/FTM")
@CrossOrigin
@Api(tags = "FTM", description = "Free Text Trigger Maintenance")
public class FreeTextMaintenanceController {
    @Autowired
    FreeTextMaintenanceService freeTextMaintenanceService;

    /**
     * Method To get All Free Text Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @return List of Free Text Maintenance Data
     */
    @GetMapping(value = "/getFreeTextTriggerDetail", headers = "Accept=application/json")
    public ResponseEntity<String> getFreeTextTriggerDetail() {
        return freeTextMaintenanceService.getFreeTextTriggerDetail();
    }

    /**
     * Method to update Free Text Maintenance status
     * 
     * @author Pradeep Ravichandran
     * @param ftmEntity
     * @return
     */
    @PostMapping(value = "/updateStatus", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> updateFTMStatus(@RequestBody FreeTextMaintenanceEntity ftmEntity) {
        return freeTextMaintenanceService.updateFTMStatus(ftmEntity);

    }

    /**
     * Method to Delete Free Text Maintenance
     * 
     * @author Pradeep Ravichandran
     * @param ftmEntity
     * @return
     */
    @PostMapping(value = "/deleteFTM", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteFTM(@RequestBody FreeTextMaintenanceEntity ftmEntity) {
        return freeTextMaintenanceService.deleteFTM(ftmEntity);
    }

    /**
     * Method to get Free Text Maintenance Data Field By id
     *
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    @GetMapping(value = "/getFTMDataById/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> getFTMById(@PathVariable int id) {
        return freeTextMaintenanceService.getFTMDataById(id);
    }

    /**
     * Method to save Free Text Trigger Details
     * 
     * @author Pradeep
     * @param ftmEntity
     * @return
     */
    @PostMapping(value = "/saveFreeTextTriggerDetail", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveFreeTextTriggerDetail(@RequestBody FreeTextMaintenanceEntity ftmEntity) {
        if (ftmEntity.getId() == null) {
            return freeTextMaintenanceService.saveFreeTextTriggerDetail(ftmEntity);
        } else {
            return freeTextMaintenanceService.updateFreeTextTriggerDetail(ftmEntity);
        }
    }

    /**
     * Method to get All Template and Field List
     * 
     * @author Pradeep
     * @return
     */
    @GetMapping(value = "/getAllTemplateList")
    public ResponseEntity<String> getAllTemplateList() {
        return freeTextMaintenanceService.getAllTemplateList();
    }

    /**
     * Method to get All Distribution List
     * 
     * @author Pradeep
     * @return
     */
    @GetMapping(value = "/getAllDistributionList")
    public ResponseEntity<String> getAllDistributionList() {
        return freeTextMaintenanceService.getAllDistributionList();
    }

    /**
     * Method to save Stream Distribution List
     * 
     * @author Pradeep
     * @param freeTextTriggerCriteria
     * @return
     */
    @PostMapping(value = "/saveDistributionList", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveDistributionList(@RequestBody FreeTextMaintenanceEntity freeTextTriggerCriteria) {
        return freeTextMaintenanceService.saveDistributionList(freeTextTriggerCriteria);
    }

    /**
     * Method to save category Alignment
     * 
     * @author Pradeep
     * @param atmCatAlignEntity
     * @return
     */
    @PostMapping(value = "/saveCatAlignment", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveCatAlignment(@RequestBody FreeTextMaintenanceEntity freeTextTriggerCriteria) {
        return freeTextMaintenanceService.saveCatAlignment(freeTextTriggerCriteria);
    }
}
