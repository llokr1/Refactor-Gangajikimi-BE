package Myaong.Gangajikimi.s3file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * S3 관련 Bean 설정 클래스
 * - AWS SDK v2의 S3Presigner 객체를 스프링 빈으로 등록한다.
 * - Presigned URL을 발급할 때 이 Bean을 주입받아 사용한다.
 */

@Configuration
public class S3Config {

	@Bean
	public S3Presigner s3Presigner() {
		return S3Presigner.builder()
			.region(Region.AP_NORTHEAST_2) // 서울 리전
			.credentialsProvider(DefaultCredentialsProvider.create()) // IAM Role or 환경 변수에서 자격증명 자동 로드
			.build();
	}
}

