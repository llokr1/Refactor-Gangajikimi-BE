package Myaong.Gangajikimi.dogtype.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * FastAPI의 /dogbreed 엔드포인트로부터 받는 JSON 응답을 매핑하기 위한 DTO입니다.
 * JSON: {"result": "품종명"}
 */
@Getter
@NoArgsConstructor
public class DogBreedResponse {

    /**
     * JSON의 'result' 키에 해당하는 값을 담을 필드입니다.
     * 예측된 강아지 품종 텍스트가 여기에 저장됩니다.
     */
    private String result;

    private DogBreedResponse(String result) {
        this.result = result;
    }

    public static DogBreedResponse of(String result) {
        return new DogBreedResponse(result);
    }
}