package com.codifi.cp2.controller;

import com.codifi.cp2.entity.ReceiverGroupMaintenanceEntity;
import com.codifi.cp2.entity.ReceiverGroupUserEntity;
import com.codifi.cp2.entity.StreamTriggerEventEntity;
import com.codifi.cp2.service.ReceiverGroupMaintenanceService;

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
@RequestMapping("/receiverGroup")
@CrossOrigin
@Api(tags = "RGM", description = "Receiver Group Maintenance")
public class ReceiverGroupMaintenanceController {
    @Autowired
    ReceiverGroupMaintenanceService receiverGroupMaintenanceService;

    /**
     * Method To get All Receiver Group Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @return List of Receiver Group Maintenance List
     */

    @GetMapping(value = "/getReceiverGroupDetails", headers = "Accept=application/json")
    public ResponseEntity<String> getReceiverGroupDetails() {
        return receiverGroupMaintenanceService.getReceiverGroupDetails();

    }

    /**
     * Method to update Receiver Group Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @param rgmEntity
     * @return
     */
    @PostMapping(value = "/updateRGMStatus", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> updateRGMStatus(@RequestBody ReceiverGroupMaintenanceEntity rgmEntity) {
        return receiverGroupMaintenanceService.updateRGMStatus(rgmEntity);

    }

    /**
     * Method to Delete Receiver Group Maintenance List
     * 
     * @author Pradeep Ravichandran
     * @param rgmEntity
     * @return
     */
    @PostMapping(value = "/deleteRGM", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteATM(@RequestBody ReceiverGroupMaintenanceEntity rgmEntity) {
        return receiverGroupMaintenanceService.deleteRGM(rgmEntity);
    }

    /**
     * Method to get Receiver Group Maintenance Data Field By id
     *
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    @GetMapping(value = "/getRGMDetailsById/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> getRGMDetailsById(@PathVariable int id) {
        return receiverGroupMaintenanceService.getRGMDetailsById(id);
    }

    /**
     * Method to get Receiver Group Maintenance Users Details By id
     *
     * @author Jayaprakash Ponnusamy
     * @param id
     * @return
     */
    @GetMapping(value = "/getRGMUserDetailsById/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> getRGMUserDetailsById(@PathVariable int id) {
        return receiverGroupMaintenanceService.getRGMUserDetailsById(id);
    }

    /**
     * Method to save and update Receiver Group Details
     * 
     * @author Pradeep Ravichandran
     * @param rgmEntity
     * @return
     */
    @PostMapping(value = "/saveReceiverGroupDetail", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveReceiverGroupDetail(@RequestBody ReceiverGroupMaintenanceEntity rgmEntity) {
        if (rgmEntity.getId() == null) {
            return receiverGroupMaintenanceService.saveReceiverGroupDetail(rgmEntity);
        } else {
            return receiverGroupMaintenanceService.updateReceiverGroupDetail(rgmEntity);
        }
    }

    /**
     * Method To get All Article Name List
     * 
     * @author Pradeep Ravichandran
     * @return List of Article Name List
     */

    @GetMapping(value = "/getAllArticleNameList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllArticleNameList() {
        return receiverGroupMaintenanceService.getAllArticleNameList();
    }

    /**
     * Method To get All Dc List
     * 
     * @author Pradeep Ravichandran
     * @return List of Article Name List
     */
    @GetMapping(value = "/getAllDCList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllDCList() {
        return receiverGroupMaintenanceService.getAllDCList();
    }

    /**
     * Method To get All Trigger Name List
     * 
     * @author JP
     * @return List of Article Name List
     */
    @GetMapping(value = "/getAllTriggerNameList/{triggerType}", headers = "Accept=application/json")
    public ResponseEntity<String> getAllTriggerNameList(@PathVariable String triggerType) {
        return receiverGroupMaintenanceService.getAllTriggerNameList(triggerType);
    }

    /**
     * Method to save User Details
     * 
     * @author Pradeep
     * @param receiverGroupUserEntity
     * @return
     */
    @PostMapping(value = "/saveReceiverUser", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveReceiverUser(@RequestBody ReceiverGroupUserEntity receiverGroupUserEntity) {
        return receiverGroupMaintenanceService.saveReceiverUser(receiverGroupUserEntity);
    }

    /**
     * Method to save Trigger Details
     * 
     * @author Pradeep
     * @param streamTriggerEventEntity
     * @return
     */
    @PostMapping(value = "/saveTriggerEvent", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveTriggerEvent(@RequestBody StreamTriggerEventEntity streamTriggerEventEntity) {
        return receiverGroupMaintenanceService.saveTriggerEvent(streamTriggerEventEntity);
    }

    /**
     * Method To get All Activated ATM trigger names
     * 
     * @author Pradeep Ravichandran
     * @return List of ATM trigger names
     */

    @GetMapping(value = "/getAutomatedTriggerDetails", headers = "Accept=application/json")
    public ResponseEntity<String> getAutomatedTriggerDetails() {
        return receiverGroupMaintenanceService.getAutomatedTriggerDetails();

    }

    /**
     * Method To get All Article Type List
     * 
     * @author Pradeep Ravichandran
     * @return List of Article Type List
     */

    @GetMapping(value = "/getArticleTypeLists", headers = "Accept=application/json")
    public ResponseEntity<String> getArticleTypeLists() {
        return receiverGroupMaintenanceService.getArticleTypeLists();

    }

    /**
     * Method To get Mapped Free Text Details
     * 
     * @author Pradeep Ravichandran
     * @return List of Free Text List
     */
    @GetMapping(value = "/getMappedFreeTextList/{receiverID}", headers = "Accept=application/json")
    public ResponseEntity<String> getMappedFreeTextList(@PathVariable String receiverID) {
        return receiverGroupMaintenanceService.getMappedFreeTextList(receiverID);
    }

    /**
     * Method To get Receiver Details by userID
     * 
     * @author Pradeep Ravichandran
     * @return List of Receiver Group List
     */
    @GetMapping(value = "/getRecieverListByUser/{emailId}", headers = "Accept=application/json")
    public ResponseEntity<String> getRecieverListByUser(@PathVariable String emailId) {
        return receiverGroupMaintenanceService.getRecieverListByUser(emailId);
    }
}
