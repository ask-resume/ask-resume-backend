package app.askresume.global.config.xss

import com.fasterxml.jackson.core.SerializableString
import com.fasterxml.jackson.core.io.CharacterEscapes
import com.fasterxml.jackson.core.io.SerializedString
import org.apache.commons.text.StringEscapeUtils

class HtmlCharacterEscapes : CharacterEscapes() {

    private val asciiEscapes: IntArray = standardAsciiEscapesForJSON()

    init {
        // XSS 방지 처리할 특수 문자 지정
        asciiEscapes['<'.code] = ESCAPE_CUSTOM
        asciiEscapes['>'.code] = ESCAPE_CUSTOM
        asciiEscapes['\"'.code] = ESCAPE_CUSTOM
        asciiEscapes['('.code] = ESCAPE_CUSTOM
        asciiEscapes[')'.code] = ESCAPE_CUSTOM
        asciiEscapes['#'.code] = ESCAPE_CUSTOM
        asciiEscapes['\''.code] = ESCAPE_CUSTOM
    }

    override fun getEscapeCodesForAscii() = asciiEscapes

    override fun getEscapeSequence(ch: Int): SerializableString {
        return SerializedString(
            StringEscapeUtils.escapeHtml4(
                ch.toChar().toString()
            )
        )
    }
}

