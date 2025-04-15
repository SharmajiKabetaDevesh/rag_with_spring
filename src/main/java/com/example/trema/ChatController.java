package com.example.trema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {
    @Autowired
    ModelConfig modelConfig;

    @GetMapping("/")
    String greet(){
        return "Api is working fine";
    }

    @PostMapping("/chat")
    String getResponse(@RequestBody String prompt){
        return modelConfig.singleResponse(prompt);
    }


    @PostMapping("/schat")
    Flux<String> getStreamingResponse(@RequestBody String prompt){
        return modelConfig.streamingResponse(prompt);
    }

}
