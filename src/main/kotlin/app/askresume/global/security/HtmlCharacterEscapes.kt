package app.askresume.global.security

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
        val charAt = ch.toChar()
        return if (Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt)) {
            val sb = StringBuilder()
            sb.append("\\u")
            sb.append(String.format("%04x", ch))
            SerializedString(sb.toString())
        } else {
            SerializedString(StringEscapeUtils.escapeHtml4(charAt.toString()))
        }
    }
}

