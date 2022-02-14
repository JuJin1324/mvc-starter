package practice.mvcstarter.domain.members.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import practice.mvcstarter.domain.files.entity.ContentType;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@NoArgsConstructor
@Getter
public class MemberUpdateProfileDto {
    private String      base64Image;
    private ContentType contentType;

    public MemberUpdateProfileDto(String base64Image, ContentType contentType) {
        this.base64Image = base64Image;
        this.contentType = contentType;
    }

    public void validate() {
        /* base64Image 은 null 일 수 있음.
         * 하지만 base64Image 가 null 이 아닌 경우 contentType 이 무조건 있어야 함. */
        if (StringUtils.hasText(base64Image) && contentType == null) {
            throw new IllegalArgumentException("[MemberUpdateProfileDto] contentType is null.");
        }
    }

    public boolean hasBase64Image() {
        return StringUtils.hasText(base64Image) && contentType != null;
    }
}
