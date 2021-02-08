package uz.real.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.real.entity.Role;
import uz.real.entity.User;
import uz.real.repository.UserRepository;
import uz.real.security.UserDetailsImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationCheckService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return new UserDetailsImpl(
                    user.getFull_name(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getAge(),
                    user.getRole().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList()));
        } else throw new UsernameNotFoundException("Userneme not found...");
    }
}
