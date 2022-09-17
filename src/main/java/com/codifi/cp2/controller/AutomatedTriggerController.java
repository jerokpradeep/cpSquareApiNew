package com.codifi.cp2.controller;

import com.codifi.cp2.entity.ATMCategoryAlignmentEntity;
import com.codifi.cp2.entity.ATMHeaderMessageEntity;
import com.codifi.cp2.entity.AutomatedConditionFunctionEntity;
import com.codifi.cp2.entity.AutomatedTriggerCDTableEntity;
import com.codifi.cp2.entity.AutomatedTriggerEntity;
import com.codifi.cp2.service.AutomatedTriggerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/ATM")
@CrossOrigin
@Api(tags = "ATM", description = "Automated Trigger Maintenance")
public class AutomatedTriggerController {
    @Autowired
    AutomatedTriggerService automatedTriggerService;

    /**
     * Method To get All Automated Trigger Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @return List of Automated Trigger Maintenance Data
     */

    @GetMapping(value = "/getAutomatedTriggerDetail", headers = "Accept=application/json")
    public ResponseEntity<String> getAutomatedTriggerDetail() {
        return automatedTriggerService.getAutomatedTriggerDetail();

    }

    /**
     * Method to update Automated Trigger detail status
     * 
     * @author Pradeep Ravichandran
     * @param atmEntity
     * @return
     */
    @PostMapping(value = "/updateStatus", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> updateATMStatus(@RequestBody AutomatedTriggerEntity atmEntity) {
        return automatedTriggerService.updateATMStatus(atmEntity);

    }

    /**
     * Method to Delete Automated Trigger detail
     * 
     * @author Pradeep Ravichandran
     * @param atmEntity
     * @return
     */
    @PostMapping(value = "/deleteATM", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteATM(@RequestBody AutomatedTriggerEntity atmEntity) {
        return automatedTriggerService.deleteATM(atmEntity);
    }

    /**
     * Method to get Automated Trigger Data Field By id
     *
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    @GetMapping(value = "/getATMDetailsById/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> getATMDetailsById(@PathVariable int id) {
        return automatedTriggerService.getATMDetailsById(id);
    }

    /**
     * Method to save and update Automated Trigger Details
     * 
     * @author Pradeep
     * @param atmEntity
     * @return
     */
    @PostMapping(value = "/saveAutomatedTriggerDetail", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveFreeTextTriggerDetail(@RequestBody AutomatedTriggerEntity atmEntity) {
        if (atmEntity.getId() == null) {
            return automatedTriggerService.saveAutomatedTriggerDetail(atmEntity);
        } else {
            return automatedTriggerService.updateAutomatedTriggerDetail(atmEntity);
        }
    }

    /**
     * Method To get All Condition list
     * 
     * @author Pradeep Ravichandran
     * @return List of Condition list
     */

    @GetMapping(value = "/getAllConditionList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllConditionList() {
        return automatedTriggerService.getAllConditionList();
    }

    /**
     * Method To get All Field list
     * 
     * @author Pradeep Ravichandran
     * @return List of Field list
     */
    @GetMapping(value = "/getAllFieldList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllFieldList() {
        return automatedTriggerService.getAllFieldList();
    }

    /**
     * Method to save CDHDR and CDPOS
     * 
     * @author Pradeep
     * @param atmCDPOSEntity
     * @return
     */
    @PostMapping(value = "/saveCdhdrAndCdpos", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveCdhdrAndCdpos(@RequestBody AutomatedTriggerCDTableEntity atmCDPOSEntity) {
        return automatedTriggerService.saveCdhdrAndCdpos(atmCDPOSEntity);
    }

    /**
     * Method to save conditional function
     * 
     * @author Pradeep
     * @param atmCondFuncEntity
     * @return
     */
    @PostMapping(value = "/saveCondFunc", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveCondFunc(@RequestBody AutomatedConditionFunctionEntity atmCondFuncEntity) {
        return automatedTriggerService.saveCondFunc(atmCondFuncEntity);
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
    public ResponseEntity<String> saveCatAlignment(@RequestBody ATMCategoryAlignmentEntity atmCatAlignEntity) {
        return automatedTriggerService.saveCatAlignment(atmCatAlignEntity);
    }

    /**
     * Method to save Variable Messages
     * 
     * @author Pradeep
     * @param atmVariableEntity
     * @return
     */
    @PostMapping(value = "/saveVariableMessage", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveVariableMessage(@RequestBody ATMHeaderMessageEntity atmVariableEntity) {
        return automatedTriggerService.saveVariableMessage(atmVariableEntity);
    }

    /**
     * Method To get All Extracted Data Field List with out date type
     * 
     * @author Pradeep Ravichandran
     * @return List of Extracted Data Field List
     */
    @GetMapping(value = "/getExtractedDataFieldList", headers = "Accept=application/json")
    public ResponseEntity<String> getExtractedDataFieldList() {
        return automatedTriggerService.getExtractedDataFieldList();
    }
}
