package com.codifi.cp2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.codifi.cp2.entity.ExtractedDataFieldEntity;
import com.codifi.cp2.service.ExtractedDataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/extractedData")
@CrossOrigin
@Api(tags = "EFM", description = "Extracted Data Field List Maintenance")
public class ExtractedDataFieldController {
    @Autowired
    ExtractedDataFieldService extractedDataFieldService;

    /**
     * Method To get All Extracted Data Field List
     * 
     * @author Pradeep Ravichandran
     * @return List of Extracted Data Field List
     */
    @GetMapping(value = "/getExtractedDataFieldList", headers = "Accept=application/json")
    public ResponseEntity<String> getExtractedDataFieldList() {
        return extractedDataFieldService.getExtractedDataFieldList();
    }

    /**
     * Method to Delete Extracted Data Field List Record
     * 
     * @author Pradeep Ravichandran
     * @param extractedDataFieldEntity
     * @return
     */
    @PostMapping(value = "/deleteExtractedDataFieldList", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteExtractedDataFieldList(
            @RequestBody ExtractedDataFieldEntity extractedDataFieldEntity) {
        return extractedDataFieldService.deleteExtractedDataFieldList(extractedDataFieldEntity);
    }

    /**
     * Method to save Extracted data Field List
     * 
     * @author Pradeep Ravichandran
     * @param extractedDataFieldEntity
     * @return
     */
    @RequestMapping(value = "/saveExtractedDataFieldList", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> saveExtractedDataFieldList(
            @RequestBody ExtractedDataFieldEntity extractedDataFieldEntity) {
        return extractedDataFieldService.saveExtractedDataFieldList(extractedDataFieldEntity);
    }

    /**
     * Method to get Extracted Data Field By id
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    @GetMapping(value = "/getExtractedDataById/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> getExtractedDataById(@PathVariable int id) {
        return extractedDataFieldService.getExtractedDataById(id);
    }

    /**
     * Method to get Extracted Master Data for screen
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getExtractedMasterData", headers = "Accept=application/json")
    public ResponseEntity<String> getExtractedMasterData() {
        return extractedDataFieldService.getExtractedMasterData();
    }
}
