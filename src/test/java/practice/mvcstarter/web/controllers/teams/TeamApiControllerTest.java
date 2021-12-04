package practice.mvcstarter.web.controllers.teams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import practice.mvcstarter.domain.teams.Team;
import practice.mvcstarter.domain.teams.TeamService;
import practice.mvcstarter.exceptions.ErrorMessageConst;
import practice.mvcstarter.exceptions.InvalidRequestBodyException;
import practice.mvcstarter.web.controllers.ApiTestClient;
import practice.mvcstarter.web.controllers.advice.ExceptionControllerAdvice;
import practice.mvcstarter.web.controllers.initdb.InitTeam;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamApiControllerTest {
    private static final String TEAM_NAME        = "팀 이름";
    private static final int    TEAM_TOTAL_COUNT = InitTeam.TEAM_TOTAL_COUNT;

    ApiTestClient apiTestClient;

    @Autowired
    TestRestTemplate          testRestTemplate;
    @Autowired
    InitTeam                  initTeam;
    @Autowired
    TeamApiController         teamApiController;
    @Autowired
    ExceptionControllerAdvice exceptionControllerAdvice;
    @Autowired
    TeamService               teamService;

    @BeforeEach
    void setUp() {
        // 한글 깨짐 처리
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(teamApiController)
                .setControllerAdvice(exceptionControllerAdvice)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .alwaysDo(print())
                .build();
        apiTestClient = new ApiTestClient(mockMvc, new ObjectMapper());
    }

    @Test
    @DisplayName("[팀 생성] 유효하지 않은 Request Body")
    void createTeam_whenInvalidReqBody_thenThrowException() throws Exception {
        apiTestClient.reqExpectBadRequest(post("/api/teams"), givenCreateTeamReqBody(null),
                ErrorMessageConst.INVALID_REQUEST_BODY);
        apiTestClient.reqExpectBadRequest(post("/api/teams"), givenCreateTeamReqBody(""),
                ErrorMessageConst.INVALID_REQUEST_BODY);
    }

    @Test
    @DisplayName("[팀 생성] 신규 팀 생성")
    @Commit
    void createTeam_whenCreateNewTeam_thenReturnTeamId() throws Exception {
        /* when */
        Long teamId = testRestTemplate.postForObject("/api/teams", givenCreateTeamReqBody(TEAM_NAME), Long.class);

        /* then */
        assertNotEquals(0L, teamId);

        /* after */
        System.out.println("=============================== after ===============================");
        teamService.deleteTeam(teamId);
    }

    @Test
    @DisplayName("[팀 조회 - 단건] 존재하지 않는 teamId")
    void getSingleTeam_whenNotExistTeamId_thenReturnNotFound() throws Exception {
        Long invalidTeamId = 999999999L;
        apiTestClient.reqExpectNotFound(get("/api/teams/{teamId}", invalidTeamId), null, TeamService.RESOURCE_NAME);
    }

    @Test
    @DisplayName("[팀 조회 - 단건] 존재하는 teamId")
    void getSingleTeam_whenEmptyReqBody_thenResponseBadRequest() {
        /* given */
        Team givenTeam = initTeam.givenAllTeams().get(0);
        System.out.println("=================================== given =================================== ");
        /* when */
        TeamApiController.GetSingleTeamResBody resBody = testRestTemplate
                .getForObject("/api/teams/{teamId}", TeamApiController.GetSingleTeamResBody.class, givenTeam.getId());
        /* then */
        assertEquals(givenTeam.getId(), resBody.getTeamId());
        assertEquals(givenTeam.getName(), resBody.getTeamName());
    }

    @Test
    @DisplayName("[팀 조회 - 페이지] RequestParam 없이 요청")
    void getTeamPage_whenHasNoRequestParams_thenReturnDefault() throws Exception {
        final int defaultPageNo = 0;
        final int defaultPageSize = 20;
        final int totalPage = TEAM_TOTAL_COUNT / defaultPageSize;

        apiTestClient.reqPageable(
                get("/api/teams"),
                defaultPageNo,
                defaultPageSize,
                totalPage);
    }

    @Test
    @DisplayName("[팀 조회 - 페이지] Page & Size 요청")
    void getTeamPage_whenHasRequestParamsPageAndSize_thenReturnPage() throws Exception {
        final int givenPage = 1;
        final int givenSize = 10;
        final int totalPage = TEAM_TOTAL_COUNT / givenSize;

        apiTestClient.reqPageable(
                get("/api/teams?page={page}&size={size}", givenPage, givenSize),
                givenPage,
                givenSize,
                totalPage);
    }

    private Map<String, Object> givenCreateTeamReqBody(String teamName) {
        HashMap<String, Object> reqBody = new HashMap<>();
        if (teamName != null) {
            reqBody.put("teamName", teamName);
        }

        return reqBody;
    }
}