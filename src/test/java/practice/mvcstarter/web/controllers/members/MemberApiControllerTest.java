package practice.mvcstarter.web.controllers.members;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import practice.mvcstarter.web.controllers.advice.ExceptionControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {
    @Autowired
    MemberApiController       memberApiController;
    @Autowired
    ExceptionControllerAdvice exceptionControllerAdvice;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // 한글 깨짐 처리
        mockMvc = MockMvcBuilders.standaloneSetup(memberApiController)
                .setControllerAdvice(exceptionControllerAdvice)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .alwaysDo(print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("회원 생성")
    @Commit
    void createMember() throws Exception {
        Map<String, Object> reqBody = givenCreateMemberReqBody("멤버 이름", "멤버 닉네임", "22");
        mockMvc.perform(
                        post("/api/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reqBody))
                )
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원 조회 - 단건")
    void getSingleMember() throws Exception {
        mockMvc.perform(get("/api/members/{memberId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.CREATED.value())));
    }

    @Test
    @DisplayName("회원 조회 - 페이지")
    void getMemberPage() throws Exception {
        final int givenPage = 1;
        final int givenSize = 10;

        mockMvc.perform(get("/api/members?page={page}&size={size}", givenPage, givenSize))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(String.valueOf(givenPage)))
                .andExpect(jsonPath("$.pageable.pageSize").value(String.valueOf(givenSize)));
    }

    @Test
    @DisplayName("회원 갱신")
    void updateMember() throws Exception {
        Map<String, Object> reqBody = givenUpdateMemberReqBody("멤버 닉네임", "23");
        mockMvc.perform(
                        put("/api/members/{memberId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(reqBody))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteMember() throws Exception {
        mockMvc.perform(delete("/api/members/{memberId}", 1L))
                .andExpect(status().isOk());
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
