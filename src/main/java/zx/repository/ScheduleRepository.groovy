package zx.repository

import org.springframework.data.jpa.repository.JpaRepository
import zx.domain.Schedule

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPlanPatientPidAndDay(String pid, Date day)
    List<Schedule> findByPlanPatientPid(String pid)
}