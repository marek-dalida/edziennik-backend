package ztp.edziennik.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "grade")
public class GradeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "gradeTypeId", referencedColumnName = "id")
    private GradeTypeData gradeType;

    private Long userId;
    private Float value;
    private Date creationDate;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacherId", referencedColumnName = "userId")
    private Member teacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GradeTypeData getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeTypeData gradeType) {
        this.gradeType = gradeType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Member getTeacher() {
        return teacher;
    }

    public void setTeacher(Member teacher) {
        this.teacher = teacher;
    }

    public GradeData() {
    }
}
