package practice.mvcstarter.web.controller.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import practice.mvcstarter.exceptions.ResourceDuplicatedException;
import practice.mvcstarter.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2021/06/01
 * Copyright (C) 2021, Centum Factorial all rights reserved.
 */

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResBody handleBizCheckedException(HttpServletRequest request, final ResourceNotFoundException e) {
        log.error("requestURI: {}, errorMessage: {}", request.getRequestURI(), e.getMessage());
        return new ErrorResBody(HttpStatus.NOT_FOUND, e.getError(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResBody handleBizCheckedException(HttpServletRequest request, final ResourceDuplicatedException e) {
        log.error("requestURI: {}, errorMessage: {}", request.getRequestURI(), e.getMessage());
        return new ErrorResBody(HttpStatus.BAD_REQUEST, e.getError(), e.getMessage());
    }
}
