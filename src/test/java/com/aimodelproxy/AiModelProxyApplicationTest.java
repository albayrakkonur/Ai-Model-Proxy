package com.aimodelproxy;

import com.aimodelproxy.dto.openai.*;
import com.aimodelproxy.dto.qwen3.*;
import com.aimodelproxy.service.ApiTransformationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiModelProxyApplicationTest {

    @Test
    void contextLoads() {
        // Verify Spring context loads successfully
    }

    @Test
    void testApiTransformation() {
        ApiTransformationService transformationService = new ApiTransformationService();
        
        // Create test OpenAI request
        OpenAIChatRequest openAIRequest = new OpenAIChatRequest();
        openAIRequest.setModel("gpt-3.5-turbo");
        openAIRequest.setTemperature(0.7);
        openAIRequest.setMaxTokens(100);
        
        OpenAIMessage userMessage = new OpenAIMessage("user", "Hello, how are you?");
        openAIRequest.setMessages(Arrays.asList(userMessage));
        
        // Transform to Qwen3 format
        Qwen3ChatRequest qwen3Request = transformationService.transformToQwen3Request(openAIRequest);
        
        // Verify transformation
        assertNotNull(qwen3Request);
        assertEquals("qwen-turbo", qwen3Request.getModel());
        assertEquals(0.7, qwen3Request.getTemperature());
        assertEquals(100, qwen3Request.getMaxTokens());
        assertNotNull(qwen3Request.getMessages());
        assertEquals(1, qwen3Request.getMessages().size());
        assertEquals("user", qwen3Request.getMessages().get(0).getRole());
        assertEquals("Hello, how are you?", qwen3Request.getMessages().get(0).getContent());
    }
    
    @Test
    void testResponseTransformation() {
        ApiTransformationService transformationService = new ApiTransformationService();
        
        // Create test Qwen3 response
        Qwen3ChatResponse qwen3Response = new Qwen3ChatResponse();
        qwen3Response.setId("test-id");
        qwen3Response.setModel("qwen-turbo");
        qwen3Response.setCreated(System.currentTimeMillis() / 1000);
        
        Qwen3Message assistantMessage = new Qwen3Message("assistant", "I'm doing well, thank you!");
        Qwen3Choice choice = new Qwen3Choice(0, assistantMessage, "stop");
        qwen3Response.setChoices(Arrays.asList(choice));
        
        Qwen3Usage usage = new Qwen3Usage(10, 8, 18);
        qwen3Response.setUsage(usage);
        
        // Transform to OpenAI format
        OpenAIChatResponse openAIResponse = transformationService.transformToOpenAIResponse(qwen3Response, "gpt-3.5-turbo");
        
        // Verify transformation
        assertNotNull(openAIResponse);
        assertEquals("test-id", openAIResponse.getId());
        assertEquals("gpt-3.5-turbo", openAIResponse.getModel());
        assertEquals("chat.completion", openAIResponse.getObject());
        assertNotNull(openAIResponse.getChoices());
        assertEquals(1, openAIResponse.getChoices().size());
        assertEquals("assistant", openAIResponse.getChoices().get(0).getMessage().getRole());
        assertEquals("I'm doing well, thank you!", openAIResponse.getChoices().get(0).getMessage().getContent());
        assertEquals("stop", openAIResponse.getChoices().get(0).getFinishReason());
        
        assertNotNull(openAIResponse.getUsage());
        assertEquals(10, openAIResponse.getUsage().getPromptTokens());
        assertEquals(8, openAIResponse.getUsage().getCompletionTokens());
        assertEquals(18, openAIResponse.getUsage().getTotalTokens());
    }
}
