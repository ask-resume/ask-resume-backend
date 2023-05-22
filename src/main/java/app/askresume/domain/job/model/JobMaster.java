package app.askresume.domain.job.model;

import app.askresume.domain.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Where(clause = "deleted_at = 'Y'")
@SQLDelete(sql = "UPDATE job_master SET deleted_at = 'N' WHERE id = ?")
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class JobMaster extends BaseTimeEntity {

    @Builder.Default
    @OneToMany(mappedBy = "jobMaster", fetch = FetchType.LAZY)
    private List<Job> jobs = new ArrayList<>();

    @Comment(value = "작업마스터명")
    @Column(length = 150, nullable = false)
    private String masterName;

    @Comment(value = "삭제유무")
    @ColumnDefault("'Y'")
    @Column(length = 1, nullable = false)
    private String deletedAt;
}
