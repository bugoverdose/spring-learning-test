package nextstep.helloworld.config.javaConfig;

import nextstep.helloworld.HelloApplication;
import nextstep.helloworld.config.xmlConfig.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

// "When you work directly in Java,
// you can do anything you like with your objects
// and do not always need to rely on the container lifecycle."
/**
 * AnnotationConfigApplicationContext을 통해
 * AuthenticationPrincipalConfig에 설정된 빈들을 이용한 테스트
 * <p>
 * AuthenticationPrincipalConfig을 이용하여
 * AuthService 빈 등록하기
 */
class JavaConfigTest {

    @Test
    void javaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(HelloApplication.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println(beanName);
        }

        AuthenticationPrincipalConfig authenticationPrincipalConfig = context.getBean("authenticationPrincipalConfig", AuthenticationPrincipalConfig.class);
        AuthService authService = context.getBean("newNameForAuthServiceBean", AuthService.class);
        AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver = context.getBean("authenticationPrincipalArgumentResolver", AuthenticationPrincipalArgumentResolver.class);

        assertThat(authenticationPrincipalConfig).isNotNull();
        assertThat(authService).isNotNull();
        assertThat(authenticationPrincipalArgumentResolver).isNotNull();
    }
}
