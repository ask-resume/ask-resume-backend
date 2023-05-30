package app.askresume.domain.prompt.model

import app.askresume.domain.common.BaseTimeEntity
import app.askresume.domain.prompt.constant.PromptType
import org.hibernate.annotations.*
import javax.persistence.*
import javax.persistence.Entity

@SQLDelete(sql = "UPDATE prompt SET deleted_at = 'N' WHERE id = ?")
@Entity
class Prompt(

    @Enumerated(EnumType.STRING)
    @Comment(value = "타입")
    @Column(length = 30, nullable = false)
    val promptType: PromptType,

    @Lob
    @Comment(value = "내용")
    @Column(nullable = false)
    val content: String,

    @Comment(value = "타입")
    @Column(length = 200)
    val description: String? = null,

) : BaseTimeEntity()