package app.askresume.global.util;

import com.vdurmont.emoji.EmojiParser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParsingUtils {


    /**
     * text속 emoji를 모두 제거합니다.
     *
     * @param text : emoji를 제가할 text
     */
    public static String removeEmojis(String text) {
        return EmojiParser.removeAllEmojis(text);
    }

    /**
     * text속 URL을 모두 제거합니다.
     *
     * @param text : URL을 제가할 text
     */
    public static String removeUrls(String text) {
        //String regex = "(http|https)://[a-zA-Z0-9\\./\\?=_-]+";
        String regex = "(http|https)://[a-zA-Z0-9\\./\\?=_-]+.*?\n";
        return text.replaceAll(regex, "");
    }
}
