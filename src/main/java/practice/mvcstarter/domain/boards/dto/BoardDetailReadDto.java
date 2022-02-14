package practice.mvcstarter.domain.boards.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import practice.mvcstarter.domain.boards.entity.BoardTopic;
import practice.mvcstarter.domain.files.dto.FileReadDto;
import practice.mvcstarter.domain.members.entity.Member;
import practice.mvcstarter.domain.members.dto.MemberReadDto;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Getter
public class BoardDetailReadDto {
    private final Long          boardId;
    private final BoardTopic    topic;
    private final String        title;
    private final String        content;
    private final Long          likeCount;
    private final Long          readCount;
    private final Long          commentCount;
    private       MemberReadDto member;

    @QueryProjection
    public BoardDetailReadDto(Long boardId, BoardTopic topic, String title, String content,
                              Long likeCount, Long readCount, Long commentCount) {
        this.boardId = boardId;
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.readCount = readCount;
        this.commentCount = commentCount;
    }

    public void setMember(Member member, Optional<FileReadDto> profileOptional) {
        this.member = new MemberReadDto(member, profileOptional);
    }
}
