package practice.mvcstarter.domain.users.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.base.entity.TimeBaseEntity;
import practice.mvcstarter.domain.boards.entity.Board;
import practice.mvcstarter.domain.boards.entity.PostComment;
import practice.mvcstarter.domain.files.entity.FileStore;
import practice.mvcstarter.domain.files.exception.FileIsNotBase64Exception;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/04
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeBaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String nickName;

    private Integer age;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<UserFile> userFiles = new ArrayList<>();

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Board> myBoards = new ArrayList<>();

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<PostComment> myPostComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<UserLikeBoard> likeBoards = new ArrayList<>();

    @Builder
    private User(Long id, String name, String nickName, Integer age) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
    }

    public static User createMember(String name, String nickName, Integer age) {
        return User.builder()
                .name(name)
                .nickName(nickName)
                .age(age)
                .build();
    }

    public void update(String nickName, Integer age) {
        this.nickName = nickName;
        this.age = age;
    }

    public void updateProfile(FileStore fileStore) {
        if (!fileStore.isImage()) {
            throw new FileIsNotBase64Exception(fileStore.getUploadFileName());
        }

        Optional<UserFile> optional = this.findMemberFile(UserFileType.PROFILE);
        if (optional.isPresent()) {
            UserFile profile = optional.get();
            profile.updateFile(fileStore);
        } else {
            UserFile newProfile = UserFile.createProfile(this, fileStore);
            userFiles.add(newProfile);
        }
    }

    public Optional<FileStore> getProfile() {
        return this.findMemberFile(UserFileType.PROFILE)
                .map(UserFile::getFileStore);
    }

    private Optional<UserFile> findMemberFile(UserFileType fileType) {
        if (this.getUserFiles().isEmpty()) {
            return Optional.empty();
        }
        return this.getUserFiles().stream()
                .filter(userFile -> userFile.getFileType() == fileType)
                .findAny();
    }
}
