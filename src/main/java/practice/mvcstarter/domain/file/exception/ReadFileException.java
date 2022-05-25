package practice.mvcstarter.domain.file.exception;

import practice.mvcstarter.global.error.exception.BusinessException;
import practice.mvcstarter.global.error.exception.ErrorCode;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

public class ReadFileException extends BusinessException {

    public ReadFileException(String fileName) {
        super("Failed to read file: " + fileName, ErrorCode.FAILED_TO_READ_FILE);
    }
}
