package practice.mvcstarter.domain.boards;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    public static final String RESOURCE_NAME = "Board";

    private final BoardRepository boardRepository;

    /**
     * 게시판 생성
     *
     * @return memberId
     */
    @Transactional
    public Long createBoard(BoardDto dto) {

        return null;
    }

    /**
     * 게시판 조회 - 단건
     */
    public BoardDto getSingleBoard(Long boardId) {

        return null;
    }

    /**
     * 게시판 조회 - 페이지
     */
    public Page<BoardDto> getBoardPage(Pageable pageable) {
        return null;
    }

    /**
     * 게시판 갱신
     */
    @Transactional
    public void updateBoard(Long boardId, BoardDto dto) {

    }

    /**
     * 게시판 삭제
     */
    @Transactional
    public void deleteBoard(Long boardId) {

    }

    @Override
    public Page<BoardDto> getAttractiveBoardPage(Long memberId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<BoardDto> getMineBoardPage(Long memberId, Pageable pageable) {
        return null;
    }
}
