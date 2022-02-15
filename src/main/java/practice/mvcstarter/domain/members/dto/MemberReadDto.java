package practice.mvcstarter.domain.members.dto;

import lombok.Getter;
import practice.mvcstarter.domain.files.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.members.entity.Member;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Getter
public class MemberReadDto {
    private final Long        memberId;
    private final String      nickName;
    private       ContentType contentType;
    private       String      base64Image;

    public MemberReadDto(Member member, Optional<FileBase64ReadDto> profileOptional) {
        this.memberId = member.getId();
        this.nickName = member.getNickName();

        if (profileOptional.isPresent()) {
            FileBase64ReadDto profile = profileOptional.get();
            this.contentType = profile.getContentType();
            this.base64Image = profile.getBase64Image();
        }
    }
}
