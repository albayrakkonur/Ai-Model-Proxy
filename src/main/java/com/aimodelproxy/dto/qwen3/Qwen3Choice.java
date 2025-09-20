package com.aimodelproxy.dto.qwen3;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Qwen3Choice {
    
    @JsonProperty("index")
    private Integer index;
    
    @JsonProperty("message")
    private Qwen3Message message;
    
    @JsonProperty("finish_reason")
    private String finishReason;
    
    public Qwen3Choice() {}
    
    public Qwen3Choice(Integer index, Qwen3Message message, String finishReason) {
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
    
    public Qwen3Message getMessage() {
        return message;
    }
    
    public void setMessage(Qwen3Message message) {
        this.message = message;
    }
    
    public String getFinishReason() {
        return finishReason;
    }
    
    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}