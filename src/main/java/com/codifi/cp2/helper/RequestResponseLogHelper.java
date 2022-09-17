package com.codifi.cp2.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.codifi.cp2.entity.ODataRequestResponseLogEntity;
import com.codifi.cp2.entity.RequestResponseLogEntity;
import com.codifi.cp2.model.response.EventLogModel;
import com.codifi.cp2.util.DateUtil;

@Service
public class RequestResponseLogHelper {
    /**
     * method to populate Event log for UI
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public List<EventLogModel> populateEventLogs(List<RequestResponseLogEntity> requestResponseLogEntities) {
        List<EventLogModel> eventLogModels = new ArrayList<EventLogModel>();
        for (RequestResponseLogEntity requestResponseLogEntity : requestResponseLogEntities) {
            EventLogModel eventLogModel = new EventLogModel();
            eventLogModel.setId(requestResponseLogEntity.getId());
            eventLogModel
                    .setEventDate(DateUtil.formatDate(requestResponseLogEntity.getCreatedOn(), DateUtil.DDMMYYYYHHMM));
            eventLogModel.setDescription(requestResponseLogEntity.getUrl());
            if (requestResponseLogEntity.getStatus() == 200) {
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

    /**
     * method to populate Event log by id for UI
     * 
     * @author Pradeep Ravichandran
     * @return
     */
    public EventLogModel populateEventLog(RequestResponseLogEntity requestResponseLogEntity) {
        EventLogModel eventLogModel = new EventLogModel();
        eventLogModel.setId(requestResponseLogEntity.getId());
        eventLogModel.setEventDate(DateUtil.formatDate(requestResponseLogEntity.getCreatedOn(), DateUtil.DDMMYYYYHHMM));
        eventLogModel.setDescription(requestResponseLogEntity.getUrl());
        if (requestResponseLogEntity.getStatus() == 200) {
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
}
