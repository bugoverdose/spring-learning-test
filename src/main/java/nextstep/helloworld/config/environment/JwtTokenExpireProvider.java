package nextstep.helloworld.config.environment;

public class JwtTokenExpireProvider {

    private long validityInMilliseconds;

    public JwtTokenExpireProvider(long validityInMilliseconds) {
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public long getValidityInMilliseconds() {
        return validityInMilliseconds;
    }
}
