package ztp.edziennik.models;

import ztp.edziennik.utils.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "grade")
public class UserGradeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gradeTypeId", referencedColumnName = "id")
    private GradeTypeData gradeType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userid")
    private Member student;

    private Float value;
    private Date creationDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacherId", referencedColumnName = "userId")
    private Member teacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getStudent() {
        return student;
    }

    public void setStudent(Member student) {
        this.student = student;
    }


    public GradeTypeData getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeTypeData gradeType) {
        this.gradeType = gradeType;
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

    public UserGradeData() {
    }
}
