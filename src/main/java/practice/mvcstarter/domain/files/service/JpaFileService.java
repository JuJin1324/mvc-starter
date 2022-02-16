package practice.mvcstarter.domain.files.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import practice.mvcstarter.domain.files.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.files.dto.FileResourceReadDto;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.files.entity.File;
import practice.mvcstarter.domain.files.exception.DeleteFileException;
import practice.mvcstarter.domain.files.exception.ExpiredFileException;
import practice.mvcstarter.domain.files.exception.FileNotFoundException;
import practice.mvcstarter.domain.files.exception.ReadFileException;
import practice.mvcstarter.domain.files.repository.FileRepository;
import practice.mvcstarter.exceptions.ResourceNotFoundException;
import practice.mvcstarter.domain.files.exception.StoreFileException;
import practice.mvcstarter.infra.file.FileStoreClient;

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
public class JpaFileService implements FileService {
    private final FileStoreClient fileStoreClient;
    private final FileRepository  fileRepository;

    @Transactional
    @Override
    public File uploadBase64(String fileName, ContentType contentType, String base64Image) {
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
            String storeFilePath = fileStoreClient.uploadBase64(contentType, base64Image, null);
            File newFile = File.createFile(contentType, storeFilePath, fileName, (long) base64Image.getBytes().length);
            newFile.expireAfter(2, ChronoUnit.WEEKS);
            fileRepository.save(newFile);
            return newFile;
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
    public File uploadFile(MultipartFile multipartFile) {
        String uploadFileName = multipartFile.getOriginalFilename();
        try {
            String storedFilePath = fileStoreClient.uploadFile(multipartFile, null);
            ContentType contentType = ContentType.getValueOf(multipartFile.getContentType());
            File newFile = File.createFile(contentType, storedFilePath, uploadFileName, multipartFile.getSize());
            newFile.expireAfter(2, ChronoUnit.WEEKS);
            fileRepository.save(newFile);
            return newFile;
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

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId));
        if (file.isExpired()) {
            throw new ExpiredFileException(file.getUploadFileName());
        }

        try {
            UrlResource urlResource = new UrlResource("file:" + file.getStoreFilePath());
            if (!urlResource.exists()) {
                throw new ResourceNotFoundException(file.getUploadFileName());
            }
            return new FileResourceReadDto(file, urlResource);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException(file.getUploadFileName());
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

        fileRepository.findById(fileId)
                .ifPresent(file -> {
                    boolean isDeleted = fileStoreClient.deleteFile(file.getStoreFilePath());
                    if (!isDeleted) {
                        throw new DeleteFileException(file.getUploadFileName());
                    }
                    fileRepository.delete(file);
                });
    }
}