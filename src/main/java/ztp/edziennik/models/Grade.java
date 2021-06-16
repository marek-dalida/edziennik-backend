package ztp.edziennik.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gradeTypeId;
    private Long userId;
    private Float value;
    private Date creationDate;
    private Long teacherId;

    public Grade() {
    }

    public Grade(Long id, Long gradeTypeId, Long userId, Float value, Date creationDate, Long teacherId) {
        this.id = id;
        this.gradeTypeId = gradeTypeId;
        this.userId = userId;
        this.value = value;
        this.creationDate = creationDate;
        this.teacherId = teacherId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGradeTypeId() {
        return gradeTypeId;
    }

    public void setGradeTypeId(Long gradeTypeId) {
        this.gradeTypeId = gradeTypeId;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
