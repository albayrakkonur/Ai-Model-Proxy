package com.aimodelproxy.dto.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenAIChoice {
    
    @JsonProperty("index")
    private Integer index;
    
    @JsonProperty("message")
    private OpenAIMessage message;
    
    @JsonProperty("finish_reason")
    private String finishReason;
    
    public OpenAIChoice() {}
    
    public OpenAIChoice(Integer index, OpenAIMessage message, String finishReason) {
        this.index = index;
        this.message = message;
        this.finishReason = finishReason;
    }
    
    // Getters and setters
    public Integer getIndex() {
        return index;
    }
    
    public void setIndex(Integer index) {
        this.index = index;
    }
    
    public OpenAIMessage getMessage() {
        return message;
    }
    
    public void setMessage(OpenAIMessage message) {
        this.message = message;
    }
    
    public String getFinishReason() {
        return finishReason;
    }
    
    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}