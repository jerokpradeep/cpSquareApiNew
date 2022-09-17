package com.codifi.cp2.model.response;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class EventLogDataModel {
    private int totalCount;
    private List<EventLogModel> eventLogModel;
}
