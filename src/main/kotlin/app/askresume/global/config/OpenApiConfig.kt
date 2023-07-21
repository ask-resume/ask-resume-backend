package app.askresume.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(JWT_TOKEN, getSecurityScheme()[0])
            )
            .info(getInfo())
            .servers(getServers())
            .addSecurityItem(getSecurityRequirements()[0])
    }

    // swagger 설명 info 작업
    private fun getInfo(): Info {
        return Info()
            .title(SWAGGER_TITLE)
            .description(SWAGGER_DESCRIPTION)
            .version(API_VERSION)
//            .termsOfService("http://swagger.io/terms/")
//            .contact(Contact().name("Igor").url("http://132262b.github.io").email("132262b@naver.com"))
//            .license(License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));
    }

    private fun getSecurityScheme(): List<SecurityScheme> {
        return listOf(
            SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .name(HttpHeaders.AUTHORIZATION) // 헤더 필드 이름 (이름을 변경하여 사용 가능)
                .`in`(SecurityScheme.In.HEADER)
        )
    }

    private fun getSecurityRequirements() = listOf(SecurityRequirement().addList(JWT_TOKEN))

    // swagger에 server 종류 추가
    private fun getServers(): List<Server> {
        return listOf(
            Server().url("http://localhost:8080").description("localhost"),
            Server().url("http://dev.ask-resume.com").description("develop")
        )
    }

    companion object {
        const val SWAGGER_TITLE = "ask-resume API DOCS "
        const val SWAGGER_DESCRIPTION = ""
        const val API_VERSION = "1.2.0"

        const val JWT_TOKEN = "jwt token"
    }

}