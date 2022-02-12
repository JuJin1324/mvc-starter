package practice.mvcstarter.exceptions;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/08
 */
public class ResourceDuplicatedException extends ErrorException {

    public ResourceDuplicatedException(String resourceName) {
        super(ErrorMessageConst.RESOURCE_DUPLICATED + String.format(" [%s]", resourceName), ErrorConst.RESOURCE_DUPLICATED);
    }
}
