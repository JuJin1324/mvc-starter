package practice.mvcstarter.domain.members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;
import practice.mvcstarter.domain.files.ContentType;
import practice.mvcstarter.domain.files.FileDto;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@Getter
public class MemberDto {
    private final Long    memberId;
    private final String  name;
    private final String  nickName;
    private final Integer age;

    private final ContentType contentType;
    private final String      base64Image;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberDto(Long memberId, String name, String nickName, Integer age, ContentType contentType, String base64Image) {
        this.memberId = memberId;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.contentType = contentType;
        this.base64Image = base64Image;
    }

    public static MemberDto toCreate(String name, String nickName, Integer age) {
        return MemberDto.builder()
                .name(name)
                .nickName(nickName)
                .age(age)
                .build();
    }

    public static MemberDto toUpdate(String nickName, Integer age) {
        return MemberDto.builder()
                .nickName(nickName)
                .age(age)
                .build();
    }

    public static MemberDto toUpdateProfile(String base64Image, ContentType contentType) {
        return MemberDto.builder()
                .base64Image(base64Image)
                .contentType(contentType)
                .build();
    }

    public static MemberDto toRead(Member member, Optional<FileDto> fileDtoOptional) {
        ContentType contentType = null;
        String base64Image = null;
        if (fileDtoOptional.isPresent()) {
            FileDto fileDto = fileDtoOptional.get();
            contentType = fileDto.getContentType();
            base64Image = fileDto.getBase64Image();
        }

        return MemberDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .age(member.getAge())
                .contentType(contentType)
                .base64Image(base64Image)
                .build();
    }

    public void validateToCreate() {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("[MemberDto] name is blank.");
        }
        if (!StringUtils.hasText(nickName)) {
            throw new IllegalArgumentException("[MemberDto] nickName is blank.");
        }
        if (age == null) {
            throw new IllegalArgumentException("[MemberDto] age is null.");
        }
    }

    public void validateToUpdate() {
        if (!StringUtils.hasText(nickName)) {
            throw new IllegalArgumentException("[MemberDto] nickName is blank.");
        }
        if (age == null) {
            throw new IllegalArgumentException("[MemberDto] age is null.");
        }
    }

    public void validateToUpdateProfile() {
        /* base64Image 은 null 일 수 있음.
         * 하지만 base64Image 가 null 이 아닌 경우 contentType 이 무조건 있어야 함. */
        if (StringUtils.hasText(base64Image) && contentType == null) {
            throw new IllegalArgumentException("[MemberDto] contentType is null.");
        }
    }

    public boolean hasBase64Image() {
        return StringUtils.hasText(base64Image);
    }
}
