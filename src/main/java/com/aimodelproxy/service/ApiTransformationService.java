package com.aimodelproxy.service;

import com.aimodelproxy.dto.openai.*;
import com.aimodelproxy.dto.qwen3.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApiTransformationService {
    
    /**
     * Transform OpenAI chat request to Qwen3 format
     */
    public Qwen3ChatRequest transformToQwen3Request(OpenAIChatRequest openAIRequest) {
        Qwen3ChatRequest qwen3Request = new Qwen3ChatRequest();
        
        // Map model - use a default Qwen3 model
        qwen3Request.setModel("qwen-turbo");
        
        // Transform messages
        if (openAIRequest.getMessages() != null) {
            List<Qwen3Message> qwen3Messages = openAIRequest.getMessages().stream()
                    .map(this::transformToQwen3Message)
                    .collect(Collectors.toList());
            qwen3Request.setMessages(qwen3Messages);
        }
        
        // Map parameters
        qwen3Request.setTemperature(openAIRequest.getTemperature());
        qwen3Request.setMaxTokens(openAIRequest.getMaxTokens());
        qwen3Request.setTopP(openAIRequest.getTopP());
        qwen3Request.setStream(openAIRequest.getStream());
        
        return qwen3Request;
    }
    
    /**
     * Transform Qwen3 chat response to OpenAI format
     */
    public OpenAIChatResponse transformToOpenAIResponse(Qwen3ChatResponse qwen3Response, String originalModel) {
        OpenAIChatResponse openAIResponse = new OpenAIChatResponse();
        
        // Map basic fields
        openAIResponse.setId(qwen3Response.getId() != null ? qwen3Response.getId() : generateResponseId());
        openAIResponse.setObject("chat.completion");
        openAIResponse.setCreated(qwen3Response.getCreated() != null ? qwen3Response.getCreated() : System.currentTimeMillis() / 1000);
        openAIResponse.setModel(originalModel); // Return the original model requested by client
        
        // Transform choices
        if (qwen3Response.getChoices() != null) {
            List<OpenAIChoice> openAIChoices = qwen3Response.getChoices().stream()
                    .map(this::transformToOpenAIChoice)
                    .collect(Collectors.toList());
            openAIResponse.setChoices(openAIChoices);
        }
        
        // Transform usage
        if (qwen3Response.getUsage() != null) {
            OpenAIUsage openAIUsage = transformToOpenAIUsage(qwen3Response.getUsage());
            openAIResponse.setUsage(openAIUsage);
        }
        
        return openAIResponse;
    }
    
    private Qwen3Message transformToQwen3Message(OpenAIMessage openAIMessage) {
        return new Qwen3Message(openAIMessage.getRole(), openAIMessage.getContent());
    }
    
    private OpenAIChoice transformToOpenAIChoice(Qwen3Choice qwen3Choice) {
        OpenAIMessage openAIMessage = new OpenAIMessage(
                qwen3Choice.getMessage().getRole(),
                qwen3Choice.getMessage().getContent()
        );
        
        return new OpenAIChoice(
                qwen3Choice.getIndex(),
                openAIMessage,
                qwen3Choice.getFinishReason()
        );
    }
    
    private OpenAIUsage transformToOpenAIUsage(Qwen3Usage qwen3Usage) {
        return new OpenAIUsage(
                qwen3Usage.getPromptTokens(),
                qwen3Usage.getCompletionTokens(),
                qwen3Usage.getTotalTokens()
        );
    }
    
    private String generateResponseId() {
        return "chatcmpl-" + UUID.randomUUID().toString().replace("-", "");
    }
}