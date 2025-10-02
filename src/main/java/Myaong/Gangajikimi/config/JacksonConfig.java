package Myaong.Gangajikimi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Hibernate Lazy Loading 문제는 DTO 변환으로 해결
        
        // Java Time 모듈 등록
        mapper.registerModule(new JavaTimeModule());
        
        return mapper;
    }
}
