package practice.mvcstarter.domain.boards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import practice.mvcstarter.domain.boards.entity.BoardTopic;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@NoArgsConstructor
@Getter
public class BoardCreateDto {
    private BoardTopic topic;
    private String     title;
    private String     content;
    private Long       memberId;

    // TODO: File 추가

    public BoardCreateDto(BoardTopic topic, String title, String content, Long memberId) {
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public void validate() {
        if (topic == null) {
            throw new IllegalArgumentException("[BoardCreateDto] topic is null.");
        }
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("[BoardCreateDto] title is blank.");
        }
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("[BoardCreateDto] content is blank.");
        }
        if (memberId == null) {
            throw new IllegalArgumentException("[BoardCreateDto] memberId is null.");
        }
    }
}
