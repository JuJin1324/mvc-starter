package practice.mvcstarter.domain.files.service;

import practice.mvcstarter.domain.files.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.files.dto.FileReadDto;
import practice.mvcstarter.domain.files.dto.FileResourceReadDto;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.files.entity.File;
import practice.mvcstarter.exceptions.ExpiredFileException;
import practice.mvcstarter.exceptions.ReadFileException;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
public interface FileService {
    /**
     * 파일 업로드 - base64
     */
    File uploadBase64(String fileName, ContentType contentType, String base64Image);

    /**
     * 파일 리소스 조회 - 단건
     */
    FileResourceReadDto getFileResource(Long fileId) throws ExpiredFileException, ReadFileException;

    /**
     * 파일 base64 조회 - 단건
     */
    FileBase64ReadDto getFileBase64(Long fileId) throws ExpiredFileException, ReadFileException;

    /**
     * 파일 제거
     */
    void deleteFile(Long fileId);
}
