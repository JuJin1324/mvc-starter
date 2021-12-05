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
import practice.mvcstarter.domain.members.Member;
import practice.mvcstarter.domain.members.MemberService;
import practice.mvcstarter.domain.teams.Team;
import practice.mvcstarter.domain.teams.TeamService;
import practice.mvcstarter.exceptions.ErrorMessageConst;
import practice.mvcstarter.web.controllers.ApiTestClient;
import practice.mvcstarter.web.controllers.advice.ExceptionControllerAdvice;
import practice.mvcstarter.web.controllers.initdb.InitMember;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {
    public static final  Long    MEMBER_ID_NOT_EXIST = 999999999L;
    private static final String  MEMBER_NAME         = InitMember.MEMBER_NAME;
    private static final String  MEMBER_NICK_NAME    = InitMember.MEMBER_NICK_NAME;
    private static final Integer MEMBER_TOTAL_COUNT  = InitMember.MEMBER_TOTAL_COUNT;

    private static final String MEMBER_NAME_NEW = "새로운 팀 이름";
    private static final String MEMBER_AGE_NEW  = "23";

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
    void createMember_whenInvalidReqBody_thenThrowException() throws Exception {
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
    void createMember_whenCreateNewTeam_thenReturnMemberId() {
        /* when */
        Long memberId = testRestTemplate.postForObject(
                "/api/members",
                givenCreateMemberReqBody(MEMBER_NAME, MEMBER_NICK_NAME, "22"),
                Long.class
        );

        /* then */
        assertNotEquals(0L, memberId);

        /* after */
        System.out.println("=============================== after ===============================");
        memberService.deleteMember(memberId);
    }

    @Test
    @DisplayName("[회원 조회 - 단건] 1.존재하지 않는 memberId")
    void getSingleMember_whenNotExistMemberId_thenReturnNotFound() throws Exception {
        apiTestClient.reqExpectNotFound(
                get("/api/members/{memberId}", MEMBER_ID_NOT_EXIST),
                null,
                MemberService.RESOURCE_NAME
        );
    }

    @Test
    @DisplayName("[회원 조회 - 단건] 2.존재하는 memberId")
    void getSingleMember_whenExistMemberId_thenReturnTeam() {
        /* given */
        Member givenMember = initMember.givenAllMembers().get(0);
        System.out.println("=================================== given =================================== ");

        /* when */
        MemberApiController.GetSingleMemberResBody resBody = testRestTemplate
                .getForObject("/api/members/{memberId}", MemberApiController.GetSingleMemberResBody.class, givenMember.getId());

        /* then */
        assertEquals(givenMember.getId(), resBody.getMemberId());
        assertEquals(givenMember.getName(), resBody.getName());
        assertEquals(givenMember.getNickName(), resBody.getNickName());
        assertEquals(givenMember.getAge(), resBody.getAge());
    }

    @Test
    @DisplayName("[회원 조회 - 페이지] 1.RequestParam 없이 요청")
    void getMemberPage_whenHasNoRequestParams_thenReturnDefault() throws Exception {
        final int defaultPageNo = 0;
        final int defaultPageSize = 20;
        final int totalPage = MEMBER_TOTAL_COUNT / defaultPageSize;

        apiTestClient.reqPageable(
                get("/api/members"),
                defaultPageNo,
                defaultPageSize,
                totalPage
        );
    }

    @Test
    @DisplayName("[회원 조회 - 페이지] 2.Page & Size 요청")
    void getMemberPage_whenHasRequestParamsPageAndSize_thenReturnPage() throws Exception {
        final int givenPage = 1;
        final int givenSize = 10;
        final int totalPage = MEMBER_TOTAL_COUNT / givenSize;

        apiTestClient.reqPageable(
                get("/api/members?page={page}&size={size}", givenPage, givenSize),
                givenPage,
                givenSize,
                totalPage
        );
    }

    @Test
    @DisplayName("[회원 갱신] 1.존재하지 않는 teamId")
    void updateMember_whenNotExistTeamId_thenReturnNotFound() throws Exception {
        apiTestClient.reqExpectNotFound(put("/api/members/{memberId}", MEMBER_ID_NOT_EXIST),
                givenUpdateMemberReqBody(MEMBER_NAME_NEW, MEMBER_AGE_NEW), MemberService.RESOURCE_NAME);
    }

    @Test
    @DisplayName("[회원 갱신] 2.유효하지 않은 Reqeust Body")
    void updateMember_whenInvalidReqBody_thenThrowException() throws Exception {
        Member givenMember = initMember.givenAllMembers().get(0);
        System.out.println("=================================== given =================================== ");

        apiTestClient.reqExpectBadRequest(put("/api/members/{memberId}", givenMember.getId()),
                givenUpdateMemberReqBody(null, null), ErrorMessageConst.INVALID_REQUEST_BODY);
        apiTestClient.reqExpectBadRequest(put("/api/members/{memberId}", givenMember.getId()),
                givenUpdateMemberReqBody("", null), ErrorMessageConst.INVALID_REQUEST_BODY);

        apiTestClient.reqExpectBadRequest(put("/api/members/{memberId}", givenMember.getId()),
                givenUpdateMemberReqBody(MEMBER_NAME_NEW, null), ErrorMessageConst.INVALID_REQUEST_BODY);
        apiTestClient.reqExpectBadRequest(put("/api/members/{memberId}", givenMember.getId()),
                givenUpdateMemberReqBody(MEMBER_NAME_NEW, "qqq"), ErrorMessageConst.INVALID_REQUEST_BODY);
    }

    @Test
    @DisplayName("[회원 갱신] 3.정상 처리")
    @Commit
    void updateMember_whenExistTeamId_thenReturnNoContent() throws Exception {
        Member givenMember = initMember.givenAllMembers().get(0);
        System.out.println("=================================== given =================================== ");

        apiTestClient.reqExpectNoContent(
                put("/api/members/{memberId}", givenMember.getId()),
                givenUpdateMemberReqBody(MEMBER_NAME_NEW, MEMBER_AGE_NEW));
    }

    @Test
    @DisplayName("[회원 삭제] 1.존재하지 않는 memberId")
    void deleteMember_whenNotExistMemberId_thenReturnNotFound() throws Exception {
        apiTestClient.reqExpectNotFound(delete("/api/members/{memberId}", MEMBER_ID_NOT_EXIST),
                null, MemberService.RESOURCE_NAME);
    }

    @Test
    @DisplayName("[회원 삭제] 2.정상 처리")
    @Commit
    void deleteMember_whenExistMemberId_thenReturnNoContent() throws Exception {
        Member givenMember = initMember.givenAllMembers().get(0);
        System.out.println("=================================== given =================================== ");

        apiTestClient.reqExpectNoContent(delete("/api/members/{memberId}", givenMember.getId()), null);
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

    private Map<String, Object> givenUpdateMemberReqBody(String name, String age) {
        Map<String, Object> reqBody = new HashMap<>();
        if (name != null) {
            reqBody.put("name", name);
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
