package com.codifi.cp2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

import com.codifi.cp2.entity.RequestResponseLogEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.model.request.PaginationModel;
import com.codifi.cp2.service.RequestResponseLogService;

@RestController
@RequestMapping("/eventLog")
@CrossOrigin
@Api(tags = "EventLogs", description = "Request and Response Catalog")
public class RequestResponseLogController {
    @Autowired
    RequestResponseLogService responseLogService;

    /**
     * method to get all event log records
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    @GetMapping(value = "/getAllEvents")
    public ResponseEntity getAllEvents() {
        return responseLogService.getAllEvents();
    }

    /**
     * method to get event log record by ID
     * 
     * @author Pradeep Ravichandran
     * @param logEntity
     * @return
     */
    @PostMapping(value = "/getEventLogById", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getEventLogById(@RequestBody RequestResponseLogEntity logEntity) {
        ResponseEntity responseEntity = responseLogService.getEventLogById(logEntity);
        return responseEntity;
    }

    /**
     * method to get event log record by Pagination
     * 
     * @author Pradeep Ravichandran
     * @param paginationModel
     * @return
     */
    @PostMapping(value = "/getEventLogs", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getEventLogByPagination(@Validated @RequestBody PaginationModel paginationModel) {
        ResponseEntity responseEntity = responseLogService.getEventLogByPagination(paginationModel);
        return responseEntity;
    }

}
