package app.askresume

import app.askresume.domain.member.constant.MemberType
import app.askresume.domain.member.constant.Role
import app.askresume.domain.member.model.Member

object MemberFixture {

    fun user(): Member = Member(
        email = TestUtils.randomEmail(),
        memberType = MemberType.GOOGLE,
        locale = "kr",
        role = Role.USER,
        username = TestUtils.randomString(4, 20),
    )

    fun admin(): Member = Member(
        email = TestUtils.randomEmail(),
        memberType = MemberType.GOOGLE,
        locale = "kr",
        role = Role.ADMIN,
        username = TestUtils.randomString(4, 20),
    )

}