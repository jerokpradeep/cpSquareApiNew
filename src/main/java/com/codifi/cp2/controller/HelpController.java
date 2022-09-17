package com.codifi.cp2.controller;

import java.io.IOException;

import com.codifi.cp2.entity.HelpEntity;
import com.codifi.cp2.entity.HelperAttachmentEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.service.HelpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/help")
@CrossOrigin
@Api(tags = "Help", description = "Help Catalog")
public class HelpController {
    @Autowired
    HelpService helpService;

    /**
     * method to get all help question based on Page Id
     * 
     * @author Pradeep Ravichandran
     * @param helpEntity
     * @return
     */
    @PostMapping(value = "/getByPageId", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getByPageId(@RequestBody HelpEntity helpEntity) {
        return helpService.getByPageId(helpEntity);
    }

    /**
     * method to save and Update Help Details
     * 
     * @author Pradeep Ravichandran
     * @param helpEntity
     * @return
     */
    @PostMapping(value = "/saveHelpDetails", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity saveHelpDetails(@RequestBody HelpEntity helpEntity) {
        return helpService.saveAndUpdateHelpDetails(helpEntity);
    }

    /**
     * method to save attachment Details
     * 
     * @author Pradeep Ravichandran
     * @param helpEntity
     * @return
     * @throws IOException
     */

    @PostMapping(value = "/saveAttachmentDetails", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity saveAttachmentDetails(@RequestPart("file") MultipartFile file,
            HelperAttachmentEntity helperAttachmentEntity) throws IOException {
        return helpService.saveAttachmentDetails(file, helperAttachmentEntity);
    }

    /**
     * Method to get Attachment details by Id
     * 
     * @author Pradeep Ravichandran
     * @param helperAttachmentEntity
     * @return
     */
    @PostMapping(value = "/getAttachmentDetailsById", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getAttachmentDetailsById(@RequestBody HelperAttachmentEntity helperAttachmentEntity) {
        return helpService.getAttachmentDetailsById(helperAttachmentEntity);
    }

    /**
     * Method to get Attachmenyt file by Id
     * 
     * @author Pradeep Ravichandran
     * @param helperAttachmentEntity
     * @return
     */
    @PostMapping(value = "/getAttachmentById", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getAttachmentById(@RequestBody HelperAttachmentEntity helperAttachmentEntity) {
        return helpService.getAttachmentId(helperAttachmentEntity);
    }

    /**
     * Method to delete Attachment By Id
     * 
     * @author Pradeep Ravichandran
     * @param helperAttachmentEntity
     * @return
     */
    @PostMapping(value = "/deleteAttachmentById", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity deleteAttachmentById(@RequestBody HelperAttachmentEntity helperAttachmentEntity) {
        return helpService.deleteAttachmentById(helperAttachmentEntity);
    }

}
