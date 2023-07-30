package app.askresume.global.resolver.memberinfo

import app.askresume.domain.member.constant.Role
import app.askresume.global.cookie.CookieProvider
import app.askresume.global.error.exception.NotAccessTokenTypeException
import app.askresume.global.jwt.constant.JwtTokenType
import app.askresume.global.jwt.service.TokenManager
import org.springframework.beans.factory.annotation.Value
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
    @Value("\${spring.profiles.active}") var profile: String
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasMemberInfoAnnotation = parameter.hasParameterAnnotation(MemberInfoResolver::class.java)
        val hasMemberInfo = MemberInfo::class.java.isAssignableFrom(parameter.parameterType)
        return hasMemberInfoAnnotation && hasMemberInfo
    }

    @Throws(Exception::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): MemberInfo {

        return if (profile.uppercase() == PROFILE_LOCAL) {
            MemberInfo(1, Role.USER)
        } else {
            val request = webRequest.nativeRequest as HttpServletRequest
            val accessTokenCookie = cookieProvider.getCookie(request.cookies, JwtTokenType.ACCESS.cookieName)
                ?: throw NotAccessTokenTypeException()
            val accessToken = accessTokenCookie.value

            val tokenClaims = tokenManager.getTokenClaims(accessToken)
            val memberId = (tokenClaims["memberId"] as Int).toLong()
            val role = tokenClaims["role"] as String

            MemberInfo(memberId, Role.from(role))
        }

    }

    companion object {
        private const val PROFILE_LOCAL = "LOCAL"
    }

}

