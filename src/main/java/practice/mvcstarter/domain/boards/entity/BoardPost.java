package practice.mvcstarter.domain.boards.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.members.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/16
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPost {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private long readCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private LocalDateTime lastModifiedDateUTC;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<BoardFile> attachedFiles = new ArrayList<>();

    public BoardPost(String title, String content, Board board, Member writer) {
        this.title = title;
        this.content = content;
        this.readCount = 0;
        this.board = board;
        this.writer = writer;
    }


}
