package practice.mvcstarter.domain.files.exception;

import practice.mvcstarter.global.error.exception.BusinessException;
import practice.mvcstarter.global.error.exception.ErrorCode;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

public class ExpiredFileException extends BusinessException {
    public ExpiredFileException(String fileName) {
        super("File has expired: " + fileName, ErrorCode.FILE_HAS_EXPIRED);
    }
}
