package practice.mvcstarter.domain.files;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@Getter
public class FileDto {
    private final Long          fileId;
    private final String        fileName;
    private final ContentType   contentType;
    private final Resource      resource;
    private final String        base64Image;
    private final LocalDateTime expiredTimeKST;
    private final boolean       isImage;

    @Builder
    private FileDto(Long fileId, String fileName, ContentType contentType, Resource resource, String base64Image, LocalDateTime expiredTimeKST, boolean isImage) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.resource = resource;
        this.base64Image = base64Image;
        this.expiredTimeKST = expiredTimeKST;
        this.isImage = isImage;
    }

    public static FileDto toRead(File file, Resource resource) throws IOException {
        return FileDto.builder()
                .fileId(file.getId())
                .fileName(file.getUploadFileName())
                .contentType(file.getContentType())
                .resource(resource)
                .base64Image(file.isImage() ?
                        new String(resource.getInputStream().readAllBytes()) : null)
                .expiredTimeKST(file.getExpiredTimeKST())
                .isImage(file.isImage())
                .build();
    }
}
