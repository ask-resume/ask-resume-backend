package app.askresume.domain.job.model;

import app.askresume.domain.common.BaseTimeEntity;
import app.askresume.domain.locale.constant.LocaleType;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;

@Getter
@Where(clause = "deleted_at = 'Y'")
@SQLDelete(sql = "UPDATE job SET deleted_at = 'N' WHERE id = ?")
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Job extends BaseTimeEntity {

    @ManyToOne
    @Comment(value = "직업마스터 ID")
    @JoinColumn(name = "jobMasterId", nullable = false)
    private JobMaster jobMaster;

    @Comment(value = "직업명")
    @Column(length = 150, nullable = false)
    private String name;

    @Comment(value = "언어")
    @Column(length = 5, nullable = false)
    @Enumerated(EnumType.STRING)
    private LocaleType locale;

    @ColumnDefault("'Y'")
    @Comment(value = "삭제유무")
    @Column(length = 1, nullable = false)
    private String deletedAt;

}
