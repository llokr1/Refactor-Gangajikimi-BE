package Myaong.Gangajikimi.llm.web.dto.request;

import java.util.List;

public record GeminiApiRequest(List<Content> contents, GenerationConfig generationConfig) {

    public record Content(List<Part> parts) {}

    public record Part(String text) {}

}