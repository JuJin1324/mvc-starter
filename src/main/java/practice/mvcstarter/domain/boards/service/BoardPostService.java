package practice.mvcstarter.domain.boards.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.boards.dto.*;
import practice.mvcstarter.domain.boards.entity.BoardType;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/03/15
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */
public interface BoardPostService {
    /**
     * 게시글 신규
     *
     * @return postId
     */
    Long createPost(Long memberId, Long boardId, BoardPostCreateDto dto);

    /**
     * 게시글 조회 - 페이지
     */
    Page<BoardPostReadDto> getPostPage(Long boardId, Pageable pageable);

    /**
     * 게시글 조회 - 단건
     */
    BoardPostReadDto getPost(Long boardId, Long postId);

    /**
     * 게시글 갱신
     */
    void updatePost(Long memberId, Long postId, BoardPostUpdateDto dto);

    /**
     * 게시글 삭제
     */
    void deletePost(Long memberId, Long postId);
}
