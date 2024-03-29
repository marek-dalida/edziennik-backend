package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ztp.edziennik.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.role = 'STUDENT'\n" +
            " and (u.firstName like %:search% or u.lastName like %:search% or u.email like %:search% )")
    List<User> findStudents(@Param("search") String search);
}
