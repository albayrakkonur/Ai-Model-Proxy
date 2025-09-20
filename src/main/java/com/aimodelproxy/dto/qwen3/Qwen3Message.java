package com.aimodelproxy.dto.qwen3;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Qwen3Message {
    
    @JsonProperty("role")
    private String role;
    
    @JsonProperty("content")
    private String content;
    
    public Qwen3Message() {}
    
    public Qwen3Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
    
    // Getters and setters
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}