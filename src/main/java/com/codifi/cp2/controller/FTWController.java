package com.codifi.cp2.controller;

import com.codifi.cp2.entity.FreeTextMessageEntity;
import com.codifi.cp2.entity.NoteListEntity;
import com.codifi.cp2.service.FTWService;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/FTW")
@CrossOrigin
@Api(tags = "FTW", description = "Free Text Workbench")
public class FTWController {
    @Autowired
    FTWService ftwService;

    /**
     * Method to get All Status list
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllStatusList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllStatusList() {
        return ftwService.getAllStatusList();
    }

    /**
     * Method to get All Display ID List
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllDisplayIdList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllDisplayIdList() {
        return ftwService.getAllDisplayIdList();
    }

    /**
     * Method to get All Display ID List
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllCreatedByList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllCreatedByList() {
        return ftwService.getAllCreatedByList();
    }

    /**
     * Method to get All Approved By List
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllApprovedByList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllApprovedByList() {
        return ftwService.getAllApprovedByList();
    }

    /**
     * Method to get All FTW Trigger Events
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getFTWTriggerList", headers = "Accept=application/json")
    public ResponseEntity<String> getFTWTriggerList() {
        return ftwService.getFTWTriggerList();
    }

    /**
     * Method to get Messages by trigger id.
     * 
     * @author Pradeep Ravichandran
     * @param triggerId // FTM id
     * @return message
     * 
     */
    @GetMapping(value = "/messages/{triggerId}", headers = "Accept=application/json")
    public ResponseEntity<String> getMessageById(@PathVariable final String triggerId) {
        return ftwService.getMessageById(triggerId, "");
    }

    /**
     * Method to get Messages by trigger id.
     * 
     * @author Jayaprakash Ponnusamy
     * @param triggerId  // FTM id
     * @param searchText
     * @return message
     * 
     */
    @GetMapping(value = "/messages/{triggerId}/{searchText}", headers = "Accept=application/json")
    public ResponseEntity<String> searchMessage(@PathVariable final String triggerId,
            @PathVariable final String searchText) {
        return ftwService.searchMessage(triggerId, searchText);
    }

    /**
     * Method to get Messages by trigger id.
     * 
     * @author Pradeep Ravichandran
     * @param triggerId // FTM id
     * @return message
     * 
     */
    @GetMapping(value = "/pendingApprovals/{triggerId}", headers = "Accept=application/json")
    public ResponseEntity<String> getApprovePendingMessages(@PathVariable final String triggerId) {
        return ftwService.getMessageById(triggerId, MessageConstants.CONST_STATUS_AWAITING_APPROVAL);
    }

    /**
     * Method to update send for Approvals
     * 
     * @author Pradeep Ravichandran
     * @param ftwEntity
     * @return
     */
    @PostMapping(value = "/sendForApproval", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> sendForApproval(@RequestBody FreeTextMessageEntity ftwEntity) {
        return ftwService.sendForApproval(ftwEntity);

    }

    /**
     * Method to delete Messages
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    @GetMapping(value = "/deleteMessage/{messageId}", headers = "Accept=application/json")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId) {
        return ftwService.deleteMessage(messageId);
    }

    /**
     * Method to clone Message by message Id
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    @GetMapping(value = "/cloneMessage/{messageId}", headers = "Accept=application/json")
    public ResponseEntity<String> cloneMessage(@PathVariable String messageId) {
        return ftwService.cloneMessage(messageId);
    }

    /**
     * Method to View Message by message Id
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    @GetMapping(value = "/viewMessage/{messageId}", headers = "Accept=application/json")
    public ResponseEntity<String> viewMessage(@PathVariable String messageId) {
        return ftwService.viewMessage(messageId);
    }

    /**
     * method to Save Notes in RGW
     * 
     * @author Pradeep Ravichandran
     * @param noteListEntity
     * @return
     */
    @PostMapping(value = "/saveNotes", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveNotes(@RequestBody NoteListEntity noteListEntity) {
        return ftwService.saveNotes(noteListEntity);
    }

    /**
     * method to delete Notes in RGW
     * 
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    @GetMapping(value = "/deleteNotes/{notesId}", headers = "Accept=application/json")
    public ResponseEntity<String> deleteNoteById(@PathVariable int notesId) {
        return ftwService.deleteNotes(notesId);
    }

    /**
     * method to get Notes in RGW for Message id
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    @GetMapping(value = "/getNotesById/{messageId}", headers = "Accept=application/json")
    public ResponseEntity<String> getNotesById(@PathVariable String messageId) {
        return ftwService.getNotesById(messageId);
    }

    /**
     * method to get Trigger Id and Trigger Name
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getTriggerIdAndName", headers = "Accept=application/json")
    public ResponseEntity<String> getTriggerIdAndName() {
        return ftwService.getTriggerIdAndName();
    }

    /**
     * method to edit messages
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    @GetMapping(value = "/editMessage/{messageId}/{screen}", headers = "Accept=application/json")
    public ResponseEntity<String> editMessage(@PathVariable String messageId, @PathVariable String screen) {
        return ftwService.editMessage(messageId, screen);
    }

    /**
     * method to get attachment by message ID
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return
     */
    @GetMapping(value = "/getAttachment/{messageId}", headers = "Accept=application/json")
    public ResponseEntity<String> getAttachment(@PathVariable String messageId) {
        return ftwService.getAttachment(messageId);
    }

    /**
     * method to save message Details
     * 
     * @author Pradeep Ravichandran
     * @param freeTextMessageEntity
     * @return
     */
    @PostMapping(value = "/saveEditMessageDetails", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> saveEditMessageDetails(
            @RequestParam(value = "file", required = false) final MultipartFile file,
            @RequestParam(value = "data") String ftmEntity) {
        return ftwService.saveEditMessageDetails(file, ftmEntity);
    }

    /**
     * method to save Trigger
     * 
     * @author Pradeep Ravichandran
     * @param freeTextMessageEntity
     * @return
     */
    @PostMapping(value = "/saveTrigger", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveTrigger(@RequestBody FreeTextMessageEntity ftmEntity) {
        return ftwService.saveTrigger(ftmEntity);
    }

    // /**
    // * method to get Distribution List in RGW for trigger id
    // *
    // * @author Pradeep Ravichandran
    // * @param triggerID
    // * @return
    // */
    // @GetMapping(value = "/getDistributionList/{triggerID}", headers =
    // "Accept=application/json")
    // public ResponseEntity<String> getDistributionList(@PathVariable String
    // triggerID) {
    // return ftwService.getDistributionList(triggerID);
    // }

    /**
     * method to save Distribution List
     * 
     * @author Pradeep Ravichandran
     * @param freeTextMessageEntity
     * @return
     */
    @PostMapping(value = "/saveDistributionList", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveDistributionList(@RequestBody FreeTextMessageEntity ftmEntity) {
        return ftwService.saveDistributionList(ftmEntity);
    }
}
