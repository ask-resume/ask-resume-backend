package app.askresume.oauth.service

import app.askresume.domain.member.constant.MemberType
import org.springframework.stereotype.Service

@Service
class SocialLoginApiServiceFactory(
    private val socialLoginApiServices: Map<String, SocialLoginApiService>
) {

    fun getSocialLoginApiService(memberType: MemberType): SocialLoginApiService? {
        var socialLoginApiServiceBeanName = ""

        if (MemberType.GOOGLE == memberType) {
            socialLoginApiServiceBeanName = "googleLoginApiServiceImpl"
        }
        return socialLoginApiServices[socialLoginApiServiceBeanName]
    }

}

