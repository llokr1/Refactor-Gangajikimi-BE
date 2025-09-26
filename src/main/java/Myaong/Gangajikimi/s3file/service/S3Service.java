package Myaong.Gangajikimi.s3file.service;
import Myaong.Gangajikimi.s3file.dto.PresignedUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Presigner s3Presigner;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "webp");

	public PresignedUrlResponse generatePresignedUrl(String originalFileName, String contentType) {
		// 파일 확장자 유효성 검사 (허용 확장자가 아니면 예외 발생)
		validateExtension(originalFileName);

		// 파일 확장자 추출 (예: png)
		String ext = getExtension(originalFileName);

		// UUID 기반 고유 파일명 생성 (파일명 충돌 방지)
		String uniqueFileName = UUID.randomUUID() + "." + ext;


		// S3에 업로드할 객체 요청 정의
		PutObjectRequest objectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(uniqueFileName)
			.contentType(contentType)
			.build();

		// Presigned URL 요청 정의 (10분 유효)
		PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
			.signatureDuration(Duration.ofMinutes(30))
			.putObjectRequest(objectRequest)
			.build();

		// Presigned URL 생성
		URL presignedUrl = s3Presigner.presignPutObject(presignRequest).url();

		// 최종 접근 가능한 S3 URL (DB 저장용)
		String fileUrl = String.format("https://%s.s3.ap-northeast-2.amazonaws.com/%s",
			bucketName, uniqueFileName);

		// 응답 DTO 반환 (uploadUrl: S3 PUT 업로드 주소, fileUrl: 최종 접근 주소)
		return new PresignedUrlResponse(presignedUrl.toString(), fileUrl);
	}

	// 확장자 유효성 검사
	private void validateExtension(String fileName) {
		String ext = getExtension(fileName).toLowerCase();
		if (!ALLOWED_EXTENSIONS.contains(ext)) {
			throw new IllegalArgumentException("허용되지 않은 파일 확장자: " + ext);
		}
	}


	private String getExtension(String fileName) {
		int dotIdx = fileName.lastIndexOf(".");
		if (dotIdx == -1) {
			throw new IllegalArgumentException("파일 확장자가 없습니다.");
		}
		return fileName.substring(dotIdx + 1);
	}
}
