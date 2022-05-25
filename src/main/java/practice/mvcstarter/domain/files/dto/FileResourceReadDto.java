package practice.mvcstarter.domain.files.dto;

import lombok.Getter;
import org.springframework.core.io.Resource;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.files.entity.FileUpload;

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

    public FileResourceReadDto(FileUpload fileUpload, Resource resource) {
        this.fileId = fileUpload.getId();
        this.fileName = fileUpload.getUploadFileName();
        this.contentType = fileUpload.getContentType();
        this.resource = resource;
        this.expiredTimeKST = fileUpload.getExpiredTimeKST();
        this.isImage = fileUpload.isImage();
    }
}
