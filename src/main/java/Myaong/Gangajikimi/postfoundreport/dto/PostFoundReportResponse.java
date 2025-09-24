package Myaong.Gangajikimi.postfoundreport.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostFoundReportResponse {

    private Long postId;
    private LocalDateTime createdAt;

    public static PostFoundReportResponse of(Long postId, LocalDateTime createdAt) {
        return PostFoundReportResponse.builder()
                .postId(postId)
                .createdAt(createdAt)
                .build();
    }
}
