package Myaong.Gangajikimi.kakaoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoResponse {

    private Meta meta;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private int total_count;
    }

    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    public static class Document {
        private String region_type;
        private String code;
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String region_4depth_name;
        private double x;
        private double y;
    }
}
