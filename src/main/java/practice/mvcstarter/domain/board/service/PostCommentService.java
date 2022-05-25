package practice.mvcstarter.domain.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.board.dto.PostCommentCreateDto;
import practice.mvcstarter.domain.board.dto.PostCommentReadDto;
import practice.mvcstarter.domain.board.dto.PostCommentUpdateDto;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/03/15
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */
public interface PostCommentService {
    /**
     * 게시글 댓글 신규
     */
    void createComment(Long memberId, Long postId, PostCommentCreateDto dto);

    /**
     * 게시글 대댓글 신규
     */
    void createChildComment(Long memberId, Long postId, Long parentCommentId, PostCommentCreateDto dto);

    /**
     * 게시글 댓글 조회 - 페이지
     */
    Page<PostCommentReadDto> getCommentPage(Long postId, Pageable pageable);

    /**
     * 게시글 댓글 수정
     */
    void updateComment(Long memberId, Long commentId, PostCommentUpdateDto dto);

    /**
     * 게시글 댓글 삭제
     */
    void deleteComment(Long memberId, Long commentId);
}
