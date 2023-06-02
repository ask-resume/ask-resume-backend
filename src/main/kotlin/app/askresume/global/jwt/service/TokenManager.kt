package app.askresume.global.jwt.service

import app.askresume.domain.member.constant.Role
import app.askresume.global.error.ErrorCode
import app.askresume.global.error.exception.AuthenticationException
import app.askresume.global.jwt.constant.GrantType
import app.askresume.global.jwt.constant.TokenType
import app.askresume.global.jwt.dto.JwtTokenDto
import app.askresume.global.util.LoggerUtil.log
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.nio.charset.StandardCharsets
import java.util.*

class TokenManager(
    private val accessTokenExpirationTime: String,
    private val refreshTokenExpirationTime: String,
    private val tokenSecret: String,
) {

    private val log = log()

    fun createJwtTokenDto(memberId: Long?, role: Role): JwtTokenDto {
        val accessTokenExpireTime = createAccessTokenExpireTime()
        val refreshTokenExpireTime = createRefreshTokenExpireTime()

        val accessToken = createAccessToken(memberId, role, accessTokenExpireTime)
        val refreshToken = createRefreshToken(memberId, refreshTokenExpireTime)

        return JwtTokenDto(
            GrantType.BEARER.type,
            accessToken,
            accessTokenExpireTime,
            refreshToken,
            refreshTokenExpireTime
        )
    }

    fun createAccessTokenExpireTime(): Date {
        return Date(System.currentTimeMillis() + accessTokenExpirationTime.toLong())
    }

    fun createRefreshTokenExpireTime(): Date {
        return Date(System.currentTimeMillis() + refreshTokenExpirationTime.toLong())
    }

    fun createAccessToken(memberId: Long?, role: Role, expirationTime: Date): String {
        return Jwts.builder()
            .setSubject(TokenType.ACCESS.name)      // 토큰 제목
            .setIssuedAt(Date())                    // 토큰 발급 시간
            .setExpiration(expirationTime)          // 토큰 만료 시간
            .claim("memberId", memberId)      // 회원 아이디
            .claim("role", role)              // 유저 role
            .signWith(SignatureAlgorithm.HS512, tokenSecret.toByteArray(StandardCharsets.UTF_8))
            .setHeaderParam("typ", "JWT")
            .compact()
    }

    fun createRefreshToken(memberId: Long?, expirationTime: Date): String {
        return Jwts.builder()
            .setSubject(TokenType.REFRESH.name)     // 토큰 제목
            .setIssuedAt(Date())                    // 토큰 발급 시간
            .setExpiration(expirationTime)          // 토큰 만료 시간
            .claim("memberId", memberId)      // 회원 아이디
            .signWith(SignatureAlgorithm.HS512, tokenSecret.toByteArray(StandardCharsets.UTF_8))
            .setHeaderParam("typ", "JWT")
            .compact()
    }

    fun validateToken(token: String) {
        try {
            Jwts.parser().setSigningKey(tokenSecret.toByteArray(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            log.info("token 만료", e)
            throw AuthenticationException(ErrorCode.TOKEN_EXPIRED)
        } catch (e: Exception) {
            log.info("유효하지 않은 token", e)
            throw AuthenticationException(ErrorCode.NOT_VALID_TOKEN)
        }
    }

    fun getTokenClaims(token: String): Claims {
        val claims =
            try {
                Jwts.parser().setSigningKey(tokenSecret.toByteArray(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).body
            } catch (e: Exception) {
                log.info("유효하지 않은 token", e)
                throw AuthenticationException(ErrorCode.NOT_VALID_TOKEN)
            }

        return claims
    }
}

