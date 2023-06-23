package com.erwan.human.controller;

import com.erwan.human.domaine.kamino.Clone;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HumanCloningControllerTest extends AbstractControllerTest {

    @Test
    public void createClone_OK() throws Exception {
        when(cloneRepository.save(any())).thenReturn(getClone(1L));
        mvc.perform(post("/kamino/clones")
                        .with(httpBasic("kamino", "kamino"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(getClone(1L))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findCloneById_OK() throws Exception {
        Clone found = getClone(1L);
        Optional<Clone> inDb = Optional.ofNullable(found);
        when(cloneRepository.findById(1L)).thenReturn(inDb);

        mvc.perform(get("/kamino/clones/1")
                        .with(httpBasic("kamino", "kamino")))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void findCloneById_NotFound() throws Exception {
        when(cloneRepository.findById(any())).thenReturn(Optional.empty());
        mvc.perform(get("/kamino/clones/1")
                        .with(httpBasic("kamino", "kamino")))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Can't find clone with id : 1"));
    }

    @Test
    public void executeOrder66_NotAuthorized() throws Exception {
        mvc.perform(put("/kamino/clones/order66")
                        .with(httpBasic("kamino", "kamino")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void executeOrder66_OK() throws Exception {
        mvc.perform(put("/kamino/clones/order66")
                        .with(httpBasic("palpatine", "palpatine")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteClone_OK() throws Exception {
        Clone found = getClone(1L);
        Optional<Clone> inDb = Optional.ofNullable(found);
        when(cloneRepository.findById(1L)).thenReturn(inDb);

        mvc.perform(delete("/kamino/clones/1")
                        .with(httpBasic("palpatine", "palpatine")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllJedi_OK() throws Exception {
        when(jediControllerApi.findAllUsingGET()).thenReturn(new ArrayList());

        mvc.perform(get("/kamino/clones/jedi")
                        .with(httpBasic("palpatine", "palpatine")))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
