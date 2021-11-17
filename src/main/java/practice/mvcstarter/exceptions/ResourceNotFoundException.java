package practice.mvcstarter.exceptions;

import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/08/22
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String error;

    public ResourceNotFoundException() {
        super(ErrorMessageConst.RESOURCE_NOT_FOUND);
        this.error = "ResourceNotFound";
    }
}
