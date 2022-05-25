package practice.mvcstarter.domain.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@AllArgsConstructor
@Getter
public enum BoardType {
    FREE_BOARD("FR"),
    AFTER_MARKET("AM"),
    STOCK_MARKET("SM"),
    ;

    private final String value;
}
