package nextstep.helloworld.mvcconfig.ui;

import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import nextstep.helloworld.mvcconfig.domain.LoginMember;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override // (1) 메서드의 매개변수 중에 @AuthenticationPrincipal가 붙은 대상에 대해
    public boolean supportsParameter(MethodParameter parameter) {
        // ex) 컨트롤러에서 EncryptedAuthCredentials 타입의 매개변수에 대해 동작
        // parameter.getParameterType().equals(EncryptedAuthCredentials.class);

        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override // (2) 해당 메서드의 반환값을 매개변수에 인자로 담아주기
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws IOException {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        System.out.println(request.getMethod()); // POST, DELETE 등

        // ?abc=XXX 형식으로 들어오는 값들
        System.out.println(request.getParameterMap().keySet());
        System.out.println(request.getParameterMap().entrySet());

        // request body로 들어오는 JSON 데이터
        System.out.println(request.getReader().lines().findFirst());
        request.getReader().lines().forEach(System.out::println);

        LoginMember loginMember = new LoginMember(1L, "email", 120);
        System.out.println("!!!인자로 넣어줄 값!!!");
        System.out.println(loginMember);
        return loginMember;
    }
}
