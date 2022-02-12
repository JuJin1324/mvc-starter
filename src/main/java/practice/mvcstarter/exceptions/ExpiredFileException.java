package practice.mvcstarter.exceptions;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

public class ExpiredFileException extends ErrorException {
    public ExpiredFileException(String fileName) {
        super(ErrorMessageConst.FILE_IS_EXPIRED + String.format(" [%s]", fileName), ErrorConst.FILE_IS_EXPIRED);
    }
}
