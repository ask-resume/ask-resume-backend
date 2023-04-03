package app.askresume.global.config;

import app.askresume.global.config.xss.HtmlCharacterEscapes;
import app.askresume.global.interceptor.AdminAuthorizationInterceptor;
import app.askresume.global.interceptor.AuthenticationInterceptor;
import app.askresume.global.resolver.memberinfo.MemberInfoArgumentResolver;
import app.askresume.global.resolver.token.AuthorizationTokenResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberInfoArgumentResolver memberInfoArgumentResolver;
    private final AuthorizationTokenResolver authorizationTokenResolver;
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;
    private final ObjectMapper objectMapper;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name()
                )
                .maxAge(3600)
        ;
    }

    // 인터셉트 체크
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 토큰 체크
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/oauth/login",
                        "/api/login",
                        "/api/sign-up",
                        "/api/access-token/issue",
                        "/api/logout",
                        "/api/v1/resume/generate",
                        "/api/v1/jobs",
                        "/api/v1/extract/link",
                        "/api/v1/extract/pdf"
                );

        // 권한 체크
        registry.addInterceptor(adminAuthorizationInterceptor)
                .order(2)
                .addPathPatterns("/api/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
        resolvers.add(authorizationTokenResolver);
    }

    // Controller 상단 @Validated를 붙이고, @RequestParam 등을 유효성 검사를 할 수 있게 도와주는 bean
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonEscapeConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(copy);
    }

}
