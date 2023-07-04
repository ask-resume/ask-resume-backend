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
class RefreshTokenResolver(
    private val cookieProvider: CookieProvider,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasRefreshTokenAnnotation = parameter.hasParameterAnnotation(RefreshToken::class.java)
        val hasTokenDto = TokenDto::class.java.isAssignableFrom(parameter.parameterType)
        return hasRefreshTokenAnnotation && hasTokenDto
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val request = webRequest.nativeRequest as HttpServletRequest

        val refreshTokenCookie = cookieProvider.getCookie(request.cookies, JwtTokenType.REFRESH.cookieName)

        return TokenDto(refreshTokenCookie.value)
    }
}
