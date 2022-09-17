package com.codifi.cp2.model.request;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class RGWFilterModel {
    private Integer displayId = null;
    private String status = null;
    private String assignedTo = null;
    private String productHierarchy = null;
    private String commodityGroup = null;
    private String dcList = null;
    private JSONObject dc = null;
    private String articleType = null;
    private String orderAreaList = null;
    private String articleStatus = null;    
    private String stockPlannerList = null;
    private String postDateFrom = null;
    private String postDateTo = null;  
    private String listingDateFrom = null;
    private String listingDateTo = null; 
    private String triggerId = null;
}