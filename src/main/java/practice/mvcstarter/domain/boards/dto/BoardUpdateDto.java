package practice.mvcstarter.domain.boards.dto;

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
public class BoardUpdateDto {
    private String     title;
    private String     content;

    // TODO : File 추가

    public BoardUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validate() {
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("[BoardUpdateDto] title is blank.");
        }
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("[BoardUpdateDto] content is blank.");
        }
    }
}
