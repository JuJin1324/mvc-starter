package practice.mvcstarter.exceptions;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/04
 */

public class InvalidRequestBodyException extends ErrorException {

    public InvalidRequestBodyException(String resourceName) {
        super(ErrorMessageConst.INVALID_REQUEST_BODY +
                        (StringUtils.hasText(resourceName) ? String.format(" [%s]", resourceName) : ""),
                ErrorConst.INVALID_REQUEST_BODY);
    }
}
