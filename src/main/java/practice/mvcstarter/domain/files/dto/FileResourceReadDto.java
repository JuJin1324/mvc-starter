package practice.mvcstarter.domain.files.dto;

import lombok.Getter;
import org.springframework.core.io.Resource;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.files.entity.File;

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

    public FileResourceReadDto(File file, Resource resource) {
        this.fileId = file.getId();
        this.fileName = file.getUploadFileName();
        this.contentType = file.getContentType();
        this.resource = resource;
        this.expiredTimeKST = file.getExpiredTimeKST();
        this.isImage = file.isImage();
    }
}
