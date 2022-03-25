package practice.mvcstarter.external.service.token;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/25
 */
public interface TokenClient<T> {
    /**
     * 토큰 생성
     *
     * @return token
     */
    String createToken(T tokenPayload);

    /**
     * 토큰 검증
     */
    boolean verifyToken(String token);

    /**
     * Payload 조회
     */
    T getPayload(String token);
}
