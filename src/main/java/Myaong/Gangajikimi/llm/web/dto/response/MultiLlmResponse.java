package Myaong.Gangajikimi.llm.web.dto.response;

import java.util.List;

public record MultiLlmResponse(
        List<TemperatureResult> results
) {}