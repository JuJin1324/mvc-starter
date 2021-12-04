package practice.mvcstarter.web.controllers.initdb;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.teams.Team;
import practice.mvcstarter.domain.teams.TeamRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@Profile("test")
@Component
@RequiredArgsConstructor
public class InitTeam {
    public static final String TEAM_NAME = "Spring Integration Test 팀 이름";
    public static final int TEAM_TOTAL_COUNT = 40;

    private final InitService initService;

    @PostConstruct
    public void postConstruct() {
        initService.deleteTeams();
        initService.createTeams();
    }

    public List<Team> givenAllTeams() {
        return initService.getTeams();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final TeamRepository teamRepository;

        public List<Team> getTeams() {
            return teamRepository.findAllByNameLike("%" + TEAM_NAME + "%");
        }

        public void createTeams() {
            IntStream.range(1, TEAM_TOTAL_COUNT)
                    .forEach(idx -> teamRepository.save(Team.createTeam(TEAM_NAME + " " + idx)));
        }

        public void deleteTeams() {
            List<Team> teams = teamRepository.findAllByNameLike(TEAM_NAME);
            teamRepository.deleteAll(teams);
        }
    }
}
