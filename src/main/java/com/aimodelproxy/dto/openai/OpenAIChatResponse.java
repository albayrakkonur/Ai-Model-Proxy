package com.aimodelproxy.dto.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OpenAIChatResponse {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("object")
    private String object;
    
    @JsonProperty("created")
    private Long created;
    
    @JsonProperty("model")
    private String model;
    
    @JsonProperty("choices")
    private List<OpenAIChoice> choices;
    
    @JsonProperty("usage")
    private OpenAIUsage usage;
    
    public OpenAIChatResponse() {}
    
    public OpenAIChatResponse(String id, String object, Long created, String model, 
                              List<OpenAIChoice> choices, OpenAIUsage usage) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
        this.usage = usage;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getObject() {
        return object;
    }
    
    public void setObject(String object) {
        this.object = object;
    }
    
    public Long getCreated() {
        return created;
    }
    
    public void setCreated(Long created) {
        this.created = created;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public List<OpenAIChoice> getChoices() {
        return choices;
    }
    
    public void setChoices(List<OpenAIChoice> choices) {
        this.choices = choices;
    }
    
    public OpenAIUsage getUsage() {
        return usage;
    }
    
    public void setUsage(OpenAIUsage usage) {
        this.usage = usage;
    }
}