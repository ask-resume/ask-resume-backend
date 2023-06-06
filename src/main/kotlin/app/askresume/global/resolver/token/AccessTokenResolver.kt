package app.askresume.global.resolver.token

import app.askresume.global.cookie.CookieProvider
import app.askresume.global.jwt.constant.JwtTokenType
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class AccessTokenResolver(
    private val cookieProvider: CookieProvider,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasAccessTokenAnnotation = parameter.hasParameterAnnotation(AccessToken::class.java)
        val hasTokenDto = TokenDto::class.java.isAssignableFrom(parameter.parameterType)
        return hasAccessTokenAnnotation && hasTokenDto
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val request = webRequest.nativeRequest as HttpServletRequest

        val accessTokenCookie = cookieProvider.getCookie(request.cookies, JwtTokenType.ACCESS.cookieName)
            ?: throw Exception("미구현 예외") // TODO

        return TokenDto(accessTokenCookie.value)
    }
}
