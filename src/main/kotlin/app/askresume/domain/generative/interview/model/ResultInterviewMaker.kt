package app.askresume.domain.generative.interview.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.submit.constant.Satisfaction
import app.askresume.domain.submit.model.Submit
import app.askresume.domain.submit.model.SubmitData
import org.hibernate.annotations.Comment
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "is_deleted = false")
@Entity
class ResultInterviewMaker(
    question : String,
    bestAnswer : String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val submit: Submit,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_data_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val submitData: SubmitData,
) : BaseTimeEntity() {

    @Comment("질문")
    @Column(columnDefinition= "text",nullable = false)
    var question : String = question
        protected set

    @Comment("모범 답안")
    @Column(columnDefinition= "text",nullable = false)
    var bestAnswer : String = bestAnswer
        protected set

    @Enumerated(EnumType.STRING)
    @Comment("만족도")
    @Column(nullable = false, length = 30)
    var satisfaction : Satisfaction = Satisfaction.NO_RESPONSE
        protected set
}