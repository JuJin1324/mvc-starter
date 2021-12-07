package practice.mvcstarter.domain.boards;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@Getter
public class BoardDto {
    private final Long boardId;

    private final String title;

    private final String content;

    private final Integer recommendCount;

    private final Integer readCount;

    private final Integer commentCount;

    /* TODO: BoardCommentDto List 추가 */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd HH:mm:ss", locale = "Asia/Seoul")
    private LocalDateTime createdDateKST;

    @Builder
    public BoardDto(Long boardId, String title, String content, Integer recommendCount, Integer readCount, Integer commentCount, LocalDateTime createdDateKST) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.recommendCount = recommendCount;
        this.readCount = readCount;
        this.commentCount = commentCount;
        this.createdDateKST = createdDateKST;
    }

    public static BoardDto toCreate(String title, String content) {
        return BoardDto.builder()
                .title(title)
                .content(content)
                .build();
    }

    public static BoardDto toUpdate(String title, String content) {
        return BoardDto.builder()
                .title(title)
                .content(content)
                .build();
    }

    public static BoardDto toRead(Board board) {
        /* TODO: count 추가 */
        return BoardDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }

    public void validateToCreate() {
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("[BoardDto] title is blank.");
        }
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("[BoardDto] content is blank.");
        }
    }

    public void validateToUpdate() {
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("[BoardDto] title is blank.");
        }
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("[BoardDto] content is blank.");
        }
    }
}
