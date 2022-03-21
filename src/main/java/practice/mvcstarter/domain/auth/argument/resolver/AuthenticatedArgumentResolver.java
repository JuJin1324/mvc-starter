package practice.mvcstarter.domain.auth.argument.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import practice.mvcstarter.domain.auth.service.LoginService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/21
 */

@RequiredArgsConstructor
public class AuthenticatedArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String       AUTH_HEADER = "Authorization";
    private final        LoginService loginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Authenticated.class);
        boolean hasUserType = Long.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return loginService.authenticate(request.getHeader(AUTH_HEADER));
    }
}
