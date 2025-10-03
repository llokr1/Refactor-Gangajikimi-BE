package Myaong.Gangajikimi.fastapi;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.dogtype.dto.response.DogBreedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FastApiService {

    private RestTemplate restTemplate; // AppConfig에 rootUri가 설정된 Bean

    /**
     * 강아지 이미지를 FastAPI 서버로 전송하여 품종 예측 결과를 문자열로 받아옵니다.
     *
     * @param imageFile Controller에서 받은 강아지 이미지 파일
     * @return FastAPI 서버가 예측한 강아지 품종 텍스트 (예: "골든 리트리버")
     * @throws IOException 이미지 파일 처리 중 발생할 수 있는 예외
     */
    public String analyzeImage(MultipartFile imageFile) throws IOException {

        final String apiPath = "/api/v1/dogbreed";

        // 1. HTTP Header 설정 (multipart/form-data)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 2. Multipart Body 생성
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();

        // MultipartFile에서 직접 바이트와 파일명을 가져와 ByteArrayResource로 만든다.
        ByteArrayResource resource = new ByteArrayResource(imageFile.getBytes()) {
            @Override
            public String getFilename() {
                return imageFile.getOriginalFilename();
            }
        };

        // FastAPI 코드: image: UploadFile = File(...) -> 'image' key 사용
        // FastAPI의 'image' 파라미터 이름과 일치해야 함
        requestBody.add("image", resource);

        ResponseEntity<DogBreedResponse> responseEntity = restTemplate.postForEntity(
                apiPath,
                headers,
                DogBreedResponse.class // 응답은 DogBreedResponseDto로 매핑
        );

        // 5. 응답받은 DTO에서 결과(품종 텍스트)를 추출하여 반환합니다.
        DogBreedResponse responseDto = responseEntity.getBody();
        if (responseDto != null) {
            return responseDto.getResult();
        } else {
            // 서버 응답이 비어있는 경우 예외 처리
            throw new GeneralException(ErrorCode.AI_SERVER_ERROR);
        }

    }
}