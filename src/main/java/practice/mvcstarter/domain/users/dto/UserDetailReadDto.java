package practice.mvcstarter.domain.users.dto;

import lombok.Getter;
import practice.mvcstarter.domain.files.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.users.entity.User;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Getter
public class UserDetailReadDto {
    private final Long        memberId;
    private final String      name;
    private final String      nickName;
    private final Integer     age;
    private       ContentType contentType;
    private       String      base64Image;

    public UserDetailReadDto(User user, Optional<FileBase64ReadDto> profileOptional) {
        this.memberId = user.getId();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.age = user.getAge();

        if (profileOptional.isPresent()) {
            FileBase64ReadDto profile = profileOptional.get();
            this.contentType = profile.getContentType();
            this.base64Image = profile.getBase64Image();
        }
    }
}
