package practice.mvcstarter.domain.files.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.exceptions.FileIsNotBase64Exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/14
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */

@Getter
public class FileBase64ReadDto {
    private final Long        fileId;
    private final String      fileName;

    private final ContentType contentType;
    private final String      base64Image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime expiredTimeKST;

    public FileBase64ReadDto(FileResourceReadDto fileResourceReadDto) throws IOException {
        if (!fileResourceReadDto.isImage()) {
            throw new FileIsNotBase64Exception(fileResourceReadDto.getFileName());
        }
        this.fileId = fileResourceReadDto.getFileId();
        this.fileName = fileResourceReadDto.getFileName();
        this.contentType = fileResourceReadDto.getContentType();
        this.base64Image = Base64.getEncoder()
                .encodeToString(fileResourceReadDto.getResource().getInputStream().readAllBytes());
        this.expiredTimeKST = fileResourceReadDto.getExpiredTimeKST();
    }
}
