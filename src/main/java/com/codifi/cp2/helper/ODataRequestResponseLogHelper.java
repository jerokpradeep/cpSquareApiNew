package com.codifi.cp2.helper;

import java.util.ArrayList;
import java.util.List;

import com.codifi.cp2.entity.ODataRequestResponseLogEntity;
import com.codifi.cp2.model.response.EventLogModel;
import com.codifi.cp2.util.DateUtil;

import org.springframework.stereotype.Service;

@Service
public class ODataRequestResponseLogHelper {

    
    /**
     * method to populate Event log by id for Back End
     * 
     * @author Dinesh Kumar
     * @return
     */
    public EventLogModel populateEventLog(ODataRequestResponseLogEntity requestResponseLogEntity) {
        EventLogModel eventLogModel = new EventLogModel();
        eventLogModel.setId(requestResponseLogEntity.getId());
        eventLogModel.setEventDate(DateUtil.formatDate(requestResponseLogEntity.getCreatedOn(), DateUtil.DDMMYYYYHHMM));
        eventLogModel.setDescription(requestResponseLogEntity.getUrl());
        if (requestResponseLogEntity.getStatus().equalsIgnoreCase("Success")) {
            eventLogModel.setStatus("Success");
        } else {
            eventLogModel.setStatus("Error");
        }
        eventLogModel.setUserId(requestResponseLogEntity.getUserId());
        eventLogModel.setSource(requestResponseLogEntity.getUrl());
        eventLogModel.setUserAgent(requestResponseLogEntity.getUserAgent());
        eventLogModel.setRequest(requestResponseLogEntity.getRequestInput());
        eventLogModel.setResponse(requestResponseLogEntity.getResponseData());
        eventLogModel.setDeviceIp(requestResponseLogEntity.getDeviceIp());
        return eventLogModel;
    }

     /**
     * method to populate Event log for Back end
     * 
     * @author Dinesh Kumar
     * @return
     */
    public List<EventLogModel> populateEventLogs(List<ODataRequestResponseLogEntity> entity) {
        List<EventLogModel> eventLogModels = new ArrayList<EventLogModel>();
        for (ODataRequestResponseLogEntity requestResponseLogEntity : entity) {
            EventLogModel eventLogModel = new EventLogModel();
            eventLogModel.setId(requestResponseLogEntity.getId());
            eventLogModel
                    .setEventDate(DateUtil.formatDate(requestResponseLogEntity.getCreatedOn(), DateUtil.DDMMYYYYHHMM));
            eventLogModel.setDescription(requestResponseLogEntity.getUrl());
            if (requestResponseLogEntity.getStatus().equalsIgnoreCase("Success")) {
                eventLogModel.setStatus("Success");
            } else {
                eventLogModel.setStatus("Error");
            }
            eventLogModel.setUserId(requestResponseLogEntity.getUserId());
            eventLogModel.setSource(requestResponseLogEntity.getUrl());
            eventLogModels.add(eventLogModel);
        }
        return eventLogModels;
    }

    
}
