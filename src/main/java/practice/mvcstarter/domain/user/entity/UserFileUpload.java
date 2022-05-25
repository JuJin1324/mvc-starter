package practice.mvcstarter.domain.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.base.entity.TimeBaseEntity;
import practice.mvcstarter.domain.file.entity.FileUpload;

import javax.persistence.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserFileUpload extends TimeBaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = UserFileTypeConverter.class)
    private UserFileType fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_upload_id")
    private FileUpload fileUpload;

    public UserFileUpload(UserFileType fileType, User user, FileUpload fileUpload) {
        this.fileType = fileType;
        this.user = user;
        this.fileUpload = fileUpload;
    }

    public void updateFile(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }
}
