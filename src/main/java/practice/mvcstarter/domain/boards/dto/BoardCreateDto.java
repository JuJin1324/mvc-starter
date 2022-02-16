package practice.mvcstarter.domain.boards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import practice.mvcstarter.domain.boards.entity.BoardType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@NoArgsConstructor
@Getter
public class BoardCreateDto {
    private BoardType topic;
    private String    title;
    private String              content;
    private Long                memberId;
    private List<MultipartFile> files;

    public BoardCreateDto(BoardType topic, String title, String content, Long memberId, MultipartFile[] files) {
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.files = Arrays.asList(files);
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
