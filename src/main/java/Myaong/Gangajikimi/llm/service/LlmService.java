package Myaong.Gangajikimi.llm.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.llm.prompt.GeminiPrompt;
import Myaong.Gangajikimi.llm.web.dto.request.GeminiApiRequest;
import Myaong.Gangajikimi.llm.web.dto.request.LlmRequest;
import Myaong.Gangajikimi.llm.web.dto.response.GeminiApiResponse;
import Myaong.Gangajikimi.llm.web.dto.response.LlmResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LlmService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    // application.yml에서 설정한 값 주입
    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final GeminiPrompt geminiPrompt;

    public LlmResponse formattingDogInfo(LlmRequest request) {
        // 1. API 전체 URL 조합
        String fullUrl = apiUrl + "/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + apiKey;

        // 2. 프롬프트 설정
        String prompt = geminiPrompt.generatePrompt(request.getBreed(), request.getColors(), request.getFeatures());

        GeminiApiRequest.Part part = new GeminiApiRequest.Part(prompt);
        GeminiApiRequest.Content content = new GeminiApiRequest.Content(List.of(part));
        GeminiApiRequest requestBody = new GeminiApiRequest(List.of(content));

        // 4. HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GeminiApiRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        // 6. RestTemplate을 사용하여 POST 요청 보내기

        GeminiApiResponse response = restTemplate.postForObject(fullUrl, requestEntity, GeminiApiResponse.class);

        if (response != null) {
            return parseToLlmResponse(response);
        } else{
            throw new GeneralException(ErrorCode.AI_SERVER_ERROR);
        }
    }

    private LlmResponse parseToLlmResponse(GeminiApiResponse apiResponse) {
        if (apiResponse == null) {
            return null;
        }

        try {
            // 1. 핵심 문자열 추출
            String llmOutputText = apiResponse.extractFirstCandidateText();
            if (llmOutputText == null || llmOutputText.isBlank()) {
                throw new GeneralException(ErrorCode.AI_SERVER_ERROR);
            }

            // 2. 문자열 정리
            String cleanedJson = llmOutputText.replace("```json", "").replace("```", "").trim();

            // 3. 최종 객체로 파싱
            return objectMapper.readValue(cleanedJson, LlmResponse.class);

        } catch (JsonProcessingException e) {
            throw new GeneralException(ErrorCode.AI_SERVER_ERROR);
        }

    }

}

