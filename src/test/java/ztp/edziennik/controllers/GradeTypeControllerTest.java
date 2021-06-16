package ztp.edziennik.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import ztp.edziennik.repositories.GradeTypeRepository;
import ztp.edziennik.repositories.SubjectGroupRepository;
import ztp.edziennik.repositories.UserRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GradeTypeControllerTest extends TestHelper {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SubjectGroupRepository subjectGroupRepository;

    @MockBean
    private GradeTypeRepository gradeTypeRepository;

    @Before
    public void setUp() {
        Mockito.when(userRepository.findByEmail(mockTeacher1.getEmail())).thenReturn(Optional.of(mockTeacher1));
        Mockito.when(subjectGroupRepository.findById(mockGroup2.getId())).thenReturn(Optional.of(mockGroup2));
        Mockito.when(gradeTypeRepository.findById(mockGradeType2.getId())).thenReturn(Optional.of(mockGradeType2));
        Mockito.when(gradeTypeRepository.findById(mockGradeType1.getId())).thenReturn(Optional.of(mockGradeType1));
        Mockito.when(gradeTypeRepository.findById(999L)).thenReturn(Optional.empty());

    }

    @Test
    public void whenUserDeleteGradeTypeToNotHisGroup_thenNoPermissionException() throws Exception {
        mockMvc.perform(delete(baseUrl + "/grade/types/" + mockGradeType2.getId())
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("User " + mockTeacher1.getEmail() + " does not have permission to perform this operation"))
                .andExpect(jsonPath("$.statusCode").value(403))
                .andExpect(jsonPath("$.request").value("uri=/api/grade/types/" + mockGradeType2.getId()));
    }

    @Test
    @WithMockUser(username = "janusz.tracz@gmail.com", roles = {"TEACHER"})
    public void whenNonExistingUserDeleteGradeType_throwNoUserFoundException() throws Exception {
        mockMvc.perform(delete(baseUrl + "/grade/types/" + mockGradeType1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.statusCode").value(403))
                .andExpect(jsonPath("$.message").value("User with email= janusz.tracz@gmail.com not found"))
                .andExpect(jsonPath("$.request").value("uri=/api/grade/types/" + mockGradeType1.getId()));
    }


    @Test
    public void whenGetGradeTypeById_thenStatus200() throws Exception {
        mockMvc.perform(get(baseUrl + "/grade/types/" + mockGradeType1.getId())
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockGradeType1.getId()))
                .andExpect(jsonPath("$.groupId").value(mockGradeType1.getGroupId()))
                .andExpect(jsonPath("$.gradeWeight").value(mockGradeType1.getGradeWeight()))
                .andExpect(jsonPath("$.gradeName").value(mockGradeType1.getGradeName()))
                .andExpect(jsonPath("$.gradeDesc").value(mockGradeType1.getGradeDesc()));
    }

    @Test
    public void whenGetGradeTypeById_thenExceptionObjectNotFound() throws Exception {
        mockMvc.perform(get(baseUrl + "/grade/types/999")
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("GradeType with id= 999 not found"))
                .andExpect(jsonPath("$.request").value("uri=/api/grade/types/999"));
    }

}
