package practice.mvcstarter.domain.members.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.boards.entity.Board;
import practice.mvcstarter.domain.boards.entity.Comment;
import practice.mvcstarter.domain.files.entity.File;
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
public class Member {
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
    private final List<Comment> myComments = new ArrayList<>();

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

    public void updateProfile(File file) {
        if (!file.isImage()) {
            throw new FileIsNotBase64Exception(file.getUploadFileName());
        }

        Optional<MemberFile> optional = this.findMemberFile(MemberFileType.PROFILE);
        if (optional.isPresent()) {
            MemberFile profile = optional.get();
            profile.updateFile(file);
        } else {
            MemberFile newProfile = MemberFile.createProfile(this, file);
            memberFiles.add(newProfile);
        }
    }

    public Optional<File> getProfile() {
        return this.findMemberFile(MemberFileType.PROFILE)
                .map(MemberFile::getFile);
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
