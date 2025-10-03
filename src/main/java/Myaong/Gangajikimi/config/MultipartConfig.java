package Myaong.Gangajikimi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement(
                "", // 임시 파일 저장 경로 (기본값 사용)
                10 * 1024 * 1024, // max-file-size: 10MB
                50 * 1024 * 1024, // max-request-size: 50MB
                0 // 파일 쓰기 임계값 (0 = 메모리에만 저장)
        );
    }
}
