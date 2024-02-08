package app.askresume.domain.job.repository

import app.askresume.JobFixture
import app.askresume.RepositoryTest
import app.askresume.domain.job.exception.JobNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
class JobMasterRepositoryKtTest {

    @Autowired
    private lateinit var jobMasterRepository: JobMasterRepository

    @DisplayName("직업 아이디를 조회하는 경우, 직업 정보가 조회된다.")
    @Test
    fun findJobMasterByIdTest() {
        // given
        val jobMaster = jobMasterRepository.save(JobFixture.jobMaster())

        // when
        val findJobMaster = jobMasterRepository.findJobMasterById(jobMaster.id !!)

        // then
        assertThat(findJobMaster.masterName).isEqualTo(jobMaster.masterName)
    }

    @DisplayName("없는 직업 아이디로 조회 하는경우, JobNotFoundException 반횐된다.")
    @Test
    fun findJobMasterByIdExtinctionTest() {
        // when & then
        assertThatThrownBy { jobMasterRepository.findJobMasterById(-1) }
            .isInstanceOf(JobNotFoundException::class.java)
    }

}