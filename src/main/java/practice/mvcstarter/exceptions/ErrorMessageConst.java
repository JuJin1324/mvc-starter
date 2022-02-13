package practice.mvcstarter.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessageConst {
    public static final String RESOURCE_NOT_FOUND  = "요청한 리소스가 존재하지 않습니다.";
    public static final String RESOURCE_DUPLICATED = "요청한 리소스가 1개 이상 존재합니다.";
    public static final String INVALID_REQUEST_BODY = "유효하지 않은 Request Body 입니다.";
    public static final String FILE_IS_NOT_BASE64 = "요청한 파일이 Base64 이미지가 아닙니다.";
    public static final String FILE_IS_EXPIRED = "요청한 파일의 다운로드 기한이 만료되었습니다.";
    public static final String READ_FILE = "요청한 파일 읽기에 실패하였습니다.";
    public static final String STORE_FILE = "요청한 파일 저장에 실패하였습니다.";
    public static final String DELETE_FILE = "요청한 파일 삭제에 실패하였습니다.";

}
