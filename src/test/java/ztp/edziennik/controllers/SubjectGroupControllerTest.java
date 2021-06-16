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
public class SubjectGroupControllerTest extends TestHelper {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectGroupRepository subjectGroupRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp(){
        Mockito.when(userRepository.findByEmail(mockTeacher1.getEmail())).thenReturn(Optional.of(mockTeacher1));
        Mockito.when(subjectGroupRepository.findById(69L)).thenReturn(Optional.empty());
        Mockito.when(subjectGroupRepository.findById(mockGroup1.getId())).thenReturn(Optional.of(mockGroup1));
    }

    @Test
    public void whenDeleteSubjectGroup_ThrowNotFoundException() throws Exception{
        mockMvc.perform(delete(baseUrl +"/groups/69")
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("SubjectGroup with id= 69 not found"))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.request").value("uri=/api/groups/69"));
    }

    @Test
    public void whenGetGroupById_thenGetGroupWith200Status() throws Exception {
        mockMvc.perform(get(baseUrl + "/groups/"+ mockGroup1.getId())
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockGroup1.getId()))
                .andExpect(jsonPath("$.subjectId").value(mockGroup1.getSubjectId()))
                .andExpect(jsonPath("$.groupName").value(mockGroup1.getGroupName()))
                .andExpect(jsonPath("$.groupTeacherId").value(mockGroup1.getGroupTeacherId()));
    }


}
