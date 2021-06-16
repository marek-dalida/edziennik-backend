package ztp.edziennik.models;

import javax.persistence.*;

@Entity
public class SubjectGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long subjectId;
    private String groupName;
    private Long groupTeacherId;

    public SubjectGroup() {
    }

    public SubjectGroup(Long id, Long subjectId, String groupName, Long groupTeacherId) {
        this.id = id;
        this.subjectId = subjectId;
        this.groupName = groupName;
        this.groupTeacherId = groupTeacherId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupTeacherId() {
        return groupTeacherId;
    }

    public void setGroupTeacherId(Long groupTeacherId) {
        this.groupTeacherId = groupTeacherId;
    }
}
