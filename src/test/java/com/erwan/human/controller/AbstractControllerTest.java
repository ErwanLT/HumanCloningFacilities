package com.erwan.human.controller;

import com.erwan.human.dao.CloneRepository;
import com.erwan.human.domaine.Clone;
import com.erwan.human.reference.CloneType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AbstractControllerTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    protected CloneRepository cloneRepository;

    @MockBean
    protected JediControllerApiImpl jediControllerApi;

    protected static Faker faker;

    @BeforeAll
    public static void setUp(){

        faker = new Faker(Locale.FRENCH);

    }


    public Clone getClone(Long id) {
        return Clone.builder()
                .id(faker.random().nextLong())
                .codeName(faker.elderScrolls().lastName())
                .type(CloneType.flametrooper)
                .affiliation(faker.company().name())
                .build();
    }
}
