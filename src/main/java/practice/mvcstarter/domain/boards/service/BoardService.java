package practice.mvcstarter.domain.boards.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.boards.dto.BoardCreateDto;
import practice.mvcstarter.domain.boards.dto.BoardDetailReadDto;
import practice.mvcstarter.domain.boards.dto.BoardReadDto;
import practice.mvcstarter.domain.boards.dto.BoardUpdateDto;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

public interface BoardService {

    /**
     * 게시판 생성
     *
     * @return memberId
     */
    Long createBoard(BoardCreateDto dto);

    /**
     * 게시판 조회 - 단건
     */
    BoardDetailReadDto getBoardDetail(Long boardId);

    /**
     * 게시판 조회 - 페이지
     */
    Page<BoardReadDto> getBoardPage(Pageable pageable);

    /**
     * 게시판 갱신
     */
    void updateBoard(Long boardId, BoardUpdateDto dto);

    /**
     * 게시판 삭제
     */
    void deleteBoard(Long boardId);

    /**
     * 특정 회원의 관심 게시글 조회 - 페이지
     */
    Page<BoardReadDto> getAttractiveBoardPage(Long memberId, Pageable pageable);

    /**
     * 특정 회원이 작성한 게시글 조회 - 페이지
     */
    Page<BoardReadDto> getMineBoardPage(Long memberId, Pageable pageable);
}
