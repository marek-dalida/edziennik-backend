package ztp.edziennik.models;

import com.sun.istack.NotNull;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserGroup implements Serializable {
    @NotNull
    private Long userId;
    @NotNull
    private Long groupId;

    public UserGroup() {
    }

    public UserGroup(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroup userGroup = (UserGroup) o;
        return Objects.equals(userId, userGroup.userId) && Objects.equals(groupId, userGroup.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId);
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
