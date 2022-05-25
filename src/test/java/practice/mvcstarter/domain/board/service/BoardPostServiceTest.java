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
class BoardPostServiceTest {
    private static final Long   POST_ID           = 777L;
    private static final Long   POST_ID_NOT_EXIST = 999999999L;
    private static final String POST_TITLE        = "게시판 제목";
    private static final String POST_CONTENT      = "게시판 내용입니다.\n게시판 내용을 채워보았습니다.\n이 게시물은 JUnit 테스트 게시물입니다.\n감사합니다.\n\n";

    private static final String POST_TITLE_NEW   = "새로운 게시판 제목";
    private static final String POST_CONTENT_NEW = "새로운 게시판 내용입니다.\n게시판 내용은 간소화하였습니다.\n\n";

    BoardPostService boardPostService;

    @BeforeEach
    void setUp() {
        // TODO: init BoardPostService
    }

    @Test
    @DisplayName("[게시글 신규] 1.유효하지 않은 매개변수")
    void createPost_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시글 조회 - 페이지] 1.유효하지 않은 매개변수")
    void getPostPage_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시글 조회 - 단건] 1.유효하지 않은 매개변수")
    void getPost_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시글 갱신] 1.유효하지 않은 매개변수")
    void updatePost_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시글 삭제] 1.유효하지 않은 매개변수")
    void deletePost_whenInvalidParam_thenThrowException() {

    }
}
