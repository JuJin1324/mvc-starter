package practice.mvcstarter.domain.teams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.mvcstarter.exceptions.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/15
 */

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    private static final String TEAM_NAME       = "팀 이름";
    private static final String NEW_TEAM_NAME   = "새로운 팀 이름";
    private static final Long   INVALID_TEAM_ID = 999999999L;
    private static final Long   VALID_TEAM_ID   = 777L;

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new TeamService(teamRepository);
    }

    @Test
    @DisplayName("[팀 생성] 1.매개변수가 null 인 경우")
    void createTeam_whenParamIsNull_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> teamService.createTeam(null));
    }

    @Test
    @DisplayName("[팀 생성] 2.dto validation 미통과")
    void createTeam_whenInvalidParam_thenThrowException() {
        /* name is null */
        assertThrows(IllegalArgumentException.class, () ->
                teamService.createTeam(TeamDto.toCreate(null)));
        /* name is empty string */
        assertThrows(IllegalArgumentException.class, () ->
                teamService.createTeam(TeamDto.toCreate("")));
    }

    @Test
    @DisplayName("[팀 생성] 3.dto validation 통과")
    void createTeam_whenValidParam_thenReturnTeamId() {
        /* given */
        TeamDto givenDto = TeamDto.toCreate(TEAM_NAME);

        /* when */
        teamService.createTeam(givenDto);

        /* then */
        verify(teamRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("[팀 조회 - 단건] 1.매개변수가 null 인 경우")
    void getSingleTeam_whenParamIsNull_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> teamService.getSingleTeam(null));
    }

    @Test
    @DisplayName("[팀 조회 - 단건] 2.매개변수가 invalid 인 경우")
    void getSingleTeam_whenInvalidParam_thenThrowException() {
        /* given */
        when(teamRepository.findById(INVALID_TEAM_ID))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(ResourceNotFoundException.class, () -> teamService.getSingleTeam(INVALID_TEAM_ID));
    }

    @Test
    @DisplayName("[팀 조회 - 단건] 3.매개변수가 정상 인 경우")
    void getSingleTeam_whenValidParam_thenReturnTeamDto() {
        /* given */
        Team givenTeam = Team.createTeam(TEAM_NAME);
        when(teamRepository.findById(VALID_TEAM_ID))
                .thenReturn(Optional.of(givenTeam));

        /* when */
        TeamDto dto = teamService.getSingleTeam(VALID_TEAM_ID);

        /* then */
        assertThat(dto.getTeamName()).isEqualTo(givenTeam.getName());
    }

    @Test
    @DisplayName("[팀 갱신] 1.매개변수가 null 인 경우")
    void updateTeam_whenParamIsNull_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> teamService.updateTeam(null, null));
        assertThrows(IllegalArgumentException.class, () -> teamService.updateTeam(VALID_TEAM_ID, null));
    }

    @Test
    @DisplayName("[팀 갱신] 2.매개변수가 invalid 인 경우")
    void updateTeam_whenInvalidParam_thenThrowException() {
        /* given */
        TeamDto givenDto = TeamDto.toUpdate(NEW_TEAM_NAME);
        when(teamRepository.findById(INVALID_TEAM_ID))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(ResourceNotFoundException.class, () -> teamService.updateTeam(INVALID_TEAM_ID, givenDto));
    }

    @Test
    @DisplayName("[팀 갱신] 3.매개변수가 정상 인 경우")
    void updateTeam_whenValidParam_thenUpdateTeam() {
        /* given */
        TeamDto givenDto = TeamDto.toUpdate(NEW_TEAM_NAME);
        Team givenTeam = Team.createTeam(TEAM_NAME);
        when(teamRepository.findById(VALID_TEAM_ID))
                .thenReturn(Optional.of(givenTeam));

        /* when */
        teamService.updateTeam(VALID_TEAM_ID, givenDto);

        /* then */
        assertThat(givenTeam.getName()).isEqualTo(NEW_TEAM_NAME);
    }

    @Test
    @DisplayName("[팀 삭제] 1.매개변수가 null 인 경우")
    void deleteTeam_whenParamIsNull_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> teamService.deleteTeam(null));
    }

    @Test
    @DisplayName("[팀 삭제] 2.매개변수가 invalid 인 경우")
    void deleteTeam_whenInvalidParam_thenThrowException() {
        /* given */
        when(teamRepository.findById(INVALID_TEAM_ID))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(ResourceNotFoundException.class, () -> teamService.deleteTeam(INVALID_TEAM_ID));
    }

    @Test
    @DisplayName("[팀 삭제] 3.매개변수가 정상 인 경우")
    void deleteTeam_whenValidParam_thenUpdateTeam() {
        /* given */
        Team givenTeam = Team.createTeam(TEAM_NAME);
        when(teamRepository.findById(VALID_TEAM_ID))
                .thenReturn(Optional.of(givenTeam));

        /* when */
        teamService.deleteTeam(VALID_TEAM_ID);

        /* then */
        verify(teamRepository, times(1)).delete(givenTeam);
    }
}
