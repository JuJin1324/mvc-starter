package practice.mvcstarter.domain.members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@Getter
public class MemberDto {
    private final Long   memberId;
    private final String name;
    private final String nickName;
    private final Integer age;

    private final String base64Image;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberDto(Long memberId, String name, String nickName, Integer age, String base64Image) {
        this.memberId = memberId;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.base64Image = base64Image;
    }

    public static MemberDto toCreate(String name, String nickName, Integer age) {
        return MemberDto.builder()
                .name(name)
                .nickName(nickName)
                .age(age)
                .build();
    }

    public static MemberDto toUpdate(String name, Integer age) {
        return MemberDto.builder()
                .name(name)
                .age(age)
                .build();
    }

    public static MemberDto toUpdateProfile(String base64Image) {
        return MemberDto.builder()
                .base64Image(base64Image)
                .build();
    }

    public static MemberDto toRead(Member member) {
        return MemberDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .age(member.getAge())
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
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("[MemberDto] name is blank.");
        }
        if (age == null) {
            throw new IllegalArgumentException("[MemberDto] age is null.");
        }
    }
}
