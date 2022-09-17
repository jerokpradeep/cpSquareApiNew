package com.codifi.cp2.controller;

import com.codifi.cp2.entity.ODataRequestResponseLogEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.model.request.PaginationModel;
import com.codifi.cp2.service.ODataRequestResponseLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/eventLogBackEnd")
@CrossOrigin
@Api(tags = "BackEndEventLogs", description = "Request and Response Catalog for BackEnd")
public class ODataRequestResponseLogController {
    
    @Autowired
    ODataRequestResponseLogService service;

    /**
     * method to get event log record by ID
     * 
     * @author Dinesh Kumar
     * @param logEntity
     * @return
     */
    @PostMapping(value = "/findById", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity findById(@RequestBody ODataRequestResponseLogEntity logEntity) {
        ResponseEntity responseEntity = service.findById(logEntity);
        return responseEntity;
    }

    /**
     * method to get event log record by Pagination
     * 
     * @author Dinesh Kumar
     * @param paginationModel
     * @return
     */
    @PostMapping(value = "/getEventLogs", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getEventLogByPagination(@Validated @RequestBody PaginationModel paginationModel) {
        ResponseEntity responseEntity = service.getEventLogByPagination(paginationModel);
        return responseEntity;
    }
}
