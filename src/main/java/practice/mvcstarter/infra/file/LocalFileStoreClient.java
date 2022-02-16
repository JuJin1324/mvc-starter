package practice.mvcstarter.infra.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import practice.mvcstarter.domain.files.entity.ContentType;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/15
 */

@Service
@Slf4j
public class LocalFileStoreClient implements FileStoreClient {
    private static final String STORE_DIR_PATH = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files";
//    private static final String STORE_DIR_PATH = "/Users/ju-jinyoo/Documents/dev/workspace-git-jujin/mvc-starter/src/test/resources/files";

    @Override
    public String uploadBase64(ContentType contentType, String base64Image, @Nullable String storeDirPath) throws IOException {
        if (contentType == null) {
            throw new IllegalArgumentException();
        }
        if (!StringUtils.hasText(base64Image)) {
            throw new IllegalArgumentException();
        }

        String storeFilePath = createStoreFilePath(storeDirPath, contentType);
        OutputStream outputStream = new FileOutputStream(storeFilePath);
        outputStream.write(Base64.getDecoder().decode(base64Image));
        return storeFilePath;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, @Nullable String storeDirPath) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        ContentType contentType = ContentType.getValueOf(multipartFile.getContentType());
        String storedFilePath = createStoreFilePath(storeDirPath, contentType);
        multipartFile.transferTo(new File(storedFilePath));
        return storedFilePath;
    }

    @Override
    public boolean deleteFile(String storedFilePath) {
        java.io.File storedFile = new java.io.File(storedFilePath);
        if (!storedFile.exists()) {
            return true;
        }

        return storedFile.delete();
    }

    private String createStoreFilePath(String storeDirPath, ContentType contentType) {
        String ext = ContentType.getExtension(contentType);
        String uuid = UUID.randomUUID().toString();
        return STORE_DIR_PATH +
                (StringUtils.hasText(storeDirPath) ? "/" + storeDirPath : "") + "/" + uuid + "." + ext;
    }
}
