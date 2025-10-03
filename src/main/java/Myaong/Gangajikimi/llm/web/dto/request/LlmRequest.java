package Myaong.Gangajikimi.llm.web.dto.request;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LlmRequest {

    String breed;

    String colors;

    String features;

    @Builder
    private LlmRequest(String breed, String colors, String features) {
        this.breed = breed;
        this.colors = colors;
        this.features = features;
    }

    public static LlmRequest of(String breed, String colors, String features) {

        return LlmRequest.builder()
                .breed(breed)
                .colors(colors)
                .features(features)
                .build();
    }

}
