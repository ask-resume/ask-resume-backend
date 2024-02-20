package app.askresume.fixture

import app.askresume.RANDOM
import app.askresume.domain.member.model.Member
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.model.Submit

object SubmitFixture {

    fun submit(member: Member): Submit = Submit(
        title = RANDOM.nextString(10, 30),
        serviceType = ServiceType.INTERVIEW_MAKER,
        dataCount = RANDOM.nextInt(1, 5),
        member = member,
    )

}