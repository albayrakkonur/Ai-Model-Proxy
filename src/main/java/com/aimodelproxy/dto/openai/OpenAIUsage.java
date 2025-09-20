package com.aimodelproxy.dto.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenAIUsage {
    
    @JsonProperty("prompt_tokens")
    private Integer promptTokens;
    
    @JsonProperty("completion_tokens")
    private Integer completionTokens;
    
    @JsonProperty("total_tokens")
    private Integer totalTokens;
    
    public OpenAIUsage() {}
    
    public OpenAIUsage(Integer promptTokens, Integer completionTokens, Integer totalTokens) {
        this.promptTokens = promptTokens;
        this.completionTokens = completionTokens;
        this.totalTokens = totalTokens;
    }
    
    // Getters and setters
    public Integer getPromptTokens() {
        return promptTokens;
    }
    
    public void setPromptTokens(Integer promptTokens) {
        this.promptTokens = promptTokens;
    }
    
    public Integer getCompletionTokens() {
        return completionTokens;
    }
    
    public void setCompletionTokens(Integer completionTokens) {
        this.completionTokens = completionTokens;
    }
    
    public Integer getTotalTokens() {
        return totalTokens;
    }
    
    public void setTotalTokens(Integer totalTokens) {
        this.totalTokens = totalTokens;
    }
}