package Myaong.Gangajikimi.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .rootUri("localhost:8000") //TODO: 추후 FastAPI IP 주소로 변경
                .build();

    }
}
