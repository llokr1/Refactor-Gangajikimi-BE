package Myaong.Gangajikimi.s3file.converter;

import Myaong.Gangajikimi.s3file.dto.PresignedUrlResponse;

public class S3Converter {

	public static PresignedUrlResponse toResponseDto(PresignedUrlResponse response) {
		return PresignedUrlResponse.builder()
			.presignedUrl(response.getPresignedUrl())
			.fileUrl(response.getFileUrl())
			.build();
	}
}