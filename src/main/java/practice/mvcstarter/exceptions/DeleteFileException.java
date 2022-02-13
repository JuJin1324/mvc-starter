package practice.mvcstarter.exceptions;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
public class DeleteFileException extends ErrorException {

    public DeleteFileException(String fileName) {
        super(ErrorMessageConst.DELETE_FILE + String.format(" [%s]", fileName), ErrorConst.DELETE_FILE);
    }
}
