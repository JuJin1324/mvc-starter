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
public class MemberDetailReadDto {
    private final Long        memberId;
    private final String      name;
    private final String      nickName;
    private final Integer     age;
    private       ContentType contentType;
    private       String      base64Image;

    public MemberDetailReadDto(Member member, Optional<FileBase64ReadDto> profileOptional) {
        this.memberId = member.getId();
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.age = member.getAge();

        if (profileOptional.isPresent()) {
            FileBase64ReadDto profile = profileOptional.get();
            this.contentType = profile.getContentType();
            this.base64Image = profile.getBase64Image();
        }
    }
}
