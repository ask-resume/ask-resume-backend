package app.askresume.domain.generative.interview.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.submit.constant.Satisfaction
import org.hibernate.annotations.Comment
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class ResultInterviewMaker(
    question : String,
    bestAnswer : String,

) : BaseTimeEntity() {

    @Comment("질문")
    @Column(columnDefinition= "text",nullable = false)
    var question : String = question
        protected set

    @Comment("모범 답안")
    @Column(columnDefinition= "text",nullable = false)
    var bestAnswer : String = question
        protected set

    @Enumerated(EnumType.STRING)
    @Comment("만족도")
    @Column(nullable = false, length = 30)
    var satisfaction : Satisfaction = Satisfaction.NO_RESPONSE
        protected set


}