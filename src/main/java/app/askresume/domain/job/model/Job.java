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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jobMasterId", nullable = false)
    private JobMaster jobMaster;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 5, nullable = false)
    @Enumerated(EnumType.STRING)
    private LocaleType locale;

    @ColumnDefault("'Y'")
    @Column(length = 1, nullable = false)
    private String deletedAt;

}
