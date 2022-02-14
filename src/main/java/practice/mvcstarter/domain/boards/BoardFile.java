package practice.mvcstarter.domain.boards;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import practice.mvcstarter.domain.files.File;

import javax.persistence.*;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardFile {
    @Id
    @GeneratedValue
    @Column(name = "board_file_id")
    private Long id;

    @Convert(converter = BoardFileTypeConverter.class)
    private BoardFileType fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private File file;
}
