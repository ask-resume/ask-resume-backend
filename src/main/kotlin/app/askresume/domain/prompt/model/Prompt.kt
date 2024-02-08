package app.askresume.domain.prompt.model

import app.askresume.domain.BaseEntity
import app.askresume.domain.prompt.constant.PromptType
import org.hibernate.annotations.Comment
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE prompt SET is_deleted = true WHERE id = ?")
@Entity
class Prompt(
    promptType: PromptType,
    content: String,
    description: String? = null,

    @Comment(value = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : BaseEntity() {

    @Enumerated(EnumType.STRING)
    @Comment(value = "타입")
    @Column(length = 30, nullable = false)
    var promptType: PromptType = promptType
        protected set

    @Lob
    @Comment(value = "내용")
    @Column(nullable = false)
    var content: String = content
        protected set

    @Comment(value = "프롬프트 설명")
    @Column(length = 200)
    var description: String? = null
        protected set

}