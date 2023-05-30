package app.askresume.external.gpt.config

import java.time.Duration

object GptConfig {
    const val MODEL = "gpt-3.5-turbo"
    const val TOP_P = 1.0
    const val MAX_TOKEN = 2000
    const val TEMPERATURE = 1.0
    val TIME_OUT = Duration.ofSeconds(300)
}

