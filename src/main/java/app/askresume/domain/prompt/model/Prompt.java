package app.askresume.domain.prompt.model;

import app.askresume.domain.common.BaseTimeEntity;
import app.askresume.domain.prompt.constant.PromptType;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;

@Getter
@Where(clause = "deleted_at = 'Y'")
@SQLDelete(sql = "UPDATE prompt SET deleted_at = 'N' WHERE id = ?")
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Prompt extends BaseTimeEntity {

    @Enumerated(EnumType.STRING)
    @Comment(value = "타입")
    @Column(length = 30, nullable = false)
    private PromptType promptType;

    @Lob
    @Comment(value = "내용")
    @Column(nullable = false)
    private String content;

    @Comment(value = "타입")
    @Column(length = 200)
    private String description;

    @ColumnDefault("'Y'")
    @Comment(value = "삭제유무")
    @Column(length = 1, nullable = false)
    private String deletedAt;

}
