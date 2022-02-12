package practice.mvcstarter.exceptions;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

public class ReadFileException extends ErrorException {

    public ReadFileException(String fileName) {
        super(ErrorMessageConst.READ_FILE + String.format(" [%s]", fileName), ErrorConst.READ_FILE);
    }
}
