package practice.mvcstarter.domain.boards.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.boards.dto.PostCommentCreateDto;
import practice.mvcstarter.domain.boards.dto.PostCommentReadDto;
import practice.mvcstarter.domain.boards.dto.PostCommentUpdateDto;
import practice.mvcstarter.domain.boards.service.PostCommentService;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/03/15
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    @Override
    public void createComment(Long memberId, Long postId, PostCommentCreateDto dto) {

    }

    @Override
    public void createChildComment(Long memberId, Long postId, Long parentCommentId, PostCommentCreateDto dto) {

    }

    @Override
    public Page<PostCommentReadDto> getCommentPage(Long postId, Pageable pageable) {
        return null;
    }

    @Override
    public void updateComment(Long memberId, Long commentId, PostCommentUpdateDto dto) {

    }

    @Override
    public void deleteComment(Long memberId, Long commentId) {

    }
}
