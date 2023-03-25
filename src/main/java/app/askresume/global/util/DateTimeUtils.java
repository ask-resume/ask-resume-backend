package app.askresume.global.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Date와 관련된 내용 처리용 Util
 *
 * @author igor
 */
@UtilityClass
public class DateTimeUtils {

    /**
     * Date -> LocalDateTime 으로 형변환
     * @param date : LocalDateTime 으로 바꿀 Date
     */
    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
