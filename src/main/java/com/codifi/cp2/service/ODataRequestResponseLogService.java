package com.codifi.cp2.service;

import com.codifi.cp2.entity.ODataRequestResponseLogEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.helper.ODataRequestResponseLogHelper;
import com.codifi.cp2.model.request.PaginationModel;
import com.codifi.cp2.model.response.EventLogDataModel;
import com.codifi.cp2.model.response.EventLogModel;
import com.codifi.cp2.repository.ODataRequestResponseLogRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ODataRequestResponseLogService {

    @Autowired
    ODataRequestResponseLogRepository repository;
    @Autowired
    ODataRequestResponseLogHelper requestResponseLogHelper;


    /**
     * method to get event log record by ID
     * 
     * @author Dinesh Kumar
     * @param logEntity
     * @return
     */
    public ResponseEntity findById(ODataRequestResponseLogEntity logEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (logEntity != null && logEntity.getId() != null && logEntity.getId() > 0) {
            Optional<ODataRequestResponseLogEntity> entity = repository.findById(logEntity.getId());
            if (entity.get() != null) {
                EventLogModel logModel = requestResponseLogHelper.populateEventLog(entity.get());
                responseEntity.setResult(logModel);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            } else {
                responseEntity.setReason(MessageConstants.DATA_NULL);
                responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
                responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
            }
        } else {
            responseEntity.setReason(MessageConstants.ID_NULL);
            responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
            responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
        }
        return responseEntity;
    }

      /**
     * method to get event log record by Pagination
     * 
     * @author Dinesh Kumar
     * @param paginationModel
     * @return
     */
    public ResponseEntity getEventLogByPagination(PaginationModel paginationModel) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<ODataRequestResponseLogEntity> respone = repository
                .requestListByPagination(paginationModel.getLimit(), paginationModel.getOffset());
                System.out.println(respone.size());
        if (StringUtil.isListNotNullOrEmpty(respone)) {
            List<EventLogModel> eventLogModels = requestResponseLogHelper.populateEventLogs(respone);
            EventLogDataModel dataModel = new EventLogDataModel();
            int totalCount = repository.getCount();
            dataModel.setTotalCount(totalCount);
            dataModel.setEventLogModel(eventLogModels);
            responseEntity.setResult(dataModel);
            responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
            responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
        } else {
            responseEntity.setReason(MessageConstants.DATA_NULL);
            responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
            responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
        }
        return responseEntity;
    }
}
