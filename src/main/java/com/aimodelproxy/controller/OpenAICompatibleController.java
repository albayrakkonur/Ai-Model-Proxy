package com.aimodelproxy.controller;

import com.aimodelproxy.dto.openai.OpenAIChatRequest;
import com.aimodelproxy.dto.openai.OpenAIChatResponse;
import com.aimodelproxy.service.ProxyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class OpenAICompatibleController {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAICompatibleController.class);
    
    private final ProxyService proxyService;
    
    public OpenAICompatibleController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }
    
    @PostMapping("/chat/completions")
    public Mono<ResponseEntity<OpenAIChatResponse>> chatCompletions(
            @Valid @RequestBody OpenAIChatRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        
        logger.info("Received chat completions request for model: {}", request.getModel());
        
        return proxyService.processChatCompletion(request)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }
    
    @GetMapping("/models")
    public ResponseEntity<Object> listModels() {
        // Return a basic models list indicating we proxy OpenAI models to Qwen3
        String response = """
                {
                  "object": "list",
                  "data": [
                    {
                      "id": "gpt-3.5-turbo",
                      "object": "model",
                      "created": 1677610602,
                      "owned_by": "openai-proxy"
                    },
                    {
                      "id": "gpt-4",
                      "object": "model",
                      "created": 1687882411,
                      "owned_by": "openai-proxy"
                    }
                  ]
                }
                """;
        
        return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<Object> health() {
        return ResponseEntity.ok().body("{\"status\": \"healthy\", \"service\": \"ai-model-proxy\"}");
    }
}