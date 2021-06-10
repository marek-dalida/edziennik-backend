package ztp.edziennik.models;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class UserGroup {

    @EmbeddedId
    private UserGroupId userGroupId;

    public UserGroup() {
    }

    public UserGroup(UserGroupId userGroupId) {
        this.userGroupId = userGroupId;
    }

    public UserGroupId getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(UserGroupId userGroupId) {
        this.userGroupId = userGroupId;
    }
}
