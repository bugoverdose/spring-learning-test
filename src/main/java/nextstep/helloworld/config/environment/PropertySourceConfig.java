package nextstep.helloworld.config.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties") // application.properties 파일을 활용하기 위한 설정 추가하기
public class PropertySourceConfig {

    private final Environment env;

    public PropertySourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public JwtTokenKeyProvider jwtTokenKeyProvider() {
        return new JwtTokenKeyProvider(env.getProperty("security-jwt-token-secret-key"));
    }
}
