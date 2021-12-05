package practice.mvcstarter.domain.teams;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/15
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    @Builder
    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Team createTeam(String name) {
        return Team.builder()
                .name(name)
                .build();
    }

    public void update(String name) {
        this.name = name;
    }
}
