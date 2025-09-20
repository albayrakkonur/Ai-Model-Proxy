package com.aimodelproxy.dto.qwen3;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Qwen3ChatResponse {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("object")
    private String object;
    
    @JsonProperty("created")
    private Long created;
    
    @JsonProperty("model")
    private String model;
    
    @JsonProperty("choices")
    private List<Qwen3Choice> choices;
    
    @JsonProperty("usage")
    private Qwen3Usage usage;
    
    public Qwen3ChatResponse() {}
    
    public Qwen3ChatResponse(String id, String object, Long created, String model, 
                             List<Qwen3Choice> choices, Qwen3Usage usage) {
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
    
    public List<Qwen3Choice> getChoices() {
        return choices;
    }
    
    public void setChoices(List<Qwen3Choice> choices) {
        this.choices = choices;
    }
    
    public Qwen3Usage getUsage() {
        return usage;
    }
    
    public void setUsage(Qwen3Usage usage) {
        this.usage = usage;
    }
}