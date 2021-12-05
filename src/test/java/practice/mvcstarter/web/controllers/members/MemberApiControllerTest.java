package practice.mvcstarter.web.controllers.members;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import practice.mvcstarter.domain.members.MemberService;
import practice.mvcstarter.exceptions.ErrorMessageConst;
import practice.mvcstarter.web.controllers.ApiTestClient;
import practice.mvcstarter.web.controllers.advice.ExceptionControllerAdvice;
import practice.mvcstarter.web.controllers.initdb.InitMember;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {
    public static final  Long   MEMBER_ID_NOT_EXIST = 999999999L;
    private static final String MEMBER_NAME         = InitMember.MEMBER_NAME;
    private static final String MEMBER_NICK_NAME         = InitMember.MEMBER_NICK_NAME;

    private static final String TEAM_NAME_NEW       = "새로운 팀 이름";

    ApiTestClient apiTestClient;

    @Autowired
    TestRestTemplate          testRestTemplate;
    @Autowired
    InitMember                initMember;
    @Autowired
    MemberApiController       memberApiController;
    @Autowired
    ExceptionControllerAdvice exceptionControllerAdvice;
    @Autowired
    MemberService             memberService;

    @BeforeEach
    void setUp() {
        // 한글 깨짐 처리
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(memberApiController)
                .setControllerAdvice(exceptionControllerAdvice)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .alwaysDo(print())
                .build();
        apiTestClient = new ApiTestClient(mockMvc, new ObjectMapper());
    }

    @Test
    @DisplayName("[회원 생성] 1.유효하지 않은 Request Body")
    void createTeam_whenInvalidReqBody_thenThrowException() throws Exception {
        apiTestClient.reqExpectBadRequest(post("/api/members"), givenCreateMemberReqBody(null, null, null),
                ErrorMessageConst.INVALID_REQUEST_BODY);
        apiTestClient.reqExpectBadRequest(post("/api/members"), givenCreateMemberReqBody("", null, null),
                ErrorMessageConst.INVALID_REQUEST_BODY);

        apiTestClient.reqExpectBadRequest(post("/api/members"), givenCreateMemberReqBody(MEMBER_NAME, null, null),
                ErrorMessageConst.INVALID_REQUEST_BODY);
        apiTestClient.reqExpectBadRequest(post("/api/members"), givenCreateMemberReqBody(MEMBER_NAME, "", null),
                ErrorMessageConst.INVALID_REQUEST_BODY);

        apiTestClient.reqExpectBadRequest(post("/api/members"), givenCreateMemberReqBody(MEMBER_NAME, MEMBER_NICK_NAME, null),
                ErrorMessageConst.INVALID_REQUEST_BODY);
        apiTestClient.reqExpectBadRequest(post("/api/members"), givenCreateMemberReqBody(MEMBER_NAME, MEMBER_NICK_NAME, "qqq"),
                ErrorMessageConst.INVALID_REQUEST_BODY);
    }

    @Test
    @DisplayName("[회원 생성] 2.신규 회원 생성")
    @Commit
    void createTeam_whenCreateNewTeam_thenReturnTeamId() {
        /* when */
        Long teamId = testRestTemplate.postForObject(
                "/api/members",
                givenCreateMemberReqBody(MEMBER_NAME, MEMBER_NICK_NAME, "22"),
                Long.class
        );

        /* then */
        assertNotEquals(0L, teamId);

        /* after */
        System.out.println("=============================== after ===============================");
        memberService.deleteMember(teamId);
    }

    private Map<String, Object> givenCreateMemberReqBody(String name, String nickName, String age) {
        Map<String, Object> reqBody = new HashMap<>();
        if (name != null) {
            reqBody.put("name", name);
        }
        if (nickName != null) {
            reqBody.put("nickName", nickName);
        }
        if (age != null) {
            try {
                reqBody.put("age", Long.valueOf(age));
            } catch (NumberFormatException e) {
                reqBody.put("age", age);
            }
        }

        return reqBody;
    }
}
