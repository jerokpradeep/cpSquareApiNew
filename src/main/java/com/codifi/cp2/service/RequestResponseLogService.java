package com.codifi.cp2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.RequestResponseLogEntity;
import com.codifi.cp2.entity.ResponseEntity;
import com.codifi.cp2.helper.RequestResponseLogHelper;
import com.codifi.cp2.model.request.PaginationModel;
import com.codifi.cp2.model.response.EventLogDataModel;
import com.codifi.cp2.model.response.EventLogModel;
import com.codifi.cp2.repository.RequestResponseLogRepository;
import com.codifi.cp2.util.MessageConstants;
import com.codifi.cp2.util.StringUtil;

@Service
public class RequestResponseLogService {

    @Autowired
    RequestResponseLogRepository requestResponseLogRepository;
    @Autowired
    RequestResponseLogHelper requestResponseLogHelper;

    /**
     * method to get all event log records
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public ResponseEntity getAllEvents() {
        ResponseEntity responseEntity = new ResponseEntity();
        List<RequestResponseLogEntity> requestResponseLogEntities = requestResponseLogRepository.findAll();
        if (StringUtil.isListNotNullOrEmpty(requestResponseLogEntities)) {
            List<EventLogModel> eventLogModels = requestResponseLogHelper.populateEventLogs(requestResponseLogEntities);
            responseEntity.setResult(eventLogModels);
            responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
            responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
        } else {
            responseEntity.setReason(MessageConstants.DATA_NULL);
            responseEntity.setStatus(MessageConstants.SUCCESS_STATUS);
            responseEntity.setMessage(MessageConstants.SUCCESS_MSG);
        }
        return responseEntity;
    }

    /**
     * method to get event log record by ID
     * 
     * @author Pradeep Ravichandran
     * @param logEntity
     * @return
     */
    public ResponseEntity getEventLogById(RequestResponseLogEntity logEntity) {
        ResponseEntity responseEntity = new ResponseEntity();
        if (logEntity != null && logEntity.getId() != null && logEntity.getId() > 0) {
            Optional<RequestResponseLogEntity> entity = requestResponseLogRepository.findById(logEntity.getId());
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
     * @author Pradeep Ravichandran
     * @param paginationModel
     * @return
     */
    public ResponseEntity getEventLogByPagination(PaginationModel paginationModel) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<RequestResponseLogEntity> requestResponseLogEntities = requestResponseLogRepository
                .requestListByPagination(paginationModel.getLimit(), paginationModel.getOffset());
        if (StringUtil.isListNotNullOrEmpty(requestResponseLogEntities)) {
            List<EventLogModel> eventLogModels = requestResponseLogHelper.populateEventLogs(requestResponseLogEntities);
            EventLogDataModel dataModel = new EventLogDataModel();
            int totalCount = requestResponseLogRepository.getCount();
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
