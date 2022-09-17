package com.codifi.cp2.controller;

import com.codifi.cp2.entity.NoteListEntity;
import com.codifi.cp2.entity.ReceiverGroupDataEntity;
import com.codifi.cp2.model.request.RGWFilterModel;
import com.codifi.cp2.service.RGWService;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/RGW")
@CrossOrigin
@Api(tags = "RGW", description = "Receiver Group Workbench")
public class RGWController {

    @Autowired
    RGWService rgwService;

    /**
     * Method to get All trigger Events by trigger Event type
     * 
     * @author Pradeep Ravichandran
     * @param triggerType // All, ATM, FTM
     * @return
     */
    @GetMapping(value = "/getTriggerEvents/{triggerType}", headers = "Accept=application/json")
    public ResponseEntity<String> getTriggerEvents(@PathVariable String triggerType) {
        return rgwService.getTriggerEvents(triggerType);
    }

    /**
     * Method to get Messages by trigger id.
     * 
     * @author Pradeep Ravichandran
     * @param triggerId // ATM id or FTM id
     * @return message
     * 
     */
    @GetMapping(value = "/messages/{triggerId}", headers = "Accept=application/json")
    public ResponseEntity<String> getMessageById(@PathVariable final String triggerId) {
        return rgwService.getMessageById(triggerId);
    }

    /**
     * Method to Search the Messages.
     * 
     * @author Jayaprakash Ponnusamy
     * @param triggerId // ATM id or FTM id
     * @return message
     * 
     */
    @GetMapping(value = "/messages/{triggerId}/{searchText}", headers = "Accept=application/json")
    public ResponseEntity<String> searchMessage(@PathVariable final String triggerId,
            @PathVariable final String searchText) {
        return rgwService.searchMessage(triggerId, searchText);
    }

    // /**
    // * Method to Filter the Messages.
    // *
    // * @author Jayaprakash Ponnusamy
    // * @param triggerId // ATM id or FTM id
    // * @return message
    // *
    // */
    // @GetMapping(value = "/messages/{triggerId}/filterMessage", headers =
    // "Accept=application/json")
    // public ResponseEntity<String> filterMessage(@PathVariable final String
    // triggerId, @PathVariable final String searchText) {
    // return rgwService.searchMessage(triggerId, searchText);
    // }

    /**
     * Method to get Messages details by message id.
     * 
     * @author Pradeep Ravichandran
     * @param messageId
     * @return message
     * 
     */
    @GetMapping(value = "/getMessageDetailsById/{messageId}", headers = "Accept=application/json")
    public ResponseEntity<String> getMessageDetailsById(@PathVariable String messageId) {
        return rgwService.getMessageDetailsById(messageId);
    }

    /**
     * method to udpate status in RGW
     * 
     * @author Pradeep Ravichandran
     * @param helpEntity
     * @return
     */
    @PostMapping(value = "/updateStatus", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> updateStatus(@RequestBody ReceiverGroupDataEntity rgdEntity) {
        return rgwService.updateStatus(rgdEntity);
    }

    /**
     * Method to get All Display ID List
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllDisplayIdList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllDisplayIdList() {
        return rgwService.getAllDisplayIdList();
    }

    /**
     * Method to get All Assign user to assign the messages
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllAssignUsers", headers = "Accept=application/json")
    public ResponseEntity<String> getAllAssignUsers() {
        return rgwService.getAllAssignUsers();
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
        return rgwService.saveNotes(noteListEntity);
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
        return rgwService.deleteNotes(notesId);
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
        return rgwService.getNotesById(messageId);
    }

    /**
     * method to edit messages in RGW
     * 
     * @author Pradeep Ravichandran
     * @param rgdEntity
     * @return
     */
    @PostMapping(value = "/editMessages", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> editMessages(@RequestParam("data") String rgdEntity,
            @RequestParam(value = "file", required = false) final MultipartFile file) {
        return rgwService.editMessages(file, rgdEntity);
    }

    /**
     * method to get all filter datas
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getFilterDatas", headers = "Accept=application/json")
    public ResponseEntity<String> getFilterDatas() {
        return rgwService.getFilterDatas();
    }

    /**
     * method to get RGW data based on filters
     * 
     * @author Dinesh Kumar
     * @return
     */
    @PostMapping(value = "/getRGWFilteredData", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> getRGWFilteredData(@RequestBody RGWFilterModel model) {
        return rgwService.getRGWFilteredData(model);
    }

}
