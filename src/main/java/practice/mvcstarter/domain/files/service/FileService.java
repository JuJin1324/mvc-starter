package practice.mvcstarter.domain.files.service;

import org.springframework.web.multipart.MultipartFile;
import practice.mvcstarter.domain.files.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.files.dto.FileResourceReadDto;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.files.entity.File;
import practice.mvcstarter.exceptions.ExpiredFileException;
import practice.mvcstarter.exceptions.ReadFileException;

import java.util.List;

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
     * 파일 업로드 Multipart - 여러건
     */
    List<File> uploadFiles(List<MultipartFile> multipartFiles);

    /**
     * 파일 업로드 Multipart - 단건
     */
    File uploadFile(MultipartFile multipartFile);

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
