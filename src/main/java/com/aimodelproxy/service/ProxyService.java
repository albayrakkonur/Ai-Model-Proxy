package com.aimodelproxy.service;

import com.aimodelproxy.dto.openai.OpenAIChatRequest;
import com.aimodelproxy.dto.openai.OpenAIChatResponse;
import com.aimodelproxy.dto.qwen3.Qwen3ChatRequest;
import com.aimodelproxy.dto.qwen3.Qwen3ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProxyService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProxyService.class);
    
    private final ApiTransformationService transformationService;
    private final Qwen3ApiService qwen3ApiService;
    
    public ProxyService(ApiTransformationService transformationService, 
                        Qwen3ApiService qwen3ApiService) {
        this.transformationService = transformationService;
        this.qwen3ApiService = qwen3ApiService;
    }
    
    /**
     * Process OpenAI chat completion request by proxying to Qwen3
     */
    public Mono<OpenAIChatResponse> processChatCompletion(OpenAIChatRequest openAIRequest) {
        logger.info("Processing chat completion request for model: {}", openAIRequest.getModel());
        
        // Store original model for response
        String originalModel = openAIRequest.getModel();
        
        return Mono.fromCallable(() -> transformationService.transformToQwen3Request(openAIRequest))
                .doOnNext(qwen3Request -> logger.debug("Transformed request to Qwen3 format"))
                .flatMap(qwen3ApiService::sendChatRequest)
                .map(qwen3Response -> transformationService.transformToOpenAIResponse(qwen3Response, originalModel))
                .doOnSuccess(response -> logger.info("Successfully processed chat completion request"))
                .doOnError(error -> logger.error("Error processing chat completion request", error));
    }
}