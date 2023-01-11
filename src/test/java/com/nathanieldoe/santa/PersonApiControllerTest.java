package com.nathanieldoe.santa;

import com.nathanieldoe.santa.api.PersonApiImpl;
import com.nathanieldoe.santa.controller.PersonApiController;
import com.nathanieldoe.santa.db.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PersonApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonApiImpl personApi;

    @MockBean
    private PersonRepository personRepository;


    @Test
    void unauthenticatedRequest() throws Exception {
        this.mockMvc.perform(get(PersonApiController.BASE_PATH + "/list"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void authenticatedRequest() throws Exception {
        this.mockMvc.perform(get(PersonApiController.BASE_PATH + "/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
