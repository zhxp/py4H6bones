package zx.service

import zx.domain.Schedule

public interface ScheduleService {
    Schedule getSchedule(String pid, Date when)
}