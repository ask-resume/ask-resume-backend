package app.askresume.global.config

import app.askresume.global.config.xss.HtmlCharacterEscapes
import app.askresume.global.interceptor.AdminAuthorizationInterceptor
import app.askresume.global.interceptor.AuthenticationInterceptor
import app.askresume.global.resolver.memberinfo.MemberInfoArgumentResolver
import app.askresume.global.resolver.token.AccessTokenResolver
import app.askresume.global.resolver.token.RefreshTokenResolver
import com.fasterxml.jackson.databind.ObjectMapper
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.http.HttpMethod
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*


@Configuration
class WebConfig(
    private val memberInfoArgumentResolver: MemberInfoArgumentResolver,
    private val accessTokenResolver: AccessTokenResolver,
    private val refreshTokenResolver: RefreshTokenResolver,
    private val authenticationInterceptor: AuthenticationInterceptor,
    private val adminAuthorizationInterceptor: AdminAuthorizationInterceptor,
    private val objectMapper: ObjectMapper,
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:4000",
                "http://localhost:3000",
                "https://ask-resume.com",
                "http://ask-resume.com",
            )
            .allowedMethods(
                HttpMethod.GET.name,
                HttpMethod.POST.name,
                HttpMethod.PUT.name,
                HttpMethod.PATCH.name,
                HttpMethod.DELETE.name,
                HttpMethod.OPTIONS.name
            )
            .maxAge(3600)
    }

    // 인터셉트 체크
    override fun addInterceptors(registry: InterceptorRegistry) {
        // 토큰 체크
        registry.addInterceptor(authenticationInterceptor)
            .order(1)
            .addPathPatterns("/api/**")
            .excludePathPatterns(
                "/api/oauth/**/login",
                "/api/oauth/**/callback",
                "/api/login",
                "/api/sign-up",
                "/api/access-token/issue",
                "/api/logout",
                "/api/v1/resume/generate",
                "/api/v1/jobs",
                "/api/v1/extract/link",
                "/api/v1/extract/pdf",
                "/api/test/server",
                "/api/test/bind",
                "/api/generative/interview-maker",
            )

        // 권한 체크
        registry.addInterceptor(adminAuthorizationInterceptor)
            .order(2)
            .addPathPatterns("/api/admin/**")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(memberInfoArgumentResolver)
        resolvers.add(accessTokenResolver)
        resolvers.add(refreshTokenResolver)
    }


    // Controller 상단 @Validated를 붙이고, @RequestParam 등을 유효성 검사를 할 수 있게 도와주는 bean
    @Bean
    fun methodValidationPostProcessor(): MethodValidationPostProcessor? {
        return MethodValidationPostProcessor()
    }

    @Bean
    fun filterRegistrationBean(): FilterRegistrationBean<XssEscapeServletFilter>? {
        val filterRegistration = FilterRegistrationBean<XssEscapeServletFilter>()
        filterRegistration.filter = XssEscapeServletFilter()
        filterRegistration.order = 1
        filterRegistration.addUrlPatterns("/*")
        return filterRegistration
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        converters.add(jsonEscapeConverter())
    }

    @Bean
    fun jsonEscapeConverter(): MappingJackson2HttpMessageConverter? {
        val copy = objectMapper.copy()
        copy.factory.characterEscapes = HtmlCharacterEscapes()
        return MappingJackson2HttpMessageConverter(copy)
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = AcceptHeaderLocaleResolver()

        // 언어 & 국가정보가 없는 경우 미국으로 인식하도록 설정
        localeResolver.defaultLocale = Locale.US
        return localeResolver
    }

    @Bean
    fun messageSource(): ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setUseCodeAsDefaultMessage(true)
        messageSource.setBasenames("message/messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

}