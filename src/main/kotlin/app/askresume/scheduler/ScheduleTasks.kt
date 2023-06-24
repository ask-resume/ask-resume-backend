package app.askresume.scheduler

import app.askresume.scheduler.job.GenerativeModelJob
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduleTasks(
    private val generativeModelJob : GenerativeModelJob
) {

    @Scheduled(fixedDelay = 1000)
    fun generativeModelJob() {
        generativeModelJob.execute()
    }

}