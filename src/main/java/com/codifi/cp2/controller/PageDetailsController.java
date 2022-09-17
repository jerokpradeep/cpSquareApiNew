package com.codifi.cp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import com.codifi.cp2.entity.PageDetailsEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.service.PageDetailsService;

@RestController
@RequestMapping("/page")
@CrossOrigin
@Api(tags = "Pages", description = "Page Catalog")
public class PageDetailsController {

    @Autowired
    PageDetailsService pageDetailsService;

    /**
     * method to get all Page Details
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllPageList")
    public ResponseEntity getAllPageList() {
        return pageDetailsService.getAllPageList();
    }

    /**
     * method to save and Update page Details
     * 
     * @author Pradeep Ravichandran
     * @param pageDetailsEntity
     * @return
     */
    @PostMapping(value = "/savePageDetails", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity savePageDetails(@RequestBody PageDetailsEntity pageDetailsEntity) {
        return pageDetailsService.saveAndUpdatePageDetails(pageDetailsEntity);
    }

}
