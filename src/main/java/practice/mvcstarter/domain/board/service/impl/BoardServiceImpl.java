package practice.mvcstarter.domain.board.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.board.dto.BoardReadDto;
import practice.mvcstarter.domain.board.repository.BoardRepository;
import practice.mvcstarter.domain.board.service.BoardService;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<BoardReadDto> getBoardPage(Pageable pageable) {
        return null;
    }
}
