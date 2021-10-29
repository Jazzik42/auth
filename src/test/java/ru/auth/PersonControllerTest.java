package ru.auth;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.auth.model.Person;
import ru.auth.repository.PersonRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PersonRepository store;

    @Autowired
    public PersonControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void whenFindAll() throws Exception {
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenFindById() throws Exception {
        mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"id\":0,\"login\":null,\"password\":null}"));
    }

    @Test
    void whenCreate() throws Exception {
        mockMvc.perform(post("/person/")
                        .content("{\"login\":\"user\",\"password\":\"root\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(store).save(argument.capture());
        assertThat(argument.getValue().getLogin(), is("user"));
        assertThat(argument.getValue().getPassword(), is("root"));
    }

    @Test
    void whenUpdate() throws Exception {
        mockMvc.perform(put("/person/")
                        .content("{\"id\":1,\"login\":\"user\",\"password\":\"root\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(store).save(argument.capture());
        assertThat(argument.getValue().getLogin(), is("user"));
        assertThat(argument.getValue().getPassword(), is("root"));
    }

    @Test
    void whenDelete() throws Exception {
        mockMvc.perform(delete("/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

