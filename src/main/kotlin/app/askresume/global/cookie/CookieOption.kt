package app.askresume.global.cookie

import java.time.Duration

data class CookieOption(
    val name: String,
    val value: String = "",
    val httpOnly: Boolean = false,
    val secure: Boolean = false,
    val domain: String? = null,
    val path: String = "/",
    val maxAge: Duration? = null,
    val sameSite: String? = null,
)