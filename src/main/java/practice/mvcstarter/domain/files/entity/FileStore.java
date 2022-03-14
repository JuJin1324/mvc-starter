package practice.mvcstarter.domain.files.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FileStore {
    @Id
    @GeneratedValue
    @Column(name = "file_store_id")
    private Long id;

    @Convert(converter = ContentTypeConverter.class)
    private ContentType contentType;

    private String storeFilePath;

    private String uploadFileName;

    private Long fileSize;

    private LocalDateTime expiredTimeUTC;

    @Builder
    private FileStore(Long id, ContentType contentType, String storeFilePath, String uploadFileName, Long fileSize, LocalDateTime expiredTimeUTC) {
        this.id = id;
        this.contentType = contentType;
        this.storeFilePath = storeFilePath;
        this.uploadFileName = uploadFileName;
        this.fileSize = fileSize;
        this.expiredTimeUTC = expiredTimeUTC;
    }

    public static FileStore createFile(ContentType contentType, String storeFilePath, String uploadFileName, Long fileSize) {
        return FileStore.builder()
                .contentType(contentType)
                .storeFilePath(storeFilePath)
                .uploadFileName(uploadFileName)
                .fileSize(fileSize)
                .build();
    }

    public void expireAfter(int amountToAdd, ChronoUnit chronoUnit) {
        this.expiredTimeUTC = LocalDateTime.now(ZoneId.of("UTC"))
                .plus(amountToAdd, chronoUnit);
    }

    public LocalDateTime getExpiredTimeKST() {
        return ZonedDateTime.of(expiredTimeUTC, ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
    }

    public boolean isImage() {
        return contentType == ContentType.IMAGE_JPEG ||
                contentType == ContentType.IMAGE_PNG;
    }

    public boolean isExpired() {
        return LocalDateTime.now(ZoneId.of("UTC")).isAfter(expiredTimeUTC);
    }
}
