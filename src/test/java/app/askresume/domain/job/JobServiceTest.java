package app.askresume.domain.job;

import app.askresume.ServiceTest;
import app.askresume.api.job.dto.response.JobResponse;
import app.askresume.domain.job.model.Job;
import app.askresume.domain.job.model.JobMaster;
import app.askresume.domain.job.service.JobService;
import app.askresume.domain.locale.constant.LocaleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JobServiceTest extends ServiceTest {

    @Autowired
    private JobService jobService;

    @DisplayName("직업데이터 영어로 조회 성공")
    @Test
    void occupationalDataSuccessfulInquiryInEnglish() {

        // given
        final String jobNameEn1 = "backend developer";
        final String jobNameEn2 = "front developer";
        final String jobNameKo1 = "백엔드 개발자";
        final String jobNameKo2 = "프론트 개발자";

        직업_및_마스터테이블_언어별_생성(jobNameEn1, jobNameKo1);
        직업_및_마스터테이블_언어별_생성(jobNameEn2, jobNameKo2);

        // when
        final List<JobResponse> response = jobService.findJobs(LocaleType.EN);

        // then
        assertThat(response.size()).isEqualTo(2);
        assertThat(response).extracting("name").contains(jobNameEn1, jobNameEn2);
    }

    @DisplayName("직업데이터 한국어로 조회 성공")
    @Test
    void occupationalDataSuccessfulInquiryInKorean() {

        // given
        final String jobNameEn1 = "backend developer";
        final String jobNameEn2 = "front developer";
        final String jobNameKo1 = "백엔드 개발자";
        final String jobNameKo2 = "프론트 개발자";

        직업_및_마스터테이블_언어별_생성(jobNameEn1, jobNameKo1);
        직업_및_마스터테이블_언어별_생성(jobNameEn2, jobNameKo2);

        // when
        final List<JobResponse> response = jobService.findJobs(LocaleType.KO);

        // then
        assertThat(response.size()).isEqualTo(2);
        assertThat(response).extracting("name").contains(jobNameKo1, jobNameKo2);
    }

    @DisplayName("직업 마스터 데이터 생성 성공")
    @Test
    void jobMasterTableDataGenerationSuccessful() {
        // given
        final String jobName = "ceo";

        // when
        JobMaster jobMaster = jobService.saveJobMaster(JobSteps.직업마스터_생성(jobName));

        // then
        assertThat(jobName).isEqualTo(jobMaster.getMasterName());
    }

    @DisplayName("직업 데이터 생성 성공")
    @Test
    void jobTableDataGenerationSuccessful() {
        // given
        final String jobName = "ceo";
        JobMaster jobMaster = jobService.saveJobMaster(JobSteps.직업마스터_생성(jobName));

        // when
        Job job = jobService.saveJob(JobSteps.직업_생성(jobMaster, jobName, LocaleType.EN));

        // then
        assertThat(jobName).isEqualTo(job.getName());
    }

    private void 직업_및_마스터테이블_언어별_생성(String jobNameEn, String jobNameKo) {
        JobMaster jobMaster = JobSteps.직업마스터_생성(jobNameEn);
        JobMaster saveJobMaster = jobService.saveJobMaster(jobMaster);

        Job enJob = JobSteps.직업_생성(saveJobMaster, jobNameEn, LocaleType.EN);
        Job koJob = JobSteps.직업_생성(saveJobMaster, jobNameKo, LocaleType.KO);

        jobService.saveJob(enJob);
        jobService.saveJob(koJob);
    }

}