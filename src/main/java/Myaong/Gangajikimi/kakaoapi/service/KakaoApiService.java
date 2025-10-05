package Myaong.Gangajikimi.kakaoapi.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.kakaoapi.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoApiService {

    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String key;

    public String getAddrFromKakaoApi(double longitude, double latitude) {

        String result = "";

        String url = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?input_coord=WGS84&output_coord=WGS84&x=%f&y=%f"
                .formatted(longitude, latitude);

        log.info("Kakao API URL: " + url);

        // 헤더 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK " + key);

        // 요청 객체 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, KakaoResponse.class);

            KakaoResponse kakaoResponse = response.getBody();

            if (kakaoResponse != null && !kakaoResponse.getDocuments().isEmpty()) {
                KakaoResponse.Document firstDocument = kakaoResponse.getDocuments().get(0);
                result = firstDocument.getRegion_1depth_name() + " " + firstDocument.getRegion_2depth_name();
            }
        } catch (HttpClientErrorException e) {
            log.error(e.getMessage());
            return result;
        }

        return result;
    }
}
