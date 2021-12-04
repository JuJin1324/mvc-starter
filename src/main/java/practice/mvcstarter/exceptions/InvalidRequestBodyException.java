package practice.mvcstarter.exceptions;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/04
 */

@Getter
public class InvalidRequestBodyException extends RuntimeException {
    private final String error;

    public InvalidRequestBodyException(String resourceName) {
        super(ErrorMessageConst.INVALID_REQUEST_BODY +
                (StringUtils.hasText(resourceName) ? String.format(" [%s]", resourceName) : ""));
        this.error = ErrorConst.INVALID_REQUEST_BODY;
    }
}
