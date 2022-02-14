package practice.mvcstarter.domain.files;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@Getter
public class FileReadDto {
    private final Long          fileId;
    private final String        fileName;
    private final ContentType   contentType;
    private final Resource      resource;
    private       String        base64Image;
    private final LocalDateTime expiredTimeKST;
    private final boolean       isImage;

    public FileReadDto(File file, Resource resource) throws IOException {
        this.fileId = file.getId();
        this.fileName = file.getUploadFileName();
        this.contentType = file.getContentType();
        this.resource = resource;
        if (file.isImage()) {
            this.base64Image = Base64.getEncoder()
                    .encodeToString(resource.getInputStream().readAllBytes());
        }
        this.expiredTimeKST = file.getExpiredTimeKST();
        this.isImage = file.isImage();
    }
}
