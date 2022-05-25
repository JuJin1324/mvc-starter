package practice.mvcstarter.domain.users.dto;

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
public class UserUpdateDto {
    private String nickName;

    public UserUpdateDto(String nickName) {
        this.nickName = nickName;
    }

    public void validate() {
        if (!StringUtils.hasText(nickName)) {
            throw new IllegalArgumentException("[MemberUpdateDto] nickName is blank.");
        }
    }
}
