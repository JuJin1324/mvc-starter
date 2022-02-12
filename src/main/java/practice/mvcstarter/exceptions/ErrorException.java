package practice.mvcstarter.exceptions;

import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@Getter
public abstract class ErrorException extends RuntimeException {
    private final String error;


    public ErrorException(String message, String error) {
        super(message);
        this.error = error;
    }
}
