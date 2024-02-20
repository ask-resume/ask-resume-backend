package app.askresume.fixture

import app.askresume.api.member.vo.ModifyInfoRequest

object ModifyInfoRequestFixture {

    fun modifyInfoRequest(
        username : String = "test kim",
        profile : String = "https://domain.com/img_110x110.jpg",
        ) = ModifyInfoRequest(
        username = username,
        profile = profile,
    )
}