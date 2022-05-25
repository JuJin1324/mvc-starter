package practice.mvcstarter.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.mvcstarter.domain.auth.dto.AuthLoginDto;
import practice.mvcstarter.domain.auth.service.LoginService;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/21
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final LoginService loginService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    public String login(@Validated @RequestBody AuthLoginDto dto) {
        return loginService.getLoginToken(dto);
    }
}
