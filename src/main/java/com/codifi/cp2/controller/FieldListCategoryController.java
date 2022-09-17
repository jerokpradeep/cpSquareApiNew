package com.codifi.cp2.controller;

import com.codifi.cp2.entity.FieldListCategoryEntity;
import com.codifi.cp2.service.FieldListCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/fieldListCategory")
@CrossOrigin
@Api(tags = "FLM", description = "Field List Category Maintenance")
public class FieldListCategoryController {
    @Autowired
    FieldListCategoryService fieldListCategoryService;

    /**
     * Method To get All Field List Category List
     * 
     * @author Pradeep Ravichandran
     * @return List of Field List Category List
     */
    @GetMapping(value = "/getFieldListCategoryList", headers = "Accept=application/json")
    public ResponseEntity<String> getFieldListCategoryList() {
        return fieldListCategoryService.getFieldListCategoryList();
    }

    /**
     * Method to Delete Field List Category Record
     * 
     * @author Pradeep Ravichandran
     * @param fieldListCategoryEntity
     * @return
     */
    @PostMapping(value = "/deleteFieldListCategory", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> deleteFieldListCategory(
            @RequestBody FieldListCategoryEntity fieldListCategoryEntity) {
        return fieldListCategoryService.deleteFieldListCategory(fieldListCategoryEntity);
    }

    /**
     * Method To get All Trigger Field List
     * 
     * @author Pradeep Ravichandran
     * @return List of Trigger Field List
     */
    @GetMapping(value = "/getAllTriggerFieldList", headers = "Accept=application/json")
    public ResponseEntity<String> getAllTriggerFieldList() {
        return fieldListCategoryService.getAllTriggerFieldList();
    }

    /**
     * Method to save and update Field List Category
     * 
     * @author Pradeep
     * @param flcEntity
     * @return
     */
    @PostMapping(value = "/saveFieldListCategoryDetail", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveFieldListCategoryDetail(@RequestBody FieldListCategoryEntity flcEntity) {
        if (flcEntity.getId() == null) {
            return fieldListCategoryService.saveFieldListCategoryDetail(flcEntity);
        } else {
            return fieldListCategoryService.updateFieldListCategoryDetail(flcEntity);
        }
    }

    /**
     * Method to save Field List Name
     * 
     * @author Pradeep
     * @param flcEntity
     * @return
     */
    @PostMapping(value = "/saveFieldListName", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> saveFieldListName(@RequestBody FieldListCategoryEntity flcEntity) {
        return fieldListCategoryService.saveFieldListName(flcEntity);
    }

    /**
     * Method to get Field List Category By id
     *
     * @author Pradeep Ravichandran
     * @param id
     * @return
     */
    @GetMapping(value = "/getFLCDetailsById/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> getFLCDetailsById(@PathVariable int id) {
        return fieldListCategoryService.getFLCDetailsById(id);
    }

    /**
     * Method to get All Category List by Trigger Events
     * 
     * @author Pradeep Ravichandran
     * @return
     */

    @GetMapping("/categoryList/{triggerEvent}")
    public ResponseEntity<String> getCategoryListByTriggerEvent(@PathVariable final String triggerEvent) {
        return fieldListCategoryService.getCategoryListByTriggerEvent(triggerEvent);
    }
}
