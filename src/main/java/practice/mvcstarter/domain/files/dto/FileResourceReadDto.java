package practice.mvcstarter.domain.files.dto;

import lombok.Getter;
import org.springframework.core.io.Resource;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.files.entity.FileStore;

import java.time.LocalDateTime;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Getter
public class FileResourceReadDto {
    private final Long          fileId;
    private final String        fileName;
    private final ContentType   contentType;
    private final Resource      resource;
    private final LocalDateTime expiredTimeKST;
    private final boolean       isImage;

    public FileResourceReadDto(FileStore fileStore, Resource resource) {
        this.fileId = fileStore.getId();
        this.fileName = fileStore.getUploadFileName();
        this.contentType = fileStore.getContentType();
        this.resource = resource;
        this.expiredTimeKST = fileStore.getExpiredTimeKST();
        this.isImage = fileStore.isImage();
    }
}
