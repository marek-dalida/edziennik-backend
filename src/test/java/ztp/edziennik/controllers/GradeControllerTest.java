package ztp.edziennik.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ztp.edziennik.constants.TestHelper;
import ztp.edziennik.repositories.GradeRepository;
import ztp.edziennik.repositories.UserRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GradeControllerTest extends TestHelper {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private GradeRepository gradeRepository;

    @Before
    public void setUp() {
        Mockito.when(userRepository.findByEmail(mockTeacher1.getEmail())).thenReturn(Optional.of(mockTeacher1));
        Mockito.when(gradeRepository.findById(999L)).thenReturn(Optional.empty());
        Mockito.when(gradeRepository.findById(mockGrade1.getId())).thenReturn(Optional.of(mockGrade1));
    }

    @Test
    public void whenDeleteGradeById_thenExceptionGradeNotFound() throws Exception {
        mockMvc.perform(delete(baseUrl + "/grades/999")
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Grade with id= 999 not found"))
                .andExpect(jsonPath("$.request").value("uri=/api/grades/999"));
    }

    @Test
    public void whenGetGradeById_thenReturnGradeWith200Status() throws Exception {
        mockMvc.perform(get(baseUrl + "/grades/" + mockGrade1.getId())
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(mockGrade1.getId()))
                .andExpect(jsonPath("$.gradeTypeId").value(mockGrade1.getGradeTypeId()))
                .andExpect(jsonPath("$.userId").value(mockGrade1.getUserId()))
                .andExpect(jsonPath("$.value").value(mockGrade1.getValue()))
                .andExpect(jsonPath("$.teacherId").value(mockGrade1.getTeacherId()));
    }

    @Test
    @WithMockUser(username = "janusz.tracz@gmail.com", roles = {"TEACHER"})
    public void whenNonExistingUserDeleteGrade_throwNoUserFoundException() throws Exception {
        mockMvc.perform(delete(baseUrl + "/grades/" + mockGrade1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.statusCode").value(403))
                .andExpect(jsonPath("$.message").value("User with email= janusz.tracz@gmail.com not found"))
                .andExpect(jsonPath("$.request").value("uri=/api/grades/" + mockGrade1.getId()));
    }


}
