package practice.mvcstarter.domain.members.entity;

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
public class Member extends TimeBaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String nickName;

    private Integer age;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<MemberFile> memberFiles = new ArrayList<>();

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Board> myBoards = new ArrayList<>();

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<PostComment> myPostComments = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<MemberLikeBoard> likeBoards = new ArrayList<>();

    @Builder
    private Member(Long id, String name, String nickName, Integer age) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
    }

    public static Member createMember(String name, String nickName, Integer age) {
        return Member.builder()
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

        Optional<MemberFile> optional = this.findMemberFile(MemberFileType.PROFILE);
        if (optional.isPresent()) {
            MemberFile profile = optional.get();
            profile.updateFile(fileStore);
        } else {
            MemberFile newProfile = MemberFile.createProfile(this, fileStore);
            memberFiles.add(newProfile);
        }
    }

    public Optional<FileStore> getProfile() {
        return this.findMemberFile(MemberFileType.PROFILE)
                .map(MemberFile::getFileStore);
    }

    private Optional<MemberFile> findMemberFile(MemberFileType fileType) {
        if (this.getMemberFiles().isEmpty()) {
            return Optional.empty();
        }
        return this.getMemberFiles().stream()
                .filter(memberFile -> memberFile.getFileType() == fileType)
                .findAny();
    }
}
