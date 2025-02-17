package fun.masttf.stuManager.impl;

import fun.masttf.stuManager.entity.Permission;
import fun.masttf.stuManager.entity.Role;
import fun.masttf.stuManager.repository.PermissionRepository;
import fun.masttf.stuManager.repository.RoleRepository;
import fun.masttf.stuManager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission findByName(String name) {
        return permissionRepository.findByName(name);
    }

    @Override
    @Transactional
    public Permission add(String name){
        if(permissionRepository.findByName(name) != null){
            throw new RuntimeException("Permission already exists");
        }
        Permission permission = new Permission();
        permission.setName(name);
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public void delete(String name){
        Permission permission = permissionRepository.findByName(name);
        if(permission != null){
            for(Role role : permission.getRoles()){
                roleRepository.deletePermission(role.getId(), permission.getId());
            }
            permissionRepository.delete(permission);
        }else{
            throw new RuntimeException("Permission not found");
        }
    }
}
