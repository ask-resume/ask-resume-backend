package app.askresume.global.config

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*

@Configuration
class WebConfig(

) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:4000",
                "http://localhost:3000",
                "https://ask-resume.com",
                "http://ask-resume.com"
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


    @Bean
    fun localeResolver(): LocaleResolver? {
        val localeResolver = AcceptHeaderLocaleResolver()

        // 언어 & 국가정보가 없는 경우 미국으로 인식하도록 설정
        localeResolver.defaultLocale = Locale.US
        return localeResolver
    }

}