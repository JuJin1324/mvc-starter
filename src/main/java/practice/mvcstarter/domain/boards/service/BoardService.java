package practice.mvcstarter.domain.boards.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.boards.dto.*;
import practice.mvcstarter.domain.boards.entity.BoardType;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

public interface BoardService {
    /**
     * 게시판 조회 - 페이지
     */
    Page<BoardReadDto> getBoardPage(Pageable pageable);
}
