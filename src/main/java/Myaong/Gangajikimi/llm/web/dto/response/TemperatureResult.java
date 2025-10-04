package Myaong.Gangajikimi.llm.web.dto.response;

import java.util.List;

public record TemperatureResult(
        double temperature,
        List<String> rendered
) {
    // LlmResponse 객체를 이 DTO로 쉽게 변환하기 위한 정적 메소드
    public static TemperatureResult from(double temperature, LlmResponse llmResponse) {
        return new TemperatureResult(temperature, llmResponse.getRendered());
    }
}