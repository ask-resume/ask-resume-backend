package app.askresume.global.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

/**
 * Date와 관련된 내용 처리용 Util
 *
 * @author igor
 */
object DateTimeUtils {

    /**
     * Date -> LocalDateTime 으로 형변환
     * @param date : LocalDateTime 으로 바꿀 Date
     */
    fun convertToLocalDateTime(date: Date): LocalDateTime {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

}

