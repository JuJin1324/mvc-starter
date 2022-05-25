package practice.mvcstarter.domain.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.mvcstarter.domain.board.repository.BoardRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    private BoardService boardService;

    @BeforeEach
    void setUp() {
        // TODO: init BoardService
    }

    @Test
    @DisplayName("[게시판 조회 - 페이지] 1.유효하지 않은 매개변수")
    void getBoardPage_whenInvalidParam_thenThrowException() {

    }
}
