package app.askresume.global.resolver.memberinfo

import app.askresume.domain.member.constant.Role
import app.askresume.global.jwt.service.TokenManager
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class MemberInfoArgumentResolver(
    private val tokenManager: TokenManager,
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
        val request = webRequest.nativeRequest as HttpServletRequest
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val token = authorizationHeader.split(" ")[1]

        val tokenClaims = tokenManager.getTokenClaims(token)
        val memberId = (tokenClaims["memberId"] as Int).toLong()
        val role = tokenClaims["role"] as String

        return MemberInfoDto(memberId, Role.from(role))
    }
}

