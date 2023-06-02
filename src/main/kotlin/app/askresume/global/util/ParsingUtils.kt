package app.askresume.global.util

import com.vdurmont.emoji.EmojiParser


object ParsingUtils {

    /**
     * text속 emoji를 모두 제거합니다.
     *
     * @param text : emoji를 제가할 text
     */
    fun removeEmojis(text: String?): String = EmojiParser.removeAllEmojis(text)

    /**
     * text속 URL을 모두 제거합니다.
     *
     * @param text : URL을 제가할 text
     */
    fun removeUrls(text: String): String {
        //String regex = "(http|https)://[a-zA-Z0-9\\./\\?=_-]+";
        val regex = "(http|https)://[a-zA-Z0-9\\./\\?=_-]+.*?\n"
        return text.replace(regex.toRegex(), "")
    }

}

