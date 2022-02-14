package practice.mvcstarter.domain.boards;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.members.Member;

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
public class Board {
    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String content;

    private long readCount;

    @Convert(converter = BoardTopicConverter.class)
    private BoardTopic boardTopic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<BoardComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<BoardFile> attachedFiles = new ArrayList<>();

    public Board(String title, String content, BoardTopic boardTopic, Member writer) {
        this.title = title;
        this.content = content;
        this.readCount = 0;
        this.boardTopic = boardTopic;
        this.writer = writer;
    }
}
