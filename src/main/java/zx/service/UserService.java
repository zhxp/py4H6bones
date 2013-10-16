package zx.service;

import zx.domain.User;
import zx.exception.ExistedException;

public interface UserService {
    User createUser(String username) throws ExistedException;
    void save(User user);
    User findByUsername(String username);
    User findById(Long userId);
}
