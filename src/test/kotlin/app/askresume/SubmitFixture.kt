package app.askresume

import app.askresume.domain.member.model.Member
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.model.Submit

object SubmitFixture {

    fun submit(member: Member): Submit = Submit(
        title = TestUtils.randomString(10, 30),
        serviceType = ServiceType.INTERVIEW_MAKER,
        dataCount = TestUtils.randomInt(1, 5),
        member = member,
    )

}