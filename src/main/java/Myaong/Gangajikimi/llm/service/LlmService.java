package Myaong.Gangajikimi.llm.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.llm.prompt.GeminiPrompt;
import Myaong.Gangajikimi.llm.web.dto.request.GeminiApiRequest;
import Myaong.Gangajikimi.llm.web.dto.request.GenerationConfig;
import Myaong.Gangajikimi.llm.web.dto.request.LlmRequest;
import Myaong.Gangajikimi.llm.web.dto.response.GeminiApiResponse;
import Myaong.Gangajikimi.llm.web.dto.response.LlmResponse;
import Myaong.Gangajikimi.llm.web.dto.response.MultiLlmResponse;
import Myaong.Gangajikimi.llm.web.dto.response.TemperatureResult;
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

import java.util.ArrayList;
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

//    double temperature = 0.1;

    double[] temperature = {0.0, 0.1, 0.2};

    private final GeminiPrompt geminiPrompt;

    public MultiLlmResponse formattingDogInfo(LlmRequest request) {
        // 1. API 전체 URL 조합
        String fullUrl = apiUrl + "?key=" + apiKey;

        // 2. 프롬프트 설정
        String prompt = geminiPrompt.generatePrompt(request.getBreed(), request.getColors(), request.getFeatures());

        List<TemperatureResult> results = new ArrayList<>();

        for (int i = 0; i < temperature.length; i++) {

            // 3. temperature 설정
            GenerationConfig generationConfig = new GenerationConfig(temperature[i]);

            //4.
            GeminiApiRequest.Part part = new GeminiApiRequest.Part(prompt);
            GeminiApiRequest.Content content = new GeminiApiRequest.Content(List.of(part));
            GeminiApiRequest requestBody = new GeminiApiRequest(List.of(content), generationConfig);

            // 5. HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<GeminiApiRequest> requestEntity = new HttpEntity<>(requestBody, headers);

            // 6. RestTemplate을 사용하여 POST 요청 보내기
            GeminiApiResponse response = restTemplate.postForObject(fullUrl, requestEntity, GeminiApiResponse.class);

            // 7. 우리가 원하는 형식으로 파싱
            LlmResponse llmResponse = parseToLlmResponse(response);

            if (response != null) {
//                return parseToLlmResponse(response);
                results.add(TemperatureResult.from(temperature[i], llmResponse));
            } else {
                throw new GeneralException(ErrorCode.AI_SERVER_ERROR);
            }
        }
        return new MultiLlmResponse(results);
    }

/*
    public LlmResponse formattingDogInfo(LlmRequest request) {
        // 1. API 전체 URL 조합
        String fullUrl = apiUrl + "?key=" + apiKey;

        // 2. 프롬프트 설정
        String prompt = geminiPrompt.generatePrompt(request.getBreed(), request.getColors(), request.getFeatures());

        // 3. temperature 설정
        GenerationConfig generationConfig = new GenerationConfig(temperature);

        //4.
        GeminiApiRequest.Part part = new GeminiApiRequest.Part(prompt);
        GeminiApiRequest.Content content = new GeminiApiRequest.Content(List.of(part));
        GeminiApiRequest requestBody = new GeminiApiRequest(List.of(content), generationConfig);

        // 5. HTTP 헤더 설정
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


 */
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

