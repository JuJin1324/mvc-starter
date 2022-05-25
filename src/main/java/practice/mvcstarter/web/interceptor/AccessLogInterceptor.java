package practice.mvcstarter.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/05/25
 */

@Slf4j
public class AccessLogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Access API: {}", request.getMethod() + " " + request.getRequestURI());
        return true;
    }
}
