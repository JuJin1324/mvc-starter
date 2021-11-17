package practice.mvcstarter.domain.teams;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.exceptions.ResourceNotFoundException;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/15
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    /**
     * 팀 생성
     *
     * @return teamId
     */
    @Transactional
    public Long createTeam(TeamDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("toCreate Dto is null.");
        }
        dto.validateToCreate();

        Team team = new Team(dto.getTeamName());
        teamRepository.save(team);

        return team.getId();
    }

    /**
     * 팀 단건 조회
     */
    public TeamDto getSingleTeam(Long teamId) {
        if (teamId == null) {
            throw new IllegalArgumentException("teamId is null.");
        }

        return teamRepository.findById(teamId)
                .map(TeamDto::toRead)
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * 팀 리스트 조회
     */
    public Page<TeamDto> getTeamPage(Pageable pageable) {
        return teamRepository.findAll(pageable)
                .map(TeamDto::toRead);
    }


    /**
     * 팀 갱신
     */
    @Transactional
    public void updateTeam(Long teamId, TeamDto dto) {
        if (teamId == null) {
            throw new IllegalArgumentException("teamId is null.");
        }
        if (dto == null) {
            throw new IllegalArgumentException("toCreate Dto is null.");
        }
        dto.validateToUpdate();

        Team team = teamRepository.findById(teamId)
                .orElseThrow(ResourceNotFoundException::new);
        team.update(dto.getTeamName());
    }

    /**
     * 팀 삭제
     */
    @Transactional
    public void deleteTeam(Long teamId) {
        if (teamId == null) {
            throw new IllegalArgumentException("teamId is null.");
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(ResourceNotFoundException::new);
        teamRepository.delete(team);
    }
}
