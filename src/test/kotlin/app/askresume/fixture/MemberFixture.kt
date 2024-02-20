package app.askresume.fixture

import app.askresume.RANDOM
import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.model.Member

object MemberFixture {

    fun user(): Member = Member(
        email = RANDOM.nextEmail(),
        memberType = MemberType.GOOGLE,
        locale = "kr",
        role = Role.USER,
        username = RANDOM.nextString(4, 20),
    )

    fun admin(): Member = Member(
        email = RANDOM.nextEmail(),
        memberType = MemberType.GOOGLE,
        locale = "kr",
        role = Role.ADMIN,
        username = RANDOM.nextString(4, 20),
    )

}