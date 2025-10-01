package Myaong.Gangajikimi.s3file.service;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Presigner s3Presigner;
    private final S3Client s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "webp", "gif", "svg");

    public String upload(MultipartFile image, String keyPrefix, String fileName) {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new GeneralException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }
        return this.uploadImage(image, keyPrefix, fileName);
    }

    private String uploadImage(MultipartFile image, String keyPrefix, String fileName) {
        this.validateExtension(image.getOriginalFilename());
        try {
            return this.uploadImageToS3(image, keyPrefix, fileName);
        } catch (IOException e) {
            throw new GeneralException(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD);
        }
    }

    private String uploadImageToS3(MultipartFile image, String keyPrefix, String fileName) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new GeneralException(ErrorCode.EMPTY_FILE_EXCEPTION);
        }

        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new GeneralException(ErrorCode.NO_FILE_EXTENSION);
        }

        String extension = getExtension(originalFilename);
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + "_" + fileName + "." + extension; // 유니크 파일명
        String keyName = keyPrefix + "/" + s3FileName;

        // AWS SDK v2 방식으로 변경
        software.amazon.awssdk.core.sync.RequestBody requestBody = 
            software.amazon.awssdk.core.sync.RequestBody.fromInputStream(
                image.getInputStream(), image.getSize());

        try {
            System.out.println("Uploading image to S3: " + s3FileName);

            // S3 업로드 실행
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .contentType("image/" + extension)
                    .build();

            // 실제 S3 업로드 실행
            s3Client.putObject(objectRequest, requestBody);
            
            System.out.println("S3 업로드 성공: " + s3FileName);
        } catch (Exception e) {
            System.err.println("S3 업로드 실패: " + e.getMessage());
            e.printStackTrace();
            throw new GeneralException(ErrorCode.PUT_OBJECT_EXCEPTION);
        }

        return keyName;
    }

	// 게시글 작성 시에는 Presigned URL을 사용하지 않고 서버에서 직접 업로드

	/**
	 * DB에 저장된 keyName으로부터 다운로드용 Presigned URL 생성
	 * @param keyName DB에 저장된 S3 객체 키
	 * @return 다운로드 가능한 Presigned URL
	 */
	public String generatePresignedUrl(String keyName) {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
			.bucket(bucketName)
			.key(keyName)
			.build();

		GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
			.signatureDuration(Duration.ofMinutes(60)) // 1시간 유효
			.getObjectRequest(getObjectRequest)
			.build();

		URL presignedUrl = s3Presigner.presignGetObject(presignRequest).url();
		return presignedUrl.toString();
	}

	/**
	 * 여러 개의 keyName에 대해 다운로드용 Presigned URL 목록 생성
	 * @param keyNames DB에 저장된 S3 객체 키 목록
	 * @return 다운로드 가능한 Presigned URL 목록
	 */
	public List<String> generatePresignedUrls(List<String> keyNames) {
		return keyNames.stream()
			.map(this::generatePresignedUrl)
			.toList();
	}

	/**
	 * S3에서 파일 삭제
	 * @param keyName 삭제할 파일의 keyName
	 */
	public void deleteFile(String keyName) {
		try {
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
				.bucket(bucketName)
				.key(keyName)
				.build();

			s3Client.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE);
		}
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
