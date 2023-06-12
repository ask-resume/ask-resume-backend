package app.askresume.domain.submit.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.generative.interview.model.ResultInterviewMaker
import app.askresume.domain.submit.constant.ServiceType
import app.askresume.domain.submit.constant.SubmitStatus
import org.hibernate.annotations.Comment
import javax.persistence.*

@Entity
class Submit(
    title: String,
    serviceType: ServiceType,
    dataCount: Int,
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

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_id")
    var submitDataList: MutableList<SubmitData> = mutableListOf()
        protected set

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_id")
    var resultInterviewMakerList: MutableList<ResultInterviewMaker> = mutableListOf()
        protected set

    fun addSubmitList(submitList: List<SubmitData>) {
        submitDataList.addAll(submitList)
    }
}