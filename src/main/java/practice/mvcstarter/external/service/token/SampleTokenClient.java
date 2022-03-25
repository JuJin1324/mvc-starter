package practice.mvcstarter.external.service.token;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/25
 */

@Component
public class SampleTokenClient implements TokenClient<SampleTokenPayload> {
    private final String token = "1111";

    @Override
    public String createToken(SampleTokenPayload tokenPayload) {
        return token;
    }

    @Override
    public boolean verifyToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        return token.equals(this.token);
    }

    @Override
    public SampleTokenPayload getPayload(String token) {
        return new SampleTokenPayload(1L);
    }
}
