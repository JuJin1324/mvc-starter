package practice.mvcstarter.domain.boards.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.boards.dto.*;
import practice.mvcstarter.domain.boards.entity.BoardComment;
import practice.mvcstarter.domain.boards.entity.BoardTopic;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

public interface BoardService {

    /**
     * 게시글 저장
     *
     * @return memberId
     */
    Long createBoard(BoardCreateDto dto);

    /**
     * 전체 게시글 조회 - 페이지
     */
    Page<BoardReadDto> getAllBoardPage(Pageable pageable);

    /**
     * 토픽 게시글 조회 - 페이지
     */
    Page<BoardReadDto> getTopicBoardPage(BoardTopic topic, Pageable pageable);

    /**
     * 게시글 상세 조회 - 단건
     */
    BoardDetailReadDto getBoardDetail(Long boardId);

    /**
     * 게시글 갱신
     */
    void updateBoard(Long boardId, BoardUpdateDto dto);

    /**
     * 게시글 삭제
     */
    void deleteBoard(Long boardId);

    /**
     * 게시글 숨김 처리
     */
    void hideBoard(Long boardId, boolean isHide);

    /**
     * 게시글 신고
     */
    void reportBoard(Long boardId, String reason);

    /**
     * 게시글 댓글 조회 - 페이지
     */
    Page<BoardCommentReadDto> getBoardCommentPage(Long boardId, Pageable pageable);

    /**
     * 게시글 댓글 저장
     */
    void createBoardComment(Long boardId, Long parentCommentId, BoardCommentCreateDto dto);

    /**
     * 게시글 댓글 수정
     */
    void updateBoardComment(Long boardId, Long commentId, BoardCommentUpdateDto dto);

    /**
     * 게시글 댓글 삭제
     */
    void deleteBoardComment(Long boardId, Long commentId);

    /**
     * 게시글 댓글 신고
     */
    void reportBoardComment(Long boardId, Long commentId, String reason);

    /**
     * 게시글 댓글 숨김 처리
     */
    void hideBoardComment(Long boardId, Long commentId, boolean isHide);

    /**
     * 관심글 체크 저장
     */
    void saveLikeBoard(Long memberId, Long boardId, boolean isLiked);

    /**
     * 특정 회원의 관심 게시글 조회 - 페이지
     */
    Page<BoardReadDto> getAttractiveBoardPage(Long memberId, Pageable pageable);

    /**
     * 특정 회원이 작성한 게시글 조회 - 페이지
     */
    Page<BoardReadDto> getMineBoardPage(Long memberId, Pageable pageable);
}
