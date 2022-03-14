package practice.mvcstarter.domain.files.exception;

import practice.mvcstarter.global.error.exception.EntityNotFoundException;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/16
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */
public class FileNotFoundException extends EntityNotFoundException {

    public FileNotFoundException(Long target) {
        super(target + " is not found");
    }

    public FileNotFoundException(String target) {
        super(target + " is not found");
    }
}
