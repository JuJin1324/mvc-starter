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
public class UserReadDto {
    private final Long        memberId;
    private final String      nickName;
    private       ContentType contentType;
    private       String      base64Image;

    public UserReadDto(User user, Optional<FileBase64ReadDto> profileOptional) {
        this.memberId = user.getId();
        this.nickName = user.getNickName();

        if (profileOptional.isPresent()) {
            FileBase64ReadDto profile = profileOptional.get();
            this.contentType = profile.getContentType();
            this.base64Image = profile.getBase64Image();
        }
    }
}
