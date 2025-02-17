package fun.masttf.stuManager.repository;

import fun.masttf.stuManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO user_roles VALUES (?1, ?2)", nativeQuery = true)
    void addRole(Long user_id, Long role_id);

    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE user_id = ?1 AND role_id = ?2", nativeQuery = true)
    void deleteRole(Long user_id, Long role_id);
}
