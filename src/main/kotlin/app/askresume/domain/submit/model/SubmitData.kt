package app.askresume.domain.submit.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.submit.constant.SubmitDataStatus
import com.vladmihalcea.hibernate.type.json.JsonType
import org.hibernate.annotations.Comment
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "is_deleted = 'Y'")
@TypeDef(name = "json", typeClass = JsonType::class)
@Entity
class SubmitData(
    parameter: Map<String, Any>,

    @ManyToOne(fetch = FetchType.LAZY)
    val submit: Submit,
) : BaseTimeEntity() {

    @Comment("제출한 데이터")
    @Type(type = "json")
    @Column(columnDefinition = "json")
    var parameter: Map<String, Any> = parameter
        protected set

    @Comment("시도 횟수")
    @Column(nullable = false)
    var attempts: Int = 0
        protected set

    @Enumerated(EnumType.STRING)
    @Comment("상태")
    @Column(name = "status", nullable = false, length = 30)
    var submitDataStatus: SubmitDataStatus = SubmitDataStatus.WAITING
        protected set

    /** 비즈니스 로직 **/

    fun updateStatus(changeStatus: SubmitDataStatus) {
        this.submitDataStatus = changeStatus
    }

}