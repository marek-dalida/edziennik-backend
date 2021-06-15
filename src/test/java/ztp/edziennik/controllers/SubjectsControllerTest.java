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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ztp.edziennik.constants.TestHelper;
import ztp.edziennik.repositories.SubjectRepository;
import ztp.edziennik.repositories.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SubjectsControllerTest extends TestHelper {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectRepository subjectRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp(){
        Mockito.when(userRepository.findByEmail(mockTeacher1.getEmail())).thenReturn(Optional.of(mockTeacher1));
        Mockito.when(subjectRepository.findById(999L)).thenReturn(Optional.empty());
        Mockito.when(subjectRepository.findById(777L)).thenReturn(Optional.of(mockSubject1));
    }

    @Test
    public void whenGetSubjectById_thenExceptionNotFoundObject() throws Exception{

        mockMvc.perform(delete(baseUrl +"/subjects/999")
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Subject with id= 999 not found"))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.request").value("uri=/api/subjects/999"));
    }

    @Test
    public void whenGetSubjectById_thenGetObject_with200Status() throws Exception {
        mockMvc.perform(get(baseUrl + "/subjects/777")
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockSubject1.getId()))
                .andExpect(jsonPath("$.subjectName").value(mockSubject1.getSubjectName()))
                .andExpect(jsonPath("$.subjectDesc").value(mockSubject1.getSubjectDesc()));
    }
}
