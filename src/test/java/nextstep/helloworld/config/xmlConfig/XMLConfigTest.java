package nextstep.helloworld.config.xmlConfig;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * application-config.xml로 등록한 빈을 검증하는 학습 테스트
 * 추가로 작성할 코드는 없습니다.
 */
class XMLConfigTest {

    @Test
    void main() {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        System.out.println(Arrays.toString(beanDefinitionNames));
        // [userRepository, userService]

        for (String beanName : beanDefinitionNames) {
            System.out.println(beanName);
            System.out.println(context.getBean(beanName, Object.class));
        }
        // customBeanName
        // nextstep.helloworld.config.xmlConfig.UserRepository@4235624d
        // userService
        // nextstep.helloworld.config.xmlConfig.UserService@4f09c523

        UserService service = context.getBean("userService", UserService.class);

        System.out.println(service.sayHello()); // hello
        System.out.println(service.sayHi()); // hi
    }
}
// <?xml version="1.0" encoding="UTF-8"?>
// <beans xmlns="http://www.springframework.org/schema/beans"
//       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
//
//    <bean id="customBeanName" class="nextstep.helloworld.config.xmlConfig.UserRepository"/>
//
//    <bean id="userService" class="nextstep.helloworld.config.xmlConfig.UserService">
//        <property name="userRepository" ref="customBeanName"/>
//        <!-- userService.setUserRepository(customBeanName); -->
//    </bean>
// </beans>
