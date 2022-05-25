package practice.mvcstarter.domain.file.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import practice.mvcstarter.domain.file.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.file.dto.FileResourceReadDto;
import practice.mvcstarter.domain.file.entity.ContentType;
import practice.mvcstarter.domain.file.entity.FileUpload;
import practice.mvcstarter.domain.file.exception.*;
import practice.mvcstarter.domain.file.repository.FileUploadRepository;
import practice.mvcstarter.external.service.file.FileClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.temporal.ChronoUnit;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {
    private final FileClient           fileClient;
    private final FileUploadRepository fileUploadRepository;

    @Transactional
    @Override
    public FileUpload uploadBase64(String fileName, ContentType contentType, String base64Image) {
        if (!StringUtils.hasText(fileName)) {
            throw new IllegalArgumentException();
        }
        if (contentType == null) {
            throw new IllegalArgumentException();
        }
        if (!StringUtils.hasText(base64Image)) {
            throw new IllegalArgumentException();
        }

        try {
            String storeFilePath = fileClient.uploadBase64(contentType, base64Image, null);
            FileUpload newFileUpload = FileUpload.createFile(contentType, storeFilePath, fileName, (long) base64Image.getBytes().length);
            newFileUpload.expireAfter(2, ChronoUnit.WEEKS);
            fileUploadRepository.save(newFileUpload);
            return newFileUpload;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new StoreFileException(fileName);
        }
    }

    /**
     * 파일 업로드 Multipart
     */
    @Transactional
    @Override
    public FileUpload uploadFile(MultipartFile multipartFile) {
        String uploadFileName = multipartFile.getOriginalFilename();
        try {
            String storedFilePath = fileClient.uploadFile(multipartFile, null);
            ContentType contentType = ContentType.getValueOf(multipartFile.getContentType());
            FileUpload newFileUpload = FileUpload.createFile(contentType, storedFilePath, uploadFileName, multipartFile.getSize());
            newFileUpload.expireAfter(2, ChronoUnit.WEEKS);
            fileUploadRepository.save(newFileUpload);
            return newFileUpload;
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new StoreFileException(uploadFileName);
        }
    }

    /**
     * 파일 리소스 조회 - 단건
     */
    @Override
    public FileResourceReadDto getFileResource(Long fileId) throws ExpiredFileException, ReadFileException {
        if (fileId == null) {
            throw new IllegalArgumentException();
        }

        FileUpload fileUpload = fileUploadRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId));
        if (fileUpload.isExpired()) {
            throw new ExpiredFileException(fileUpload.getUploadFileName());
        }

        try {
            UrlResource urlResource = new UrlResource("file:" + fileUpload.getStoreFilePath());
            if (!urlResource.exists()) {
                throw new FileNotFoundException(fileUpload.getUploadFileName());
            }
            return new FileResourceReadDto(fileUpload, urlResource);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new FileNotFoundException(fileUpload.getUploadFileName());
        }
    }

    /**
     * 파일 base64 조회 - 단건
     */
    @Override
    public FileBase64ReadDto getFileBase64(Long fileId) throws ExpiredFileException, ReadFileException {
        FileResourceReadDto fileResource = getFileResource(fileId);
        try {
            return new FileBase64ReadDto(fileResource);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ReadFileException(fileResource.getFileName());
        }
    }

    /**
     * 파일 제거
     */
    @Transactional
    @Override
    public void deleteFile(Long fileId) {
        if (fileId == null) {
            throw new IllegalArgumentException();
        }

        fileUploadRepository.findById(fileId)
                .ifPresent(file -> {
                    boolean isDeleted = fileClient.deleteFile(file.getStoreFilePath());
                    if (!isDeleted) {
                        throw new DeleteFileException(file.getUploadFileName());
                    }
                    fileUploadRepository.delete(file);
                });
    }
}
