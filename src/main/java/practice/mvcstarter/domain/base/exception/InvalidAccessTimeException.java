package practice.mvcstarter.domain.base.exception;

import practice.mvcstarter.global.error.exception.BusinessException;
import practice.mvcstarter.global.error.exception.ErrorCode;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/14
 */
public class InvalidAccessTimeException extends BusinessException {
    public InvalidAccessTimeException() {
        super("엔티티 저장 이전에 KST 날짜를 조회하려 하였습니다.", ErrorCode.INVALID_ACCESS_TIME);
    }
}
