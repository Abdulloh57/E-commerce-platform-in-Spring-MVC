package library.uz.repository;

import library.uz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query(value = """
            select r.role_name
            from roles r
            join user_role ur on ur.role_id = r.id
            where ur.user_id = :userId
            limit 1
            """, nativeQuery = true)
    String findRoleNameByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query(value = """
            insert into user_role(user_id, role_id)
            values (:userId, (select id from roles where role_name = :roleName))
            """, nativeQuery = true)
    void attachRoleToUser(@Param("userId") Integer userId, @Param("roleName") String roleName);
}
