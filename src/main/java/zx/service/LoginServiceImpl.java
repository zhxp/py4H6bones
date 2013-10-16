package zx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zx.domain.User;
import zx.repository.UserRepository;

import java.util.List;

@Service("loginService")
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService, UserDetailsService, AuditorAware<User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userList = userRepository.findByUsername(username);
        if (userList.isEmpty()) {
            throw new UsernameNotFoundException(username);
        } else {
            return userList.get(0);
        }
    }

    @Override
    public User getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (User) authentication.getPrincipal();
        } else {
            return null;
        }
    }

    @Override
    public User getCurrentUser() {
        return getCurrentAuditor();
    }
}
