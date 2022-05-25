package practice.mvcstarter.domain.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/03/15
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@ExtendWith(MockitoExtension.class)
class PostCommentServiceTest {
    PostCommentService postCommentService;

    @BeforeEach
    void setUp() {
        // TODO: init PostCommentService
    }

    @Test
    @DisplayName("[게시글 댓글 신규] 1.유효하지 않은 매개변수")
    void createComment_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시글 대댓글 신규] 1.유효하지 않은 매개변수")
    void createChildComment_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시글 댓글 조회 - 페이지] 1.유효하지 않은 매개변수")
    void getCommentPage_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시글 댓글 수정] 1.유효하지 않은 매개변수")
    void updateComment_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시글 댓글 삭제] 1.유효하지 않은 매개변수")
    void deleteComment_whenInvalidParam_thenThrowException() {

    }
}
