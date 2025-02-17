package fun.masttf.stuManager.service;
import fun.masttf.stuManager.entity.User;

import java.util.List;
public interface UserService {
    User add(String name, String password);

    void delete(String name);

    void addRole(String username, String roleName);

    void deleteRole(String username, String roleName);

    public User findByUsername(String username);
    public List<String> getUserPermissions(Long userId);
}
