package practice.mvcstarter.web.controllers.members;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.members.controller.MemberApiController;
import practice.mvcstarter.domain.members.dto.MemberCreateDto;
import practice.mvcstarter.domain.members.dto.MemberUpdateDto;
import practice.mvcstarter.domain.members.dto.MemberUpdateProfileDto;
import practice.mvcstarter.global.error.GlobalExceptionHandler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@ActiveProfiles("test")
@Transactional
@Commit
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {
    private static final String      BASE64_IMAGE     = "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAKCAYAAACJxx+AAAABYWlDQ1BrQ0dDb2xvclNwYWNlRGlzcGxheVAzAAAokWNgYFJJLCjIYWFgYMjNKykKcndSiIiMUmB/yMAOhLwMYgwKicnFBY4BAT5AJQwwGhV8u8bACKIv64LMOiU1tUm1XsDXYqbw1YuvRJsw1aMArpTU4mQg/QeIU5MLikoYGBhTgGzl8pICELsDyBYpAjoKyJ4DYqdD2BtA7CQI+whYTUiQM5B9A8hWSM5IBJrB+API1klCEk9HYkPtBQFul8zigpzESoUAYwKuJQOUpFaUgGjn/ILKosz0jBIFR2AopSp45iXr6SgYGRiaMzCAwhyi+nMgOCwZxc4gxJrvMzDY7v////9uhJjXfgaGjUCdXDsRYhoWDAyC3AwMJ3YWJBYlgoWYgZgpLY2B4dNyBgbeSAYG4QtAPdHFacZGYHlGHicGBtZ7//9/VmNgYJ/MwPB3wv//vxf9//93MVDzHQaGA3kAFSFl7jXH0fsAAACKZVhJZk1NACoAAAAIAAQBGgAFAAAAAQAAAD4BGwAFAAAAAQAAAEYBKAADAAAAAQACAACHaQAEAAAAAQAAAE4AAAAAAAAAkAAAAAEAAACQAAAAAQADkoYABwAAABIAAAB4oAIABAAAAAEAAAAIoAMABAAAAAEAAAAKAAAAAEFTQ0lJAAAAU2NyZWVuc2hvdPoE6XgAAAAJcEhZcwAAFiUAABYlAUlSJPAAAAHTaVRYdFhNTDpjb20uYWRvYmUueG1wAAAAAAA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJYTVAgQ29yZSA2LjAuMCI+CiAgIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgICAgIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiCiAgICAgICAgICAgIHhtbG5zOmV4aWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vZXhpZi8xLjAvIj4KICAgICAgICAgPGV4aWY6UGl4ZWxZRGltZW5zaW9uPjEwPC9leGlmOlBpeGVsWURpbWVuc2lvbj4KICAgICAgICAgPGV4aWY6UGl4ZWxYRGltZW5zaW9uPjg8L2V4aWY6UGl4ZWxYRGltZW5zaW9uPgogICAgICAgICA8ZXhpZjpVc2VyQ29tbWVudD5TY3JlZW5zaG90PC9leGlmOlVzZXJDb21tZW50PgogICAgICA8L3JkZjpEZXNjcmlwdGlvbj4KICAgPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KbJxrmQAAABxpRE9UAAAAAgAAAAAAAAAFAAAAKAAAAAUAAAAFAAAAStvdOSgAAAAWSURBVCgVYmRgYPgPxDgBI1BmoBUAAAAA//8vs7vuAAAAFElEQVRjZGBg+A/EOAEjUGagFQAA8MwKAQlk/YkAAAAASUVORK5CYII=";
    private static final ContentType CONTENT_TYPE_PNG = ContentType.IMAGE_PNG;

    @Autowired
    MemberApiController    memberApiController;
    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // 한글 깨짐 처리
        mockMvc = MockMvcBuilders.standaloneSetup(memberApiController)
                .setControllerAdvice(globalExceptionHandler)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .alwaysDo(print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("회원 생성")
    void createMember() throws Exception {
        MemberCreateDto dto = new MemberCreateDto("멤버 이름1", "멤버 닉네임1", 21);
        mockMvc.perform(
                        post("/api/v1.0/members")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원 조회 - 단건")
    void getSingleMember() throws Exception {
        mockMvc.perform(get("/api/v1.0/members/{memberId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 갱신")
    void updateMember() throws Exception {
        MemberUpdateDto dto = new MemberUpdateDto("멤버 닉네임", 23);
        mockMvc.perform(
                        put("/api/v1.0/members/{memberId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("회원 갱신 - 프로필: reqBody 에 base64 image 가 있는 경우")
    void updateMemberProfile_whenReqBodyHasBase64Image() throws Exception {
        MemberUpdateProfileDto dto = new MemberUpdateProfileDto(BASE64_IMAGE, CONTENT_TYPE_PNG);
        mockMvc.perform(
                        put("/api/v1.0/members/{memberId}/profile", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("회원 갱신 - 프로필: reqBody 에 base64 image 가 없는 경우")
    void updateMemberProfile_whenReqBodyHasNoBase64Image() throws Exception {
        MemberUpdateProfileDto dto = new MemberUpdateProfileDto(null, null);
        mockMvc.perform(
                        put("/api/v1.0/members/{memberId}/profile", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("회원 삭제")
    void deleteMember() throws Exception {
        mockMvc.perform(delete("/api/v1.0/members/{memberId}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("관심 게시글 조회 - 목록")
    void getAttractiveBoards() throws Exception {
        Long memberId = 1L;
        mockMvc.perform(get("/api/v1.0/members/{memberId}/boards/attractive", memberId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("내가 쓴 게시글 조회 - 목록")
    void getMineBoards() throws Exception {
        Long memberId = 1L;
        mockMvc.perform(get("/api/v1.0/members/{memberId}/boards/mine", memberId))
                .andExpect(status().isOk());
    }

    //    @Test
//    @DisplayName("회원 조회 - 페이지")
//    void getMemberPage() throws Exception {
//        final int givenPage = 1;
//        final int givenSize = 10;
//
//        mockMvc.perform(get("/api/members?page={page}&size={size}", givenPage, givenSize))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pageable.pageNumber").value(String.valueOf(givenPage)))
//                .andExpect(jsonPath("$.pageable.pageSize").value(String.valueOf(givenSize)));
//    }
}
