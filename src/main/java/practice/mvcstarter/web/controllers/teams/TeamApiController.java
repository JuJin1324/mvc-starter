package practice.mvcstarter.web.controllers.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mvcstarter.domain.teams.TeamDto;
import practice.mvcstarter.domain.teams.TeamService;

import javax.validation.constraints.NotBlank;
import java.util.List;

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
     * 팀 생성
     */
    @PostMapping("")
    public Long createTeam(@Validated @RequestBody CreateTeamReqBody reqBody) {
        return teamService.createTeam(reqBody.toDto());
    }

    /**
     * 팀 조회 - 단건
     */
    @GetMapping("/{teamId}")
    public GetSingleTeamResBody getSingleTeam(@PathVariable("teamId") Long teamId) {
        TeamDto team = teamService.getSingleTeam(teamId);
        return new GetSingleTeamResBody(team.getTeamId(), team.getTeamName());
    }

    /**
     * 팀 조회 - 페이지
     */
    @GetMapping("")
    public Page<GetSingleTeamResBody> getTeamPage(@PageableDefault(size = 20) Pageable pageable) {
        return teamService.getTeamPage(pageable)
                .map(dto -> new GetSingleTeamResBody(dto.getTeamId(), dto.getTeamName()));
    }

    /**
     * 팀 갱신
     */
    @PutMapping("/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTeam(@PathVariable("teamId") Long teamId,
                           @Validated @RequestBody UpdateTeamReqBody reqBody) {
        teamService.updateTeam(teamId, reqBody.toDto());
    }

    /**
     * 팀 삭제
     */
    @DeleteMapping("/{teamId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable("teamId") Long teamId) {
        teamService.deleteTeam(teamId);
    }

    @Data
    static class CreateTeamReqBody {
        @NotBlank
        private String teamName;

        public TeamDto toDto() {
            return TeamDto.toCreate(teamName);
        }
    }

    @Data
    static class UpdateTeamReqBody {
        @NotBlank
        private String teamName;

        public TeamDto toDto() {
            return TeamDto.toUpdate(teamName);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class GetSingleTeamResBody {
        private Long   teamId;
        private String teamName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class GetTeamListResBody {
        List<GetSingleTeamResBody> list;
    }
}
