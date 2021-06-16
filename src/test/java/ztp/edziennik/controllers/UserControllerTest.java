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
import ztp.edziennik.repositories.UserRepository;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends TestHelper {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        Mockito.when(userRepository.findByEmail(mockTeacher1.getEmail())).thenReturn(Optional.of(mockTeacher1));
    }

    @Test
    public void whenGetUserData_thenReturnUserData_withStatus200() throws Exception {
        mockMvc.perform(get(baseUrl + "/users")
                .header("Authorization", tokenTeacher1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(mockTeacher1.getUserId()))
                .andExpect(jsonPath("$.firstName").value(mockTeacher1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(mockTeacher1.getLastName()))
                .andExpect(jsonPath("$.email").value(mockTeacher1.getEmail()))
                .andExpect(jsonPath("$.role").value(mockTeacher1.getRole().toString()));
    }

}
