package com.nathanieldoe.santa;

import com.nathanieldoe.santa.api.PersonApiImpl;
import com.nathanieldoe.santa.db.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PersonApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonApiImpl personApi;

    @MockBean
    private PersonRepository personRepository;

    @Test
    public void unauthenticatedRequest() throws Exception {
        this.mockMvc.perform(get("/person/list"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

}
