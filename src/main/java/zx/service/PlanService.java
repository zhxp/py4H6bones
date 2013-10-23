package zx.service;

import zx.domain.Patient;
import zx.domain.Plan;

import java.util.List;

public interface PlanService {
    Plan findById(Long id);
    void createPlan(Patient patient, List<Plan> plan);
    List<Plan> findByPid(String pid);
}
