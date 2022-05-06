package nextstep.helloworld.config.javaConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationPrincipalConfig {

    // 메서드명이 곧 해당 빈의 bean definition name
    @Bean
    public AuthService newNameForAuthServiceBean() {
        return new AuthService();
    }

    // Bean Dependencies : 빈 이름이 아닌 타입 기반으로 의존성을 찾아와서 호출?
    @Bean
    public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver(AuthService authService) {
        return new AuthenticationPrincipalArgumentResolver(authService);
    }
}
