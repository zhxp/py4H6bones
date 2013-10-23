package zx.service;

import zx.domain.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
    User findById(Long userId);
    List<User> findAllDoctors();
    List<User> findTopLevelDoctors();
}
