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
    public void save(User user) {
        List<User> users = userRepository.findByUsername(user.getUsername());
        if (!users.isEmpty()) {
            if (user.getId() != null) {
                for (User u : users) {
                    if (!u.getId().equals(user.getId())) {
                        throw new ExistedException("登录名不能重复：" + user.getUsername());
                    }
                }
            }
        }
        if (user.getId() != null) {
            User supervisor = user.getSupervisor();
            StringBuilder sb = new StringBuilder();
            while (supervisor != null) {
                sb.append(" > ").append(supervisor.getId()).append('/').append(supervisor.getDisplayName());
                if (supervisor.getId().equals(user.getId())) {
                    throw new RuntimeException("医生主管循环引用：" + sb.toString());
                }
                supervisor = supervisor.getSupervisor();
            }
        }
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

    @Override
    public List<User> findAllDoctors() {
        return userRepository.findByAuthorityOrderByUsernameAsc("ROLE_USER");
    }

    @Override
    public List<User> findTopLevelDoctors() {
        return userRepository.findByAuthorityAndSupervisorIsNull("ROLE_USER");
    }
}
