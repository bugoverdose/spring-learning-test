package nextstep.helloworld.config.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ValueConfig {

    @Bean
    public JwtTokenExpireProvider jwtTokenExpireProvider(@Value("${security-jwt-token-expire-length}") long validityInMilliseconds) {
        return new JwtTokenExpireProvider(validityInMilliseconds);
    }
}
