package app.askresume

import app.askresume.domain.job.model.JobMaster

object JobFixture {

    fun jobMaster() : JobMaster = JobMaster(
        masterName = RANDOM.nextString(3, 15)
    )

}