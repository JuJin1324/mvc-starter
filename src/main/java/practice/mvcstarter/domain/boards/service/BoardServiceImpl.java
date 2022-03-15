package practice.mvcstarter.domain.boards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.boards.repository.BoardRepository;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
//public class BoardServiceImpl implements BoardService {
public class BoardServiceImpl {
    private final BoardRepository boardRepository;

}
