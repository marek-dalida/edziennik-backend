package ztp.edziennik.constants;

import ztp.edziennik.models.GradeType;
import ztp.edziennik.models.Subject;
import ztp.edziennik.models.SubjectGroup;
import ztp.edziennik.models.User;
import ztp.edziennik.utils.Role;

public class TestHelper {
    public static String baseUrl = "http://localhost:8080/api";

    public static String tokenTeacher1 = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYXBpIiwiYXVkIjoic2VjdXJlLWFwcCIsInN1YiI6Imphbi5rb3dhbEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9URUFDSEVSIn0.Q8wjywpwEzvSABKpXos7GyI2ZV2xxylE07AFKONDMs9sprioEYsKgWei-K9OEFaSsRNegsuy318k7dnkLN59vQ";
    public static User mockTeacher1 = new User(7L, "Jan", "Kowal",
            "jan.kowal@gmail.com", "$2a$04$TghPbVD07wCUDtLmJtplNuo0FOb4ktwtLSwXN5SpwXmLdi1jnLSR6", Role.TEACHER);
    public static Subject mockSubject1 = new Subject(1L, "Historia", "Historia Podstawowa klasy 4-8");

    public static SubjectGroup mockGroup1 = new SubjectGroup(1L, 1L, "Historia Klasa 5c", 7L);
    public static SubjectGroup mockGroup2 = new SubjectGroup(2L, 1L, "Historia Klasa 5d", 1L);

    public static GradeType mockGradeType1 = new GradeType(1L, 1L, 1, "Kartkówka", "Kartkówka Starożytny Egipt");
    public static GradeType mockGradeType2 = new GradeType(2L, 2L, 1, "Kartkówka", "Kartkówka Starożytna Grecja");


}
