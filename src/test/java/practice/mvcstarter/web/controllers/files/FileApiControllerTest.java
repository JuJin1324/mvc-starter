package practice.mvcstarter.web.controllers.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;
import practice.mvcstarter.web.controllers.advice.ExceptionControllerAdvice;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileApiControllerTest {
    @Autowired
    FileApiController fileApiController;

    @Autowired
    ExceptionControllerAdvice exceptionControllerAdvice;

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // 한글 깨짐 처리
        mockMvc = MockMvcBuilders.standaloneSetup(fileApiController)
                .setControllerAdvice(exceptionControllerAdvice)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 한글 깨짐 처리
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .alwaysDo(print())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("파일 조회 - 다운로드")
    void getFileDownload() throws Exception {
        Long fileId = 1L;
        mockMvc.perform(get("/api/v1.0/files/{fileId}/download", fileId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("파일 조회 - base64 image")
    void getFileBase64() throws Exception {
        Long fileId = 1L;
        mockMvc.perform(get("/api/v1.0/files/{fileId}/base64", fileId))
                .andExpect(status().isOk());
    }
}