package fun.masttf.stuManager.service;

import fun.masttf.stuManager.entity.Role;

public interface RoleService {
    Role add(String name);

    void delete(String name);

    Role findByName(String name);

    void addPermission(String RoleName, String PermissionName);

    void deletePermission(String RoleName, String PermissionName);
}
