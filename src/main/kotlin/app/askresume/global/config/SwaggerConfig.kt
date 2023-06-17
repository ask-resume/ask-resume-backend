package app.askresume.global.config

import app.askresume.global.resolver.memberinfo.MemberInfo
import com.fasterxml.classmate.TypeResolver
import io.swagger.annotations.ApiModelProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.RequestParameterBuilder
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
class SwaggerConfig(
    private val typeResolver: TypeResolver,
) {

    @Value("\${swagger.title}")
    private lateinit var TITLE: String

    @Value("\${swagger.description}")
    private lateinit var DESCRIPTION: String

    @Value("\${swagger.version}")
    private lateinit var VERSION: String

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .select() // ApiSelectorBuilder 생성
            .apis(RequestHandlerSelectors.basePackage("app.askresume.api")) // API 패키지 경로
            .paths(PathSelectors.ant("/api/**")) // path 조건에 따라서 API 문서화
            .build()
            .apiInfo(apiInfo()) // API 문서에 대한 정보 추가
            .useDefaultResponseMessages(false) // swagger에서 제공하는 기본 응답 코드 설명 제거
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf<SecurityScheme>(apiKey()))
            .globalRequestParameters(globalParameters())
            .ignoredParameterTypes(MemberInfo::class.java)
            .directModelSubstitute(Pageable::class.java, MyPageable::class.java) // JPA Pageable에 Swagger 적용
    }

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder()
            .title(TITLE)
            .description(DESCRIPTION)
            .version(VERSION)
            .build()
    }

    private fun globalParameters(): List<RequestParameter> {
        return listOf(
            RequestParameterBuilder()
                .name(HttpHeaders.ACCEPT_LANGUAGE)
                .description("한국어 ko-KR, 영어 en-US")
                .`in`(ParameterType.HEADER)
                .required(true)
                .build()
        )
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes))
    }

    private fun apiKey(): ApiKey {
        return ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header")
    }

    data class MyPageable(
        @ApiModelProperty(value = "페이지 번호")
        var page: Int? = null,

        @ApiModelProperty(value = "페이지 크기")
        var pageSize: Int? = null,

        @ApiModelProperty(value = "정렬 (사용 예시: 컬럼명,ASC 또는 DESC)")
        var sort: List<String>? = null
    )

}