package practice.mvcstarter.domain.members.dto;

import lombok.Getter;
import practice.mvcstarter.domain.files.ContentType;
import practice.mvcstarter.domain.files.FileReadDto;
import practice.mvcstarter.domain.members.Member;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Getter
public class MemberReadDto {
    private final Long    memberId;
    private final String  name;
    private final String  nickName;
    private final Integer age;

    private ContentType contentType;
    private String      base64Image;

    public MemberReadDto(Member member, Optional<FileReadDto> fileDtoOptional) {
        this.memberId = member.getId();
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.age = member.getAge();

        if (fileDtoOptional.isPresent()) {
            FileReadDto fileReadDto = fileDtoOptional.get();
            this.contentType = fileReadDto.getContentType();
            this.base64Image = fileReadDto.getBase64Image();
        }
    }
}
