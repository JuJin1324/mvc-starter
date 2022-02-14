package practice.mvcstarter.domain.files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import practice.mvcstarter.exceptions.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocalFileService implements FileService {
    public static final String RESOURCE_NAME = "File";

    //    private static final String STORE_DIR_PATH = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files";
    private static final String STORE_DIR_PATH = "/Users/ju-jinyoo/Documents/dev/workspace-git-jujin/mvc-starter/src/test/resources/files";

    private final FileRepository fileRepository;

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

        String storeFilePath = createStoreFilename(fileName);
        try {
            OutputStream outputStream = new FileOutputStream(storeFilePath);
            outputStream.write(Base64.getDecoder().decode(base64Image));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new StoreFileException(fileName);
        }

        File newFile = File.createFile(contentType, storeFilePath, fileName, (long) base64Image.getBytes().length);
        newFile.expireAfter(2, ChronoUnit.WEEKS);
        fileRepository.save(newFile);

        return newFile;
    }

    /**
     * 파일 조회
     */
    @Override
    public FileReadDto getFile(Long fileId) {
        if (fileId == null) {
            throw new IllegalArgumentException();
        }

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME));
        if (file.isExpired()) {
            throw new ExpiredFileException(file.getUploadFileName());
        }

        try {
            UrlResource urlResource = new UrlResource("file:" + file.getStoreFilePath());
            if (!urlResource.exists()) {
                throw new ResourceNotFoundException(file.getUploadFileName());
            }
            return new FileReadDto(file, urlResource);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException(file.getUploadFileName());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ReadFileException(file.getUploadFileName());
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
                    java.io.File storedFile = new java.io.File(file.getStoreFilePath());
                    if (storedFile.exists()) {
                        boolean isDeleted = storedFile.delete();
                        if (!isDeleted) {
                            throw new DeleteFileException(file.getUploadFileName());
                        }
                    }
                    fileRepository.delete(file);
                });
    }

    private String createStoreFilename(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return STORE_DIR_PATH + "/" + uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
