package practice.mvcstarter.domain.users.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import practice.mvcstarter.domain.base.entity.TimeBaseEntity;
import practice.mvcstarter.domain.files.entity.FileStore;

import javax.persistence.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserFile extends TimeBaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_file_id")
    private Long id;

    @Convert(converter = UserFileTypeConverter.class)
    private UserFileType fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FileStore fileStore;

    @Builder
    private UserFile(UserFileType fileType, User user, FileStore fileStore) {
        this.fileType = fileType;
        this.user = user;
        this.fileStore = fileStore;
    }

    public static UserFile createProfile(User user, FileStore fileStore) {
        return UserFile.builder()
                .fileType(UserFileType.PROFILE)
                .member(user)
                .fileStore(fileStore)
                .build();
    }

    public void updateFile(FileStore fileStore) {
        this.fileStore = fileStore;
    }
}
