package practice.mvcstarter.domain.members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import practice.mvcstarter.domain.files.File;

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

    @Convert(converter = FileTypeConverter.class)
    private FileType fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private File file;

    @Builder
    private MemberFile(FileType fileType, Member member, File file) {
        this.fileType = fileType;
        this.member = member;
        this.file = file;
    }

    public static MemberFile createProfile(Member member, File file) {
        return MemberFile.builder()
                .fileType(FileType.PROFILE)
                .member(member)
                .file(file)
                .build();
    }

    public void updateFile(File file) {
        this.file = file;
    }
}
