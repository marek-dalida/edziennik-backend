package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ztp.edziennik.models.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select u from Member u where u.role = 'STUDENT'\n" +
            " and (u.firstName like %:search% or u.lastName like %:search% or u.email like %:search% )")
    List<Member> findStudents(@Param("search") String search);
}
