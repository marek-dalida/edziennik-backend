package ztp.edziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ztp.edziennik.models.UserGroup;
import ztp.edziennik.models.UserGroupId;

import javax.transaction.Transactional;

public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {
    @Transactional
    @Modifying
    @Query("delete from UserGroup ug where ug.userGroupId.userId = :userId and ug.userGroupId.groupId = :groupId")
    void deleteUserFromGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);
}
