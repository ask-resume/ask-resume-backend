package app.askresume.global.config

import app.askresume.global.jwt.service.TokenManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfig {

    @Value("\${token.access-token-expiration-time}")
    private lateinit var accessTokenExpirationTime: String

    @Value("\${token.refresh-token-expiration-time}")
    private lateinit var refreshTokenExpirationTime: String

    @Value("\${token.secret}")
    private lateinit var tokenSecret: String

    @Bean
    fun tokenManager(): TokenManager {
        return TokenManager(accessTokenExpirationTime, refreshTokenExpirationTime, tokenSecret)
    }

}