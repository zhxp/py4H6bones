package zx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zx.domain.User;
import zx.exception.ExistedException;
import zx.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(String username) throws ExistedException {
        User user = findByUsername(username);
        if (user != null) {
            throw new ExistedException(username);
        }
        user = new User();
        user.setUsername(username);
        save(user);
        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = userRepository.findByUsername(username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findById(Long userId) {
        return userId == null ? null : userRepository.findOne(userId);
    }
}
