package practice.mvcstarter.domain.board.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.board.dto.BoardPostCreateDto;
import practice.mvcstarter.domain.board.dto.BoardPostReadDto;
import practice.mvcstarter.domain.board.dto.BoardPostUpdateDto;
import practice.mvcstarter.domain.board.service.BoardPostService;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/03/15
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class BoardPostServiceImpl implements BoardPostService {
    @Override
    public Long createPost(Long memberId, Long boardId, BoardPostCreateDto dto) {
        return null;
    }

    @Override
    public Page<BoardPostReadDto> getPostPage(Long boardId, Pageable pageable) {
        return null;
    }

    @Override
    public BoardPostReadDto getPost(Long boardId, Long postId) {
        return null;
    }

    @Override
    public void updatePost(Long memberId, Long postId, BoardPostUpdateDto dto) {

    }

    @Override
    public void deletePost(Long memberId, Long postId) {

    }
}
