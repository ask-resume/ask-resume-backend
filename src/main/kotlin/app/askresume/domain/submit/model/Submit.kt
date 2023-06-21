package app.askresume.domain.submit.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.member.model.Member
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitStatus
import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
class Submit(
    title: String,
    serviceType: ServiceType,
    dataCount: Int,

    @ManyToOne
    @Comment("멤버 ID")
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    val member: Member
) : BaseTimeEntity() {

    @Comment("제목")
    @Column(nullable = false, length = 50)
    var title: String = title
        protected set

    @Enumerated(EnumType.STRING)
    @Comment("서비스 타입")
    @Column(nullable = false, length = 40)
    var serviceType: ServiceType = serviceType
        protected set

    @Enumerated(EnumType.STRING)
    @Comment("상태")
    @Column(name = "status", nullable = false, length = 30)
    var submitStatus: SubmitStatus = SubmitStatus.WAITING
        protected set

    @Comment("데이터 개수")
    @Column(nullable = false)
    var dataCount: Int = dataCount
        protected set

}