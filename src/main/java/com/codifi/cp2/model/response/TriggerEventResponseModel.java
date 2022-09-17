package com.codifi.cp2.model.response;

public class TriggerEventResponseModel {
    private String triggerId;
    private String triggerName;
    private int filteredMessageCount;
    private int totalMessageCount;

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public int getFilteredMessageCount() {
        return filteredMessageCount;
    }

    public void setFilteredMessageCount(int filteredMessageCount) {
        this.filteredMessageCount = filteredMessageCount;
    }

    public int getTotalMessageCount() {
        return totalMessageCount;
    }

    public void setTotalMessageCount(int totalMessageCount) {
        this.totalMessageCount = totalMessageCount;
    }

}
