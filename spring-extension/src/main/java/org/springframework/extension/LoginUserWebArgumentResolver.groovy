package org.springframework.extension

import org.springframework.core.MethodParameter
import org.springframework.security.core.Authentication
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import zx.domain.User
import zx.service.Services

import java.security.Principal

class LoginUserWebArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Login) != null && parameter.getParameterType().equals(User)
    }

    @Override
    Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Principal principal = webRequest.getUserPrincipal()
        if (principal != null) {
            if (!parameter.getParameterAnnotation(Login.class).active()) {
                return ((Authentication) principal).getPrincipal()
            } else {
                return Services.userService().findByUsername(principal.getName())
            }
        }
        return null
    }
}
