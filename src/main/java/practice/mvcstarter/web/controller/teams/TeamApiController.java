package practice.mvcstarter.web.controller.teams;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.mvcstarter.domain.teams.TeamDto;
import practice.mvcstarter.domain.teams.TeamService;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamApiController {
    private final TeamService teamService;

    /**
     * 팀 단건 조회
     */
    @GetMapping("/{teamId}")
    public GetSingleTeamResBody getSingleTeam(@PathVariable("teamId") Long teamId) {
        TeamDto team = teamService.getSingleTeam(teamId);
        return new GetSingleTeamResBody(team.getTeamId(), team.getTeamName());
    }

    /**
     * 팀 페이지 조회
     */
    @GetMapping("")
    public Page<GetSingleTeamResBody> getTeamPage(Pageable pageable) {
        return teamService.getTeamPage(pageable)
                .map(dto -> new GetSingleTeamResBody(dto.getTeamId(), dto.getTeamName()));
    }
}
