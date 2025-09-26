package Myaong.Gangajikimi.postlostreport.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostLostReportResponse {

    private Long postId;
    private LocalDateTime createdAt;

    public static PostLostReportResponse of(Long postId, LocalDateTime createdAt) {
        return PostLostReportResponse.builder()
                .postId(postId)
                .createdAt(createdAt)
                .build();
    }
}
