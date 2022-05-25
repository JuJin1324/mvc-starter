package practice.mvcstarter.domain.users.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.base.entity.TimeBaseEntity;
import practice.mvcstarter.domain.boards.entity.PostComment;
import practice.mvcstarter.domain.files.entity.FileUpload;
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
    private Long id;

    private String nickname;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<UserFileUpload> userFileUploads = new ArrayList<>();

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<PostComment> myPostComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<UserLikeBoard> likeBoards = new ArrayList<>();

    public User(String nickname) {
        this.nickname = nickname;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfilePhoto(FileUpload profileFile) {
        if (!profileFile.isImage()) {
            throw new FileIsNotBase64Exception(profileFile.getUploadFileName());
        }

        Optional<UserFileUpload> optional = this.findMemberFile(UserFileType.PROFILE);
        if (optional.isPresent()) {
            UserFileUpload profile = optional.get();
            profile.updateFile(profileFile);
        } else {
            UserFileUpload newProfile = new UserFileUpload(UserFileType.PROFILE, this, profileFile);
            userFileUploads.add(newProfile);
        }
    }

    public Optional<FileUpload> getProfile() {
        return this.findMemberFile(UserFileType.PROFILE)
                .map(UserFileUpload::getFileUpload);
    }

    private Optional<UserFileUpload> findMemberFile(UserFileType fileType) {
        if (this.getUserFileUploads().isEmpty()) {
            return Optional.empty();
        }
        return this.getUserFileUploads().stream()
                .filter(userFileUpload -> userFileUpload.getFileType() == fileType)
                .findAny();
    }
}
