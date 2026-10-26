package com.centoro.api.dto;

import java.util.List;

public class SendSmsCampaignRequest {
    private Long providerId;
    private String message;
    private String recipient;
    private Integer recipientsCount;
    private List<String> recipients;
    private String fromLine;

    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public Integer getRecipientsCount() { return recipientsCount; }
    public void setRecipientsCount(Integer recipientsCount) { this.recipientsCount = recipientsCount; }
    public List<String> getRecipients() { return recipients; }
    public void setRecipients(List<String> recipients) { this.recipients = recipients; }
    public String getFromLine() { return fromLine; }
    public void setFromLine(String fromLine) { this.fromLine = fromLine; }
}