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
//public class BoardServiceImpl implements BoardService {
public class BoardServiceImpl {
    public static final String RESOURCE_NAME = "Board";

    private final BoardRepository boardRepository;

}
