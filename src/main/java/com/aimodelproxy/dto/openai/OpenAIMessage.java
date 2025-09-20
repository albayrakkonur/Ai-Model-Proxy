package com.aimodelproxy.dto.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenAIMessage {
    
    @JsonProperty("role")
    private String role;
    
    @JsonProperty("content")
    private String content;
    
    @JsonProperty("name")
    private String name;
    
    public OpenAIMessage() {}
    
    public OpenAIMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
    
    public OpenAIMessage(String role, String content, String name) {
        this.role = role;
        this.content = content;
        this.name = name;
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}