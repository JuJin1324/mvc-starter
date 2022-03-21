package practice.mvcstarter.domain.boards.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.base.entity.TimeBaseEntity;
import practice.mvcstarter.domain.users.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/12/07
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends TimeBaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String name;

    @Convert(converter = BoardTypeConverter.class)
    private BoardType boardType;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<BoardPost> boardPosts = new ArrayList<>();

    public Board(Long id, String name, BoardType boardType) {
        this.id = id;
        this.name = name;
        this.boardType = boardType;
    }

    public void addPost(String title, String content, User writer) {
        boardPosts.add(new BoardPost(title, content, this, writer));
    }
}
