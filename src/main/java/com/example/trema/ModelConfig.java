package com.example.trema;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;


@Configuration
public class ModelConfig {

    ChatClient chatClient;

  

    public ModelConfig(ChatClient.Builder chatClient, VectorStore vectorStore) {
        this.chatClient=chatClient.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)).build();
         
    }

    public String chatLLm(String prompt){
        return chatClient.prompt().user(prompt).call().content();

    }
     
    public String  singleResponse(String prompt){
        return chatClient.prompt().user(prompt).call().content();
    }
    public Flux<String> streamingResponse(String prompt){
        return chatClient.prompt().user(prompt).stream().content();
    }
}
