package app.askresume.domain.job;

import app.askresume.domain.job.model.Job;
import app.askresume.domain.job.model.JobMaster;
import app.askresume.domain.locale.constant.LocaleType;

public class JobSteps {

    public static JobMaster 직업마스터_생성(String name) {
        return JobMaster
                .builder()
                .masterName(name)
                .build();
    }

    public static Job 직업_생성(JobMaster jobMaster, String name, LocaleType locale) {
        return Job.builder()
                .jobMaster(jobMaster)
                .locale(locale)
                .name(name)
                .build();
    }


}
