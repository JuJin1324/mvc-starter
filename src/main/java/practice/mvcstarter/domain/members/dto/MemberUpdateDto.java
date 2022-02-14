package practice.mvcstarter.domain.members.dto;

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
public class MemberUpdateDto {
    private String  nickName;
    private Integer age;

    public MemberUpdateDto(String nickName, Integer age) {
        this.nickName = nickName;
        this.age = age;
    }

    public void validate() {
        if (!StringUtils.hasText(nickName)) {
            throw new IllegalArgumentException("[MemberUpdateDto] nickName is blank.");
        }
        if (age == null) {
            throw new IllegalArgumentException("[MemberUpdateDto] age is null.");
        }
    }
}
