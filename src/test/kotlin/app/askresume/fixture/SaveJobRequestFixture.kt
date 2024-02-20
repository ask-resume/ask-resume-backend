package app.askresume.fixture

import app.askresume.api.job.vo.SaveJobRequest

object SaveJobRequestFixture {

    fun saveJobRequest(
        englishJobName: String = "backend developer",
        koreaJobName: String = "백엔드 개발자",
    ) = SaveJobRequest(
        englishJobName = englishJobName,
        koreaJobName = koreaJobName,
    )

}