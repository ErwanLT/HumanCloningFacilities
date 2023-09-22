package com.erwan.human.controller;

import com.erwan.human.config.JwtTokenProvider;
import com.erwan.human.domaine.authentification.AppUserRole;
import com.erwan.human.domaine.kamino.Clone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HumanCloningControllerTest extends AbstractControllerTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void createClone_OK() throws Exception {
        when(cloneRepository.save(any())).thenReturn(getClone(1L));
        mvc.perform(post("/kamino/clones")
                        .header(HttpHeaders.AUTHORIZATION, getToken("kaminoian", AppUserRole.ROLE_KAMINOAIN))
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
                        .header(HttpHeaders.AUTHORIZATION, getToken("kaminoian", AppUserRole.ROLE_KAMINOAIN)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void findCloneById_NotFound() throws Exception {
        when(cloneRepository.findById(any())).thenReturn(Optional.empty());
        mvc.perform(get("/kamino/clones/1")
                        .header(HttpHeaders.AUTHORIZATION, getToken("palpatine", AppUserRole.ROLE_EMPEROR)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Can't find clone with id : 1"));
    }

    @Test
    public void executeOrder66_NotAuthorized() throws Exception {
        mvc.perform(put("/kamino/clones/order66")
                        .header(HttpHeaders.AUTHORIZATION, getToken("kaminoian", AppUserRole.ROLE_KAMINOAIN)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void executeOrder66_OK() throws Exception {
        mvc.perform(put("/kamino/clones/order66")
                        .header(HttpHeaders.AUTHORIZATION, getToken("palpatine", AppUserRole.ROLE_EMPEROR)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteClone_OK() throws Exception {
        Clone found = getClone(1L);
        Optional<Clone> inDb = Optional.ofNullable(found);
        when(cloneRepository.findById(1L)).thenReturn(inDb);

        mvc.perform(delete("/kamino/clones/1")
                        .header(HttpHeaders.AUTHORIZATION, getToken("palpatine", AppUserRole.ROLE_EMPEROR)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String getToken(String username, AppUserRole role) {
        return "Bearer " + jwtTokenProvider.createToken(username, List.of(role));
    }

}
