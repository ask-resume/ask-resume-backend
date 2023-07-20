package app.askresume.global.resolver.memberinfo

import app.askresume.domain.member.constant.Role
import app.askresume.global.cookie.CookieProvider
import app.askresume.global.jwt.constant.JwtTokenType
import app.askresume.global.jwt.service.TokenManager
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class MemberInfoArgumentResolver(
    private val tokenManager: TokenManager,
    private val cookieProvider: CookieProvider,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasMemberInfoAnnotation = parameter.hasParameterAnnotation(MemberInfo::class.java)
        val hasMemberInfoDto = MemberInfoDto::class.java.isAssignableFrom(parameter.parameterType)
        return hasMemberInfoAnnotation && hasMemberInfoDto
    }

    @Throws(Exception::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): MemberInfoDto {
        // TODO POSTMAN에서 호출하기 힘들어서 일단 주석
        val request = webRequest.nativeRequest as HttpServletRequest
        val accessTokenCookie = cookieProvider.getCookie(request.cookies, JwtTokenType.ACCESS.cookieName)
        val accessToken = accessTokenCookie.value

        val tokenClaims = tokenManager.getTokenClaims(accessToken)
        val memberId = (tokenClaims["memberId"] as Int).toLong()
        val role = tokenClaims["role"] as String

        return MemberInfoDto(memberId, Role.from(role))
        //return MemberInfoDto(1, Role.USER)
    }
}

