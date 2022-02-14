package practice.mvcstarter.domain.boards.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import practice.mvcstarter.domain.boards.BoardTopic;

import java.time.LocalDateTime;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Getter
public class BoardReadDto {
    private final Long       boardId;
    private final BoardTopic topic;
    private final String     title;
    private final String     writerName;
    private final Long       likeCount;
    private final Long       readCount;
    private final Long       commentCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm:ss", locale = "Asia/Seoul")
    private final LocalDateTime createdDateKST;

    @QueryProjection
    public BoardReadDto(Long boardId, BoardTopic topic, String title, String writerName, Long likeCount, Long readCount, Long commentCount, LocalDateTime createdDateKST) {
        this.boardId = boardId;
        this.topic = topic;
        this.title = title;
        this.writerName = writerName;
        this.likeCount = likeCount;
        this.readCount = readCount;
        this.commentCount = commentCount;
        this.createdDateKST = createdDateKST;
    }
}
