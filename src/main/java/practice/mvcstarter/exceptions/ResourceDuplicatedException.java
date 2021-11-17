package practice.mvcstarter.exceptions;

import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/08
 */
@Getter
public class ResourceDuplicatedException extends RuntimeException {
    private final String error;

    public ResourceDuplicatedException() {
        super(ErrorMessageConst.RESOURCE_DUPLICATED);
        this.error = "ResourceDuplicate";
    }
}
