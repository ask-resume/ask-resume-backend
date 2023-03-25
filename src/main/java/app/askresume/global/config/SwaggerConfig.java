package app.askresume.global.config;

import app.askresume.global.resolver.memberinfo.MemberInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.title}")
    private String TITLE;

    @Value("${swagger.description}")
    private String DESCRIPTION;

    @Value("${swagger.version}")
    private String VERSION;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select() // ApiSelectorBuilder 생성
                .apis(RequestHandlerSelectors.basePackage("app.askresume.api")) // API 패키지 경로 todo 패키지 경로 수정
                .paths(PathSelectors.ant("/api/**")) // path 조건에 따라서 API 문서화 todo API 경로 수정
                .build()
                .apiInfo(apiInfo()) // API 문서에 대한 정보 추가
                .useDefaultResponseMessages(false) // swagger에서 제공하는 기본 응답 코드 설명 제거
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .ignoredParameterTypes(MemberInfo.class)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION)
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, "header");
    }

}