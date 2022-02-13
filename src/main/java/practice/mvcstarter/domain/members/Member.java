package practice.mvcstarter.domain.members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.files.File;
import practice.mvcstarter.exceptions.FileIsNotBase64Exception;

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
    private Long id;

    private String name;

    private String nickName;

    private Integer age;

    @Getter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<MemberFile> memberFiles = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String nickName, Integer age) {
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

        Optional<MemberFile> optional = this.findMemberFile(FileType.PROFILE);
        if (optional.isPresent()) {
            MemberFile profile = optional.get();
            profile.updateFile(file);
        } else {
            MemberFile newProfile = MemberFile.createProfile(this, file);
            memberFiles.add(newProfile);
        }
    }

    public Optional<File> getProfile() {
        return this.findMemberFile(FileType.PROFILE)
                .map(MemberFile::getFile);
    }

    private Optional<MemberFile> findMemberFile(FileType fileType) {
        if (this.getMemberFiles().isEmpty()) {
            return Optional.empty();
        }
        return this.getMemberFiles().stream()
                .filter(memberFile -> memberFile.getFileType() == fileType)
                .findAny();
    }
}
