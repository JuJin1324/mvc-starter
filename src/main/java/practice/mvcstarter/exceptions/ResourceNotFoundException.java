package practice.mvcstarter.exceptions;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/08/22
 */
public class ResourceNotFoundException extends ErrorException {

    public ResourceNotFoundException(String resourceName) {
        super(ErrorMessageConst.RESOURCE_NOT_FOUND + String.format(" [%s]", resourceName), ErrorConst.RESOURCE_NOT_FOUND);
    }
}
