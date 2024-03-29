package practice.mvcstarter.external.service.file;

import org.springframework.web.multipart.MultipartFile;
import practice.mvcstarter.domain.file.entity.ContentType;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/15
 */
public interface FileClient {

    /**
     * 파일 업로드 - base64
     *
     * @return storedFilePath
     */
    String uploadBase64(ContentType contentType, String base64Image, @Nullable String storeDirPath) throws IOException;

    /**
     * 파일 업로드 Multipart
     *
     * @return storedFilePath
     */
    String uploadFile(MultipartFile multipartFile, @Nullable String storeDirPath) throws IOException;

    /**
     * 파일 제거
     *
     * @return isDeleted : 파일이 경로 없는 경우에도 true 반환
     */
    boolean deleteFile(String storedFilePath);
}
