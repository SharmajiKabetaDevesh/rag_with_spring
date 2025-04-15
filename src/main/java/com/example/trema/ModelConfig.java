package com.example.trema;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Configuration
public class ModelConfig {

    ChatClient chatClient;
    DbConfig dbConfig;

    public ModelConfig(ChatClient.Builder chatClient,DbConfig dbConfig) {
        this.chatClient=chatClient.build();
        this.dbConfig=dbConfig;
    }

    public String singleResponse(String prompt){
        return chatClient.prompt().user(prompt).call().content();
    }
    public Flux<String> streamingResponse(String prompt){
        return chatClient.prompt().user(prompt).stream().content();
    }
}
