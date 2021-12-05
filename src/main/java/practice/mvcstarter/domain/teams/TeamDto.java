package practice.mvcstarter.domain.teams;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/15
 */
@Getter
public class TeamDto {
    private Long   teamId;
    private String teamName;
//    private Integer numberOfMembers;

    @Builder(access = AccessLevel.PRIVATE)
    private TeamDto(Long teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    public static TeamDto toCreate(String teamName) {
        return TeamDto.builder()
                .teamName(teamName)
                .build();
    }

    public static TeamDto toRead(Team team) {
        return TeamDto.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .build();
    }

    public static TeamDto toUpdate(String teamName) {
        return TeamDto.builder()
                .teamName(teamName)
                .build();
    }

    public void validateToCreate() {
        if (!StringUtils.hasText(teamName)) {
            throw new IllegalArgumentException("[TeamDto] teamName is blank.");
        }
    }

    public void validateToUpdate() {
        if (!StringUtils.hasText(teamName)) {
            throw new IllegalArgumentException("[TeamDto] teamName is blank.");
        }
    }
}
