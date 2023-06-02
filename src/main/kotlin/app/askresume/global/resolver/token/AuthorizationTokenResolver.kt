package app.askresume.global.resolver.token

import app.askresume.global.util.AuthorizationHeaderUtils
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class AuthorizationTokenResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasAuthorizationTokenAnnotation = parameter.hasParameterAnnotation(AuthorizationToken::class.java)
        val hasTokenDto = TokenDto::class.java.isAssignableFrom(parameter.parameterType)
        return hasAuthorizationTokenAnnotation && hasTokenDto
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val request = webRequest.nativeRequest as HttpServletRequest

        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader)

        val accessToken = authorizationHeader.split(" ")[1]

        return TokenDto(authorizationHeader, accessToken)
    }
}
