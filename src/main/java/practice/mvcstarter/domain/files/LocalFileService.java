package practice.mvcstarter.domain.files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.exceptions.ExpiredFileException;
import practice.mvcstarter.exceptions.ReadFileException;
import practice.mvcstarter.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;

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

    private final FileRepository fileRepository;

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
}
