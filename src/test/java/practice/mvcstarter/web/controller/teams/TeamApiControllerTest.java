package practice.mvcstarter.web.controller.teams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import practice.mvcstarter.domain.teams.Team;
import practice.mvcstarter.web.controller.ApiTestClient;
import practice.mvcstarter.web.controller.exceptions.ExceptionController;
import practice.mvcstarter.web.controller.initdb.InitTeam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamApiControllerTest {
    ApiTestClient apiTestClient;

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    InitTeam         initTeam;

    @BeforeEach
    void setUp(@Autowired TeamApiController teamApiController,
               @Autowired ExceptionController exceptionController) {
        // 한글 깨짐 처리
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(teamApiController)
                .setControllerAdvice(exceptionController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .alwaysDo(print())
                .build();
        apiTestClient = new ApiTestClient(mockMvc, new ObjectMapper());
    }

    @Test
    @DisplayName("[팀 단건 조회] 존재하지 않는 teamId")
    void getSingleTeam_whenNotExistTeamId_thenReturnNotFound() throws Exception {
        Long invalidTeamId = 999999999L;
        apiTestClient.reqExpectNotFound(get("/api/teams/{teamId}", invalidTeamId), null);
    }

    @Test
    @DisplayName("[팀 단건 조회] 존재하는 teamId")
    void getUsersByUserKeys_whenEmptyReqBody_thenResponseBadRequest() throws Exception {
        /* given */
        Team givenTeam = initTeam.givenAllTeams().get(0);
        System.out.println("=================================== given =================================== ");
        /* when */
        GetSingleTeamResBody resBody = testRestTemplate
                .getForObject("/api/teams/{teamId}", GetSingleTeamResBody.class, givenTeam.getId());
        /* then */
        assertEquals(givenTeam.getId(), resBody.getTeamId());
        assertEquals(givenTeam.getName(), resBody.getTeamName());
    }
}
