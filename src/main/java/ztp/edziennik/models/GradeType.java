package ztp.edziennik.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GradeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long groupId;
    private Integer gradeWeight;
    private String gradeName;
    private String gradeDesc;

    public GradeType(Long id, Long groupId, Integer gradeWeight, String gradeName, String gradeDesc) {
        this.id = id;
        this.groupId = groupId;
        this.gradeWeight = gradeWeight;
        this.gradeName = gradeName;
        this.gradeDesc = gradeDesc;
    }

    public GradeType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getGradeWeight() {
        return gradeWeight;
    }

    public void setGradeWeight(Integer gradeWeight) {
        this.gradeWeight = gradeWeight;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getGradeDesc() {
        return gradeDesc;
    }

    public void setGradeDesc(String gradeDesc) {
        this.gradeDesc = gradeDesc;
    }
}
