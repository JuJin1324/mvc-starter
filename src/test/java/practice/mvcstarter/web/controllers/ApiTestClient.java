package practice.mvcstarter.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.StringUtils;
import practice.mvcstarter.exceptions.ErrorMessageConst;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/11/15
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@RequiredArgsConstructor
public class ApiTestClient {
    private final MockMvc      mockMvc;
    private final ObjectMapper objectMapper;

    public void reqExpectBadRequest(MockHttpServletRequestBuilder requestBuilder, Map<String, Object> reqBody, String expectedMsg) throws Exception {
        MvcResult mvcResult = mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody == null ? "" : objectMapper.writeValueAsString(reqBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Map<String, Object> resBody = objectMapper.readValue(content, Map.class);
        String message = (String) resBody.get("message");
        assertThat(message).contains(expectedMsg);
    }

    public void reqExpectNotFound(MockHttpServletRequestBuilder requestBuilder, Map<String, Object> reqBody, String resourceName) throws Exception {
        mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody == null ? "" : objectMapper.writeValueAsString(reqBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message")
                        .value(ErrorMessageConst.RESOURCE_NOT_FOUND + String.format(" [%s]", resourceName)));
    }

    public void reqPageable(MockHttpServletRequestBuilder requestBuilder,
                                           int pageNumber,
                                           int pageSize,
                                           int totalPages) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(String.valueOf(pageNumber)))
                .andExpect(jsonPath("$.pageable.pageSize").value(String.valueOf(pageSize)))
                .andExpect(jsonPath("$.totalPages").value(String.valueOf(totalPages)));
    }

    public void reqExpectNoContent(MockHttpServletRequestBuilder requestBuilder, Map<String, Object> reqBody) throws Exception {
        mockMvc.perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody == null ? "" : objectMapper.writeValueAsString(reqBody)))
                .andExpect(status().isNoContent());
    }
}
