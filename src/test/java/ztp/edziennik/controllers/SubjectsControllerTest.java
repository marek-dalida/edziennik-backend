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
import org.springframework.test.web.servlet.MvcResult;
import ztp.edziennik.constants.TestHelper;
import ztp.edziennik.models.Subject;
import ztp.edziennik.models.User;
import ztp.edziennik.repositories.SubjectRepository;
import ztp.edziennik.repositories.UserRepository;
import ztp.edziennik.services.SubjectService;

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
        User user = mockTeacher1;
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(subjectRepository.findById(999L)).thenReturn(Optional.empty());
    }

    @Test
    public void whenGetSubjectById_thenExceptionNotFoundObject() throws Exception{

        mockMvc.perform(delete("http://localhost:8080/api/subjects/999")
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Subject with id= 999 not found"))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.request").value("uri=/api/subjects/999"));
    }

}
