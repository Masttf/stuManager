package fun.masttf.stuManager.repository;

import fun.masttf.stuManager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {
    Role findByName(String name);
    @Modifying
    @Query(value = "INSERT INTO role_permissions VALUES (?1, ?2)", nativeQuery = true)
    void addPermission(Long role_id, Long permission_id);

    @Modifying
    @Query(value = "DELETE FROM role_permissions WHERE role_id = ?1 AND permission_id = ?2", nativeQuery = true)
    void deletePermission(Long role_id, Long permission_id);
}
