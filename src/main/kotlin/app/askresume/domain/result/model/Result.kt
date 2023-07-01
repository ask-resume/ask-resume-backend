package app.askresume.domain.result.model

import app.askresume.domain.common.BaseTimeEntity
import org.hibernate.annotations.Comment
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Result(
    model: String,
    created: Long,
    promptTokens: Int,
    contentToken: Int,
    totalTokens: Int,
) : BaseTimeEntity() {

    @Comment("모델")
    @Column(nullable = false)
    var model: String = model
        protected set

    @Comment("created id")
    @Column(nullable = false)
    var created: Long = created
        protected set

    @Comment("프롬프트 token")
    @Column(nullable = false)
    var promptTokens: Int = promptTokens
        protected set

    @Comment("내용 token")
    @Column(nullable = false)
    var contentToken: Int = contentToken
        protected set

    @Comment("전체 token")
    @Column(nullable = false)
    var totalTokens: Int = totalTokens
        protected set

}