package practice.mvcstarter.exceptions;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
public class StoreFileException extends ErrorException {

    public StoreFileException(String fileName) {
        super(ErrorMessageConst.STORE_FILE + String.format(" [%s]", fileName), ErrorConst.STORE_FILE);
    }
}
