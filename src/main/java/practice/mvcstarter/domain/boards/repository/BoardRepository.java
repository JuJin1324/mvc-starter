package practice.mvcstarter.domain.boards.repository;

import practice.mvcstarter.global.repository.CommonRepository;
import practice.mvcstarter.domain.boards.entity.Board;

import java.util.List;
import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */
public interface BoardRepository extends CommonRepository<Board, Long> {
    Optional<Board> findById(Long id);

    <S extends Board> S save(S entity);

    void delete(Board entity);

    /* For MVC Test Only */
    List<Board> findAllByTitleLike(String titleLike);

    /* For MVC Test Only */
    void deleteAll(Iterable<? extends Board> entities);
}
