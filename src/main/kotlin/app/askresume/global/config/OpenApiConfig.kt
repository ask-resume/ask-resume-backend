package app.askresume.global.config

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class OpenApiConfig(
    @Value("\${swagger.title}") private var title: String,
    @Value("\${swagger.description}") private var description: String,
    @Value("\${swagger.version}") private var version: String,
) {

    @Bean
    fun openAPI(): OpenAPI {

        val securitySchemeName = "apiKeyAuth" // 보안 스키마 이름
        val securitySchemes = listOf(
            SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .name(HttpHeaders.AUTHORIZATION) // 헤더 필드 이름 (이름을 변경하여 사용 가능)
                .`in`(SecurityScheme.In.HEADER)
        )
        val securityRequirements = listOf(SecurityRequirement().addList(securitySchemeName))
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(securitySchemeName, securitySchemes[0])
            )

            .info(getInfo())
            .servers(getServers())
            .addSecurityItem(securityRequirements[0])
    }

    // swagger 설명 info 작업
    private fun getInfo(): Info {
        return Info()
            .title(title)
            .description(description)
            .version(version)
    }

    // swagger에 server 종류 추가
    private fun getServers(): List<Server> {
        return listOf(
            Server().url("http://localhost:8080").description("localhost"),
            Server().url("http://dev.ask-resume.com").description("develop")
        )
    }

}