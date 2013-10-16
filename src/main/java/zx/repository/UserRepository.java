package zx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zx.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsername(String name);
}
