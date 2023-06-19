package app.askresume.scheduler

import app.askresume.global.util.LoggerUtil.log
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class ScheduleTasks {

    val log = log()

    @Scheduled(fixedDelay = 1000)
    fun alert() {
        log.info("현재 시간 : {}", Date())
    }

}