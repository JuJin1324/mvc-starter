package practice.mvcstarter.domain.members.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import practice.mvcstarter.domain.files.entity.FileStore;

import javax.persistence.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberFile {
    @Id
    @GeneratedValue
    @Column(name = "member_file_id")
    private Long id;

    @Convert(converter = MemberFileTypeConverter.class)
    private MemberFileType fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FileStore fileStore;

    @Builder
    private MemberFile(MemberFileType fileType, Member member, FileStore fileStore) {
        this.fileType = fileType;
        this.member = member;
        this.fileStore = fileStore;
    }

    public static MemberFile createProfile(Member member, FileStore fileStore) {
        return MemberFile.builder()
                .fileType(MemberFileType.PROFILE)
                .member(member)
                .file(fileStore)
                .build();
    }

    public void updateFile(FileStore fileStore) {
        this.fileStore = fileStore;
    }
}
