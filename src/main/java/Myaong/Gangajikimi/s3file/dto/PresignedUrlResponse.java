package Myaong.Gangajikimi.s3file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PresignedUrlResponse {
	private String presignedUrl;
	private String fileUrl;
}