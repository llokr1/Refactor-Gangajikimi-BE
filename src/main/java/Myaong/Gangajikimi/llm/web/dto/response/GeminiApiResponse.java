package Myaong.Gangajikimi.llm.web.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// Gemini API 응답의 JSON 구조
@JsonIgnoreProperties(ignoreUnknown = true) // 모르는 필드는 무시
public record GeminiApiResponse(List<Candidate> candidates) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Candidate(Content content) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Content(List<Part> parts) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Part(String text) {}

    /**
     * 응답에서 최종 텍스트(LLM이 생성한 JSON 문자열)를 쉽게 추출하기 위한 헬퍼 메소드
     */
    public String extractFirstCandidateText() {
        if (candidates != null && !candidates.isEmpty() &&
                candidates.get(0).content() != null &&
                candidates.get(0).content().parts() != null &&
                !candidates.get(0).content().parts().isEmpty()) {
            return candidates.get(0).content().parts().get(0).text();
        }
        return null;
    }
}