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
    private static final String TEAM_NAME         = "팀 이름";
    private static final String TEAM_NAME_NEW     = "새로운 팀 이름";
    private static final Long   TEAM_ID_NOT_EXIST = 999999999L;
    private static final Long   TEAM_ID           = 777L;

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new TeamService(teamRepository);
    }

    @Test
    @DisplayName("[팀 생성] 1.유효하지 않은 매개변수")
    void createTeam_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> teamService.createTeam(null));

        /* name is null */
        assertThrows(IllegalArgumentException.class, () ->
                teamService.createTeam(TeamDto.toCreate(null)));
        /* name is empty string */
        assertThrows(IllegalArgumentException.class, () ->
                teamService.createTeam(TeamDto.toCreate("")));
    }

    @Test
    @DisplayName("[팀 생성] 2.정상 생성")
    void createTeam_whenNormal_thenReturnTeamId() {
        /* given */
        TeamDto givenDto = TeamDto.toCreate(TEAM_NAME);

        /* when */
        teamService.createTeam(givenDto);

        /* then */
        verify(teamRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("[팀 조회 - 단건] 1.유효하지 않은 매개변수")
    void getSingleTeam_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> teamService.getSingleTeam(null));
    }

    @Test
    @DisplayName("[팀 조회 - 단건] 2.존재하지 않는 teamId")
    void getSingleTeam_whenNotExistTeamId_thenThrowException() {
        /* given */
        when(teamRepository.findById(TEAM_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(ResourceNotFoundException.class, () ->
                teamService.getSingleTeam(TEAM_ID_NOT_EXIST));
    }

    @Test
    @DisplayName("[팀 조회 - 단건] 3.존재하는 teamId")
    void getSingleTeam_whenExistTeamId_thenReturnTeamDto() {
        /* given */
        Team givenTeam = Team.createTeam(TEAM_NAME);
        when(teamRepository.findById(TEAM_ID))
                .thenReturn(Optional.of(givenTeam));

        /* when */
        TeamDto dto = teamService.getSingleTeam(TEAM_ID);

        /* then */
        assertThat(dto.getTeamName()).isEqualTo(givenTeam.getName());
    }

    @Test
    @DisplayName("[팀 갱신] 1.유효하지 않은 매개변수")
    void updateTeam_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                teamService.updateTeam(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                teamService.updateTeam(TEAM_ID, null));

        assertThrows(IllegalArgumentException.class, () ->
                teamService.updateTeam(TEAM_ID, TeamDto.toUpdate(null)));
        assertThrows(IllegalArgumentException.class, () ->
                teamService.updateTeam(TEAM_ID, TeamDto.toUpdate("")));
    }

    @Test
    @DisplayName("[팀 갱신] 2.존재하지 않는 teamId")
    void updateTeam_whenNotExistTeamId_thenThrowException() {
        /* given */
        when(teamRepository.findById(TEAM_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        TeamDto givenDto = TeamDto.toUpdate(TEAM_NAME_NEW);

        /* when, then */
        assertThrows(ResourceNotFoundException.class, () -> teamService.updateTeam(TEAM_ID_NOT_EXIST, givenDto));
    }

    @Test
    @DisplayName("[팀 갱신] 3.정상 갱신")
    void updateTeam_whenNormal_thenUpdateTeam() {
        /* given */
        Team givenTeam = Team.createTeam(TEAM_NAME);
        when(teamRepository.findById(TEAM_ID))
                .thenReturn(Optional.of(givenTeam));

        TeamDto givenDto = TeamDto.toUpdate(TEAM_NAME_NEW);

        /* when */
        teamService.updateTeam(TEAM_ID, givenDto);

        /* then */
        assertThat(givenTeam.getName()).isEqualTo(TEAM_NAME_NEW);
    }

    @Test
    @DisplayName("[팀 삭제] 1.유효하지 않은 매개변수")
    void deleteTeam_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                teamService.deleteTeam(null));
    }

    @Test
    @DisplayName("[팀 삭제] 2.존재하지 않는 teamId")
    void deleteTeam_whenNotExistTeamId_thenThrowException() {
        /* given */
        when(teamRepository.findById(TEAM_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(ResourceNotFoundException.class, () ->
                teamService.deleteTeam(TEAM_ID_NOT_EXIST));
    }

    @Test
    @DisplayName("[팀 삭제] 3.정상 삭제")
    void deleteTeam_whenNormal_thenUpdateTeam() {
        /* given */
        Team givenTeam = Team.createTeam(TEAM_NAME);
        when(teamRepository.findById(TEAM_ID))
                .thenReturn(Optional.of(givenTeam));

        /* when */
        teamService.deleteTeam(TEAM_ID);

        /* then */
        verify(teamRepository, times(1)).delete(givenTeam);
    }
}
