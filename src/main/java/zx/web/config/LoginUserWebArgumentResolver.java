package zx.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import zx.domain.User;
import zx.service.UserService;

import java.security.Principal;

public class LoginUserWebArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Login.class) != null
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception {
        Principal principal = webRequest.getUserPrincipal();
        if (principal != null) {
            if (!parameter.getParameterAnnotation(Login.class).active()) {
                return ((Authentication) principal).getPrincipal();
            } else {
                return userService.findByUsername(principal.getName());
            }
        }
        return null;
    }
}
