package practice.mvcstarter.domain.boards;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    Long createBoard(BoardDto dto);

    /**
     * 게시판 조회 - 단건
     */
    BoardDto getSingleBoard(Long boardId);

    /**
     * 게시판 조회 - 페이지
     */
    Page<BoardDto> getBoardPage(Pageable pageable);

    /**
     * 게시판 갱신
     */
    void updateBoard(Long boardId, BoardDto dto);

    /**
     * 게시판 삭제
     */
    void deleteBoard(Long boardId);

    /**
     * 특정 회원의 관심 게시글 조회 - 페이지
     */
    Page<BoardDto> getAttractiveBoardPage(Long memberId, Pageable pageable);

    /**
     * 특정 회원이 작성한 게시글 조회 - 페이지
     */
    Page<BoardDto> getMineBoardPage(Long memberId, Pageable pageable);
}
