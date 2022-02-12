package practice.mvcstarter.domain.boards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.mvcstarter.domain.members.MemberDto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    private static final Long   BOARD_ID           = 777L;
    private static final Long   BOARD_ID_NOT_EXIST = 999999999L;
    private static final String BOARD_TITLE         = "게시판 제목";
    private static final String BOARD_CONTENT  = "게시판 내용입니다.\n게시판 내용을 채워보았습니다.\n이 게시물은 JUnit 테스트 게시물입니다.\n감사합니다.\n\n";

    private static final String BOARD_TITLE_NEW = "새로운 게시판 제목";
    private static final String BOARD_CONTENT_NEW = "새로운 게시판 내용입니다.\n게시판 내용은 간소화하였습니다.\n\n";

    @Mock
    private BoardRepository boardRepository;

    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardService = new BoardServiceImpl(boardRepository);
    }

    @Test
    @DisplayName("[게시판 생성] 1.유효하지 않은 매개변수")
    void createBoard_whenInvalidParam_thenThrowException() {
        /* 매개변수에 null */
        assertThrows(IllegalArgumentException.class, () -> boardService.createBoard(null));

        /* 제목 blank */
        assertThrows(IllegalArgumentException.class, () ->
                boardService.createBoard(BoardDto.toCreate(null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                boardService.createBoard(BoardDto.toCreate("", null)));

        /* 내용 blank */
        assertThrows(IllegalArgumentException.class, () ->
                boardService.createBoard(BoardDto.toCreate(BOARD_TITLE, null)));
        assertThrows(IllegalArgumentException.class, () ->
                boardService.createBoard(BoardDto.toCreate(BOARD_TITLE, "")));
    }

    @Test
    @DisplayName("[게시판 생성] 2.정상 생성")
    void createBoard_whenNormal_thenReturnBoardId() {
        /* given */
        BoardDto givenDto = BoardDto.toCreate(BOARD_TITLE, BOARD_CONTENT);

        /* when */
        boardService.createBoard(givenDto);

        /* then */
        verify(boardRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("[게시판 조회 - 단건] 1.유효하지 않은 매개변수")
    void getSingleBoard_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시판 조회 - 단건] 2.존재하지 않는 boardId")
    void getSingleBoard_whenNotExistBoardId_thenThrowException() {

    }

    @Test
    @DisplayName("[게시판 조회 - 단건] 3.팀이 없는 게시판 조회")
    void getSingleBoard_whenReadBoardHasNoTeam_thenReturnBoardDto() {

    }

    @Test
    @DisplayName("[게시판 조회 - 단건] 3.팀이 있는 게시판 조회")
    void getSingleBoard_whenReadBoardHasTeam_thenReturnBoardDto() {

    }

    @Test
    @DisplayName("[게시판 갱신] 1.유효하지 않은 매개변수")
    void updateTeam_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시판 갱신] 2.존재하지 않는 boardId")
    void updateBoard_whenNotExistBoardId_thenThrowException() {

    }

    @Test
    @DisplayName("[게시판 갱신] 3.정상 갱신")
    void updateTeam_whenNormal_thenUpdateTeam() {

    }

    @Test
    @DisplayName("[게시판 삭제] 1.유효하지 않은 매개변수")
    void deleteBoard_whenInvalidParam_thenThrowException() {

    }

    @Test
    @DisplayName("[게시판 삭제] 2.존재하지 않는 boardId")
    void deleteBoard_whenNotExistBoardId_thenThrowException() {

    }

    @Test
    @DisplayName("[게시판 삭제] 3.정상 삭제")
    void deleteBoard_whenNormal_thenUpdateTeam() {

    }
}
