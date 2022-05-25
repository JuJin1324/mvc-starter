package practice.mvcstarter.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.mvcstarter.domain.auth.dto.AuthLoginDto;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/21
 */

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    @Override
    public String getLoginToken(AuthLoginDto dto) {
        return null;
    }

    @Override
    public Long authenticate(String loginToken) {
        return null;
    }
}
