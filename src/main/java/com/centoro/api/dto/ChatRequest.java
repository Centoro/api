package com.centoro.api.dto;

import java.util.Date;

public class ChatRequest {
    private String clientId;
    private String subject;
    private String priority;
    private String text;
    private Date calendarDate;
    private Long linkedJobId;
    private String staffId;

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Date getCalendarDate() { return calendarDate; }
    public void setCalendarDate(Date calendarDate) { this.calendarDate = calendarDate; }
    public Long getLinkedJobId() { return linkedJobId; }
    public void setLinkedJobId(Long linkedJobId) { this.linkedJobId = linkedJobId; }
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
}
