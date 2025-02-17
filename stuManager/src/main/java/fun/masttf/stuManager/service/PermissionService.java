package fun.masttf.stuManager.service;

import fun.masttf.stuManager.entity.Permission;

public interface PermissionService {
    public abstract Permission findByName(String name);

    public abstract Permission add(String name);

    public abstract void delete(String name);
}
