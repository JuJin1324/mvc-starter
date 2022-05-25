package practice.mvcstarter.domain.board.dto;

import lombok.Getter;
import practice.mvcstarter.domain.board.entity.BoardType;
import practice.mvcstarter.domain.file.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.user.dto.UserReadDto;
import practice.mvcstarter.domain.user.entity.User;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Getter
public class BoardPostReadDto {
    private final Long          boardId;
    private final BoardType     topic;
    private final String        title;
    private final String        content;
    private final Long          likeCount;
    private final Long          readCount;
    private final Long        commentCount;
    private       UserReadDto member;

    //    @QueryProjection
    public BoardPostReadDto(Long boardId, BoardType topic, String title, String content,
                            Long likeCount, Long readCount, Long commentCount) {
        this.boardId = boardId;
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.readCount = readCount;
        this.commentCount = commentCount;
    }

    public void setMember(User user, Optional<FileBase64ReadDto> profileOptional) {
        this.member = new UserReadDto(user, profileOptional);
    }
}
