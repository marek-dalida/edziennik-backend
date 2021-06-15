package ztp.edziennik.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ztp.edziennik.constants.TestHelper;
import ztp.edziennik.models.User;
import ztp.edziennik.repositories.UserRepository;


import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Test demands created user "janina.janik@gmail.com", "ztp2021" on database

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest extends TestHelper {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp(){
        User user = mockTeacher1;
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    }

    @Test
    public void givenAuthRequest_shouldSucceedWith200() throws Exception {
        String username = "jan.kowal@gmail.com";
        String password = "ztp2021";

        String authUrlString = "http://localhost:8080/api/auth?username=" + username + "&password=" + password;

        MvcResult result = mockMvc.perform(get(authUrlString)).andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString();

        mockMvc.perform(get("http://localhost:8080/api/students")
                .header("Authorization", response)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenBadAuthRequest_shouldReturnUnauthorized() throws Exception {
        String username = "jan.kowal@gmail.com";
        String password = "bledne";

        String authUrlString = "http://localhost:8080/api/auth?username=" + username + "&password=" + password;

        mockMvc.perform(get(authUrlString)).andExpect(status().isUnauthorized());
    }

    @Test
    public void requestWithoutAuth_shouldReturnForbidden() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/groups"))
                .andExpect(status().isForbidden());
    }

}
