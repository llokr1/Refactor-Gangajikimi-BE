package Myaong.Gangajikimi.llm.web.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LlmRequest {

    String breed;

    String colors;

    String features;

}
