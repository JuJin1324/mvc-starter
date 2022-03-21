package practice.mvcstarter.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/21
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthLoginDto {
    private String loginId;
    private String password;

    public void validate() {
        if (!StringUtils.hasText(loginId)) {
            throw new IllegalArgumentException("[AuthLoginDto] loginId is blank.");
        }
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("[AuthLoginDto] password is blank.");
        }
    }
}
