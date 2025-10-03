package Myaong.Gangajikimi.postlostreport.dto;

import Myaong.Gangajikimi.common.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLostReportRequest {

    @NotNull(message = "신고 유형은 필수입니다")
    private ReportType reportType;

    @Size(max = 500, message = "신고 내용은 500자 이하로 입력해주세요")
    private String reportContent;
}
