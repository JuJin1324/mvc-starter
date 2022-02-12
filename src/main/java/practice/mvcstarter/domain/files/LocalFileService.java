package practice.mvcstarter.domain.files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import practice.mvcstarter.exceptions.ExpiredFileException;
import practice.mvcstarter.exceptions.ReadFileException;
import practice.mvcstarter.exceptions.ResourceNotFoundException;
import practice.mvcstarter.exceptions.StoreFileException;

import java.io.*;
import java.net.MalformedURLException;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    private static final String STORE_DIR_PATH = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files";

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
    public FileDto getFile(Long fileId) {
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
            return FileDto.toRead(file, urlResource);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException(file.getUploadFileName());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ReadFileException(file.getUploadFileName());
        }
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
