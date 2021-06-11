package ztp.edziennik.models;


import javax.persistence.*;

@Entity
@Table(name = "grade_type")
public class GradeTypeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "groupId", referencedColumnName = "id")
    private SubjectGroup group;

    private Integer gradeWeight;
    private String gradeName;
    private String gradeDesc;


    public GradeTypeData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubjectGroup getGroup() {
        return group;
    }

    public void setGroup(SubjectGroup group) {
        this.group = group;
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
