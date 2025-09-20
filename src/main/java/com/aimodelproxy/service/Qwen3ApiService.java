package com.aimodelproxy.service;

import com.aimodelproxy.config.Qwen3Properties;
import com.aimodelproxy.dto.qwen3.Qwen3ChatRequest;
import com.aimodelproxy.dto.qwen3.Qwen3ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class Qwen3ApiService {
    
    private static final Logger logger = LoggerFactory.getLogger(Qwen3ApiService.class);
    
    private final WebClient qwen3WebClient;
    private final Qwen3Properties qwen3Properties;
    
    public Qwen3ApiService(@Qualifier("qwen3WebClient") WebClient qwen3WebClient, 
                           Qwen3Properties qwen3Properties) {
        this.qwen3WebClient = qwen3WebClient;
        this.qwen3Properties = qwen3Properties;
    }
    
    /**
     * Send chat completion request to Qwen3 API
     */
    public Mono<Qwen3ChatResponse> sendChatRequest(Qwen3ChatRequest request) {
        logger.info("Sending request to Qwen3 API with model: {}", request.getModel());
        
        return qwen3WebClient
                .post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Qwen3ChatResponse.class)
                .timeout(Duration.ofMillis(qwen3Properties.getTimeout()))
                .doOnSuccess(response -> logger.info("Successfully received response from Qwen3 API"))
                .doOnError(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException webClientEx = (WebClientResponseException) error;
                        logger.error("Qwen3 API error - Status: {}, Response: {}", 
                                webClientEx.getStatusCode(), webClientEx.getResponseBodyAsString());
                    } else {
                        logger.error("Error calling Qwen3 API", error);
                    }
                })
                .onErrorMap(this::handleApiError);
    }
    
    private Throwable handleApiError(Throwable error) {
        if (error instanceof WebClientResponseException) {
            WebClientResponseException webClientEx = (WebClientResponseException) error;
            return new RuntimeException("Qwen3 API error: " + webClientEx.getStatusCode() + 
                    " - " + webClientEx.getResponseBodyAsString(), error);
        }
        return new RuntimeException("Failed to communicate with Qwen3 API", error);
    }
}