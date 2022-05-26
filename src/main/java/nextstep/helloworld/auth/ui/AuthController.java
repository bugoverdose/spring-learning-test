package nextstep.helloworld.auth.ui;

import javax.servlet.http.HttpSession;
import nextstep.helloworld.auth.application.AuthService;
import nextstep.helloworld.auth.application.AuthorizationException;
import nextstep.helloworld.auth.dto.MemberResponse;
import nextstep.helloworld.auth.dto.TokenRequest;
import nextstep.helloworld.auth.dto.TokenResponse;
import nextstep.helloworld.auth.infrastructure.AuthorizationExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
    private static final String SESSION_KEY = "USER";
    private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * ex) request sample
     * <p>
     * POST /login/session HTTP/1.1
     * content-type: application/x-www-form-urlencoded; charset=ISO-8859-1
     * host: localhost:55477
     * <p>
     * email=email@email.com&password=1234
     */
    @PostMapping("/login/session")
    public ResponseEntity<Void> sessionLogin(@RequestParam String email,
                                             @RequestParam String password,
                                             HttpSession session) {
        if (authService.checkInvalidLogin(email, password)) {
            throw new AuthorizationException();
        }
        // Session에 인증 정보 저장 (key: SESSION_KEY, value: email값)
        session.setAttribute(SESSION_KEY, email);

        return ResponseEntity.ok().build();
        // 응답 - Set-Cookie: JSESSIONID=62067B3144E3A07CA026C2FCA79D7B1A; Path=/; HttpOnly
        // 동일한 계정에 대해서도 쿠키에 담기는 JSESSIONID 값은 매번 변경됨!
    }

    /**
     * ex) request sample
     * <p>
     * GET /members/me HTTP/1.1
     * Cookies: JSESSIONID=62067B3144E3A07CA026C2FCA79D7B1A
     * accept: application/json
     */
    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> findMyInfo(HttpSession session) {
        // Session을 통해 인증 정보 조회하기 (key: SESSION_KEY)
        String email = (String) session.getAttribute(SESSION_KEY);
        MemberResponse member = authService.findMember(email);
        return ResponseEntity.ok().body(member);
    }

    /**
     * ex) request sample
     * <p>
     * POST /login/token HTTP/1.1
     * accept: application/json
     * content-type: application/json; charset=UTF-8
     * <p>
     * {
     * "email": "email@email.com",
     * "password": "1234"
     * }
     */
    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> tokenLogin(@RequestBody TokenRequest tokenRequest) {
        // TokenRequest 값을 메서드 파라미터로 받아오기
        TokenResponse tokenResponse = authService.createToken(tokenRequest);
        // TokenResponse{accessToken='eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2NTM1NDc1NzQsImV4cCI6MTY1MzU1MTE3NH0.kbrthKq56McBaz2TQ5r3udloKXKTsuzJEdQXMR0v6Ps'}
        return ResponseEntity.ok().body(tokenResponse);
    }

    /**
     * ex) request sample
     * <p>
     * GET /members/you HTTP/1.1
     * authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MTAzNzY2NzIsImV4cCI6MTYxMDM4MDI3Mn0.Gy4g5RwK1Nr7bKT1TOFS4Da6wxWh8l97gmMQDgF8c1E
     * accept: application/json
     */
    @GetMapping("/members/you")
    public ResponseEntity<MemberResponse> findYourInfo(HttpServletRequest request) {
        // authorization 헤더의 Bearer 값을 추출하기
        String token = AuthorizationExtractor.extract(request);
        // token = eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2NTM1NDc1NzQsImV4cCI6MTY1MzU1MTE3NH0.kbrthKq56McBaz2TQ5r3udloKXKTsuzJEdQXMR0v6P
        MemberResponse member = authService.findMemberByToken(token);
        return ResponseEntity.ok().body(member);
    }
}
