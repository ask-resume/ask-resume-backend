package app.askresume.domain.member;

import app.askresume.ServiceTest;
import app.askresume.domain.member.constant.MemberType;
import app.askresume.domain.member.constant.Role;
import app.askresume.domain.member.model.Member;
import app.askresume.domain.member.exception.DuplicateMemberException;
import app.askresume.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberServiceTest extends ServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("회원생성 성공")
    @Test
    void successfulMemberCreation() {
        // given
        final Long memberId = 1L;

        final String email = "member@domain.com";
        final Member member = MemberSteps.회원정보_생성(email);

        // when
        memberService.registerMember(member);
        final Member memberResponse = memberService.findMemberById(memberId);

        // then
        assertThat(memberResponse.getEmail()).isEqualTo(email);
        assertThat(memberResponse.getUsername()).isEqualTo(MemberSteps.USERNAME);
        assertThat(memberResponse.getProfile()).isEqualTo(MemberSteps.PROFILE);
        assertThat(memberResponse.getRole()).isEqualTo(Role.USER);
        assertThat(memberResponse.getMemberType()).isEqualTo(MemberType.GOOGLE);
    }

    @DisplayName("회원생성 도중 이메일 중복으로 Exception 발생")
    @Test
    void exceptionOccursDueToDuplicateEmailDuringMemberCreation() {
        // given
        final String email = "member@domain.com";
        final Member firstMember = MemberSteps.회원정보_생성(email);
        memberService.registerMember(firstMember);

        final Member laterMember = MemberSteps.회원정보_생성(email);

        // when && then
        assertThatThrownBy(() -> memberService.registerMember(laterMember))
                .isInstanceOf(DuplicateMemberException.class);
    }

    @DisplayName("회원정보 수정 성공")
    @Test
    @Transactional
    void successfulModificationOfMemberInformation() {
        // given
        final Long memberId = 1L;

        final String email = "member@domain.com";
        final Member member = MemberSteps.회원정보_생성(email);

        final Member registeredMember = memberService.registerMember(member);

        // when
        final String username = "길동홍";
        final String profile = "https://domain.com/img_220x220.jpg";
        memberService.modifyMemberInfo(registeredMember, MemberSteps.회원정보_수정(username, profile));

        final Member memberResponse = memberService.findMemberById(memberId);

        // then
        assertThat(memberResponse.getEmail()).isEqualTo(email);
        assertThat(memberResponse.getUsername()).isEqualTo(username);
        assertThat(memberResponse.getProfile()).isEqualTo(profile);
        assertThat(memberResponse.getRole()).isEqualTo(Role.USER);
        assertThat(memberResponse.getMemberType()).isEqualTo(MemberType.GOOGLE);
    }

}