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
public class MemberCreateDto {
    private String  name;
    private String  nickName;
    private Integer age;

    public MemberCreateDto(String name, String nickName, Integer age) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
    }

    public void validate() {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("[MemberDto] name is blank.");
        }
        if (!StringUtils.hasText(nickName)) {
            throw new IllegalArgumentException("[MemberDto] nickName is blank.");
        }
        if (age == null) {
            throw new IllegalArgumentException("[MemberDto] age is null.");
        }
    }
}
