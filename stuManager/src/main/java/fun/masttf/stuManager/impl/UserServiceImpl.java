package fun.masttf.stuManager.impl;

import fun.masttf.stuManager.entity.Permission;
import fun.masttf.stuManager.entity.Role;
import fun.masttf.stuManager.entity.User;
import fun.masttf.stuManager.repository.RoleRepository;
import fun.masttf.stuManager.repository.UserRepository;
import fun.masttf.stuManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public User add(String name, String password){
        if(userRepository.findByUsername(name) != null){
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUsername(name);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return userRepository.findByUsername(name);
    }

    @Override
    @Transactional
    public void delete(String name){
        User user = userRepository.findByUsername(name);
        if(user == null){
            throw new RuntimeException("User not found");
        }else{
            userRepository.delete(user);
        }

    }
    @Override
    @Transactional
    public void addRole(String username, String roleName){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            throw new RuntimeException("Role not found");
        }
        userRepository.addRole(user.getId(), role.getId());
    }

    @Override
    @Transactional
    public void deleteRole(String username, String roleName){
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            throw new RuntimeException("Role not found");
        }
        userRepository.deleteRole(user.getId(), role.getId());
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    @Transactional
    public List<String> getUserPermissions(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .collect(Collectors.toList());
    }
}
