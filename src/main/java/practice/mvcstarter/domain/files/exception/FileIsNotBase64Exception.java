package practice.mvcstarter.domain.files.exception;

import practice.mvcstarter.global.error.exception.BusinessException;
import practice.mvcstarter.global.error.exception.ErrorCode;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

public class FileIsNotBase64Exception extends BusinessException {

    public FileIsNotBase64Exception(String fileName) {
        super("File is not base64: " + fileName, ErrorCode.FILE_IS_NOT_BASE64);
    }
}
