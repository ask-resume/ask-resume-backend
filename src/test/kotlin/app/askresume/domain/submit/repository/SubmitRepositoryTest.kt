package app.askresume.domain.submit.repository

import app.askresume.fixture.MemberFixture
import app.askresume.RepositoryTest
import app.askresume.fixture.SubmitFixture
import app.askresume.domain.member.repository.MemberRepository
import app.askresume.domain.submit.exception.SubmitNotFoundException
import app.askresume.domain.submit.exception.UnauthorizedSubmitAccessException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
class SubmitRepositoryTest {

    @Autowired
    private lateinit var submitRepository: SubmitRepository

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @DisplayName("제출ID르 조회하는 경우, 제출 정보가 조회된다.")
    @Test
    fun findSubmitByIdTest() {
        //given
        val member = memberRepository.save(MemberFixture.user())
        val submit = submitRepository.save(SubmitFixture.submit(member))

        // when
        val findSubmit = submitRepository.findSubmitById(submit.id !!)

        // then
        assertThat(findSubmit.title).isEqualTo(submit.title)
        assertThat(findSubmit.serviceType).isEqualTo(submit.serviceType)
        assertThat(findSubmit.submitStatus).isEqualTo(submit.submitStatus)
        assertThat(findSubmit.dataCount).isEqualTo(submit.dataCount)
    }

    @DisplayName("없는 제출 아이디로 조회 하는경우, SubmitNotFoundException이 반횐된다.")
    @Test
    fun findSubmitByIdExceptionTest() {
        // when & then
        assertThatThrownBy { submitRepository.findSubmitById(- 1) }
            .isInstanceOf(SubmitNotFoundException::class.java)
    }

    @DisplayName("해당 맴버가 제출한 경우, true 가 반환된다.")
    @Test
    fun existsByIdAndMemberTrueTest() {
        // given
        val member = memberRepository.save(MemberFixture.user())
        val submitId = submitRepository.save(SubmitFixture.submit(member)).id !!

        // when
        val exists = submitRepository.existsByIdAndMember(submitId, member)

        // then
        assertThat(exists).isTrue()
    }

    @DisplayName("해당 맴버가 제출한 경우가 아닌경우, false 가 반환된다.")
    @Test
    fun existsByIdAndMemberFalseTest() {
        // given
        val memberA = memberRepository.save(MemberFixture.user())
        val memberB = memberRepository.save(MemberFixture.user())
        val submitId = submitRepository.save(SubmitFixture.submit(memberB)).id !!

        // when
        val exists = submitRepository.existsByIdAndMember(submitId, memberA)

        // then
        assertThat(exists).isFalse()
    }

    @DisplayName("해당 맴버가 제출한 경우가 아닌경우, UnauthorizedSubmitAccessException이 반환된다.")
    @Test
    fun existsSubmitByIdAndMemberExceptionTest() {
        // given
        val memberA = memberRepository.save(MemberFixture.user())
        val memberB = memberRepository.save(MemberFixture.user())
        val submitId = submitRepository.save(SubmitFixture.submit(memberB)).id !!

        // when
        assertThatThrownBy { submitRepository.existsSubmitByIdAndMember(submitId, memberA) }
            .isInstanceOf(UnauthorizedSubmitAccessException::class.java)
    }
}