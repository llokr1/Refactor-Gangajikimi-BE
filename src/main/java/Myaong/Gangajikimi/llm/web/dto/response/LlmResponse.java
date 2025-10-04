package Myaong.Gangajikimi.llm.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LlmResponse {

    private List<String> rendered;

    public static LlmResponse of(String sentence1, String sentence2, String sentence3){
        return LlmResponse.builder()
                .rendered(List.of(sentence1, sentence2, sentence3))
                .build();
    }
}
