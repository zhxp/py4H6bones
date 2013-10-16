package zx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zx.domain.SurgeryType;

public interface SurgeryTypeRepository extends JpaRepository<SurgeryType, Long> {
}
