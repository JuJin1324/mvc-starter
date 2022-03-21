package practice.mvcstarter.domain.auth.service;

import practice.mvcstarter.domain.auth.dto.AuthLoginDto;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/21
 */
public interface LoginService {
    /**
     * 로그인 토큰 조회
     *
     * @return Login Token
     */
    String getLoginToken(AuthLoginDto dto);

    /**
     * 로그인 인증
     *
     * @return User ID
     */
    Long authenticate(String loginToken);
}
