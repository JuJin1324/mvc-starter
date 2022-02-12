package practice.mvcstarter.exceptions;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

public class FileIsNotBase64Exception extends ErrorException {

    public FileIsNotBase64Exception(String fileName) {
        super(ErrorMessageConst.FILE_IS_NOT_BASE64 + String.format(" [%s]", fileName), ErrorConst.FILE_IS_NOT_BASE64);
    }
}
