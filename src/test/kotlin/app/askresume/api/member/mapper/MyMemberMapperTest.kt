package app.askresume.api.member.mapper

import app.askresume.RANDOM
import app.askresume.domain.member.dto.MemberInfoDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MyMemberMapperTest {

    @Test
    fun `memberInfoDto를 MemberInfoResponse 형태로 변환한다`() {
        // given
        val memberInfoDto = RANDOM.nextObject(MemberInfoDto::class)

        // when
        val response = MyMemberMapper.memberInfoResponseOf(memberInfoDto)

        // then
        assertThat(memberInfoDto.memberId).isEqualTo(response.memberId)
        assertThat(memberInfoDto.email).isEqualTo(response.email)
        assertThat(memberInfoDto.username).isEqualTo(response.username)
        assertThat(memberInfoDto.profile).isEqualTo(response.profile)
        assertThat(memberInfoDto.role).isEqualTo(response.role)
        assertThat(memberInfoDto.locale).isEqualTo(response.locale)
    }

    @Test
    fun `memberInfoDto를 MemberInfoResponse 형태로 변환한다 (extracting)`() {
        // given
        val memberInfoDto = RANDOM.nextObject(MemberInfoDto::class)

        // when
        val response = MyMemberMapper.memberInfoResponseOf(memberInfoDto)

        // then
        assertThat(memberInfoDto)
            .extracting("memberId", "email", "username", "profile", "role", "locale")
            .containsExactly(response.memberId, response.email, response.username, response.profile, response.role, response.locale)
    }

}