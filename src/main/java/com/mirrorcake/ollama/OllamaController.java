package com.mirrorcake.ollama;

import com.mirrorcake.ollama.common.BaseResponse;
import com.mirrorcake.ollama.service.OllamaAiService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;


/**
 * @author mirrorcake
 */
@RestController
public class OllamaController {

    private OllamaAiService ollamaAiService;

    @Autowired
    public void setOllamaAiService(OllamaAiService ollamaAiService) {
        this.ollamaAiService = ollamaAiService;
    }

    @GetMapping("/generate")
    public BaseResponse<String> generate(@RequestParam(value = "message", defaultValue = "Hi!") String message) {
        return ollamaAiService.generate(message);
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Hi!") String message) {
        return ollamaAiService.generateStream(message);
    }

    @GetMapping("/restart")
    public BaseResponse<String> restart() {
        return ollamaAiService.restartOllamaService();
    }
}
