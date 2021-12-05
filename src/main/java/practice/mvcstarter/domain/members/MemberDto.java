package practice.mvcstarter.domain.members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;
import practice.mvcstarter.domain.teams.TeamDto;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@Getter
public class MemberDto {
    private Long    memberId;
    private String  name;
    private String  nickName;
    private Integer age;
    private TeamDto team;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberDto(Long memberId, String name, String nickName, Integer age, TeamDto team) {
        this.memberId = memberId;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.team = team;
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

    public static MemberDto toRead(Member member) {
        return MemberDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .age(member.getAge())
                .team(member.hasTeam() ? TeamDto.toRead(member.getTeam()) : null)
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
