package practice.mvcstarter.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@NoArgsConstructor
@Getter
public class UserCreateDto {
    private String nickname;

    public UserCreateDto(String nickname) {
        this.nickname = nickname;
    }

    public void validate() {
        if (!StringUtils.hasText(nickname)) {
            throw new IllegalArgumentException("[MemberCreateDto] nickName is blank.");
        }
    }
}
