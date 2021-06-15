package ztp.edziennik.constants;

import ztp.edziennik.models.User;
import ztp.edziennik.utils.Role;

public class TestHelper {
    public static String STUDENT = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYXBpIiwiYXVkIjoic2VjdXJlLWFwcCIsInN1YiI6Imphbi5ub3dha0BnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9TVFVERU5UIn0.5QMkYe4ZycOMDHkGqiulMtusRU-xCzdBQJwjeFEvWURmA0RDmoM0J9yl06-v2q7ZT_zKkUoVU1SLn3hNgD987w";
    public static String tokenTeacher1 = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYXBpIiwiYXVkIjoic2VjdXJlLWFwcCIsInN1YiI6Imphbi5rb3dhbEBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9URUFDSEVSIn0.Q8wjywpwEzvSABKpXos7GyI2ZV2xxylE07AFKONDMs9sprioEYsKgWei-K9OEFaSsRNegsuy318k7dnkLN59vQ";
    public static User mockTeacher1 = new User(7L, "Jan", "Kowal",
            "jan.kowal@gmail.com", "$2a$04$TghPbVD07wCUDtLmJtplNuo0FOb4ktwtLSwXN5SpwXmLdi1jnLSR6", Role.TEACHER);
    public static String baseUrl = "http://localhost:8080/api/";
}