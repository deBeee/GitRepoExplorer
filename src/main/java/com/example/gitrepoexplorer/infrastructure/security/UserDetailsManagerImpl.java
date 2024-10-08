package com.example.gitrepoexplorer.infrastructure.security;

import com.example.gitrepoexplorer.domain.user.User;
import com.example.gitrepoexplorer.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

@AllArgsConstructor
@Log4j2
class UserDetailsManagerImpl implements UserDetailsManager {

    public static final String DEFAULT_USER_ROLE = "ROLE_USER";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new RuntimeException("not found user"));
    }

    @Override
    public void createUser(UserDetails user) {
        if (userExists(user.getUsername())) {
            log.warn("not saved user - already exists");
            throw new RuntimeException("not saved user - already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User createdUser = new User(
                user.getUsername(),
                encodedPassword,
                true,
                List.of(ADMIN_ROLE, DEFAULT_USER_ROLE)
        );
        User savedUser = userRepository.save(createdUser);
        log.info("Saved user with Id: " + savedUser.getId());
        // send email confirmation
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByEmail(username);
    }
}
