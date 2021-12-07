package practice.mvcstarter.domain.boards;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;


}
