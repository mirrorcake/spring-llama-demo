package com.mirrorcake.ollama.service;

import com.mirrorcake.ollama.common.BaseCodeEnum;
import com.mirrorcake.ollama.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * @author mirrorcake
 */
@Slf4j
@Service
public class OllamaAiService {

    private final OllamaChatModel chatModel;

    @Autowired
    public OllamaAiService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * generate normal message
     */
    public BaseResponse<String> generate(String message) {
        long startTime = System.currentTimeMillis();
        BaseResponse<String> response;
        String result;
        try {
            result = chatModel.call(message);
            response = new BaseResponse<>(result);
            long endTime = System.currentTimeMillis();
            log.info("Generated in {} ms, content:{}", endTime - startTime, result);
        } catch (Exception e) {
            response = new BaseResponse<>(BaseCodeEnum.FAILED);
            log.error("Generated failed");
        }
        return response;
    }


    /**
     * generate stream message
     */
    public Flux<ChatResponse> generateStream(String message) {
        try {
            Prompt prompt = new Prompt(new UserMessage(message));
            return chatModel.stream(prompt);
        } catch (Exception e) {
            log.error("Generate Stream failed");
            return null;
        }
    }


    /**
     * restart ollama
     */
    public BaseResponse<String> restartOllamaService() {
        long startTime = System.currentTimeMillis();
        BaseResponse<String> response;
        try {
            ProcessBuilder pb = new ProcessBuilder("systemctl", "start", "ollama");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.info("restart ollama failed");
                response = new BaseResponse<>(BaseCodeEnum.FAILED.getCode(), "restart ollama failed");
            } else {
                long endTime = System.currentTimeMillis();
                log.info("restart ollama success in {}ms", endTime - startTime);
                response = new BaseResponse<>(BaseCodeEnum.SUCCESS.getCode(), "restart ollama success");
            }
        } catch (IOException | InterruptedException e) {
            response = new BaseResponse<>(BaseCodeEnum.FAILED.getCode(), "restart ollama failed");
        }
        return response;
    }

}
