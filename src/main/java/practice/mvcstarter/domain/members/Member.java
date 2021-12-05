package practice.mvcstarter.domain.members;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.teams.Team;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Member(Long id, String name, String nickName, Integer age, Team team) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.team = team;
    }

    public static Member createMember(String name, String nickName, Integer age) {
        return Member.builder()
                .name(name)
                .nickName(nickName)
                .age(age)
                .build();
    }

    public void update(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void updateTeam(Team team) {
        this.team = team;
    }

    public boolean hasTeam() {
        return this.getTeam() != null;
    }
}
