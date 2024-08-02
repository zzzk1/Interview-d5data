package org.example.backendapi.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.backendapi.common.Constants;
import org.example.backendapi.common.ExceptionStatusEnum;
import org.example.backendapi.common.LoginRequired;
import org.example.backendapi.entity.request.UserRequest;
import org.example.backendapi.exception.BizException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // If @LoginRequired is not added to the method, there is no need to log in, and it will be released directly.
        if (isLoginFree(handler)) {
            return true;
        }
        handleLogin(request);

        return false;
    }

    /**
     * Is the interface free of login
     *
     * @param handler
     * @return true, you don't need to log in.
     */
    private boolean isLoginFree(Object handler) {
        // Determine whether login-free support is available.
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequiredAnnotation = AnnotationUtils.getAnnotation(method, LoginRequired.class);
            // Without adding @LoginRequired, no login is required
            return loginRequiredAnnotation == null;
        }

        return true;
    }

    /**
     * Login verification.
     *
     * @param request
     */
    private void handleLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserRequest currentUser = (UserRequest) session.getAttribute(Constants.CURRENT_USER_IN_SESSION);
        if (currentUser == null) {
            throw new BizException(ExceptionStatusEnum.PERMISSION_DENY);
        }
    }
}
