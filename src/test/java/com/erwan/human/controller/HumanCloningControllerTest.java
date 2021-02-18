package com.erwan.human.controller;

import com.erwan.human.HumanApiApplication;
import com.erwan.human.dao.CloneRepository;
import com.erwan.human.domaine.Clone;
import com.erwan.human.exceptions.BeanNotFound;
import com.erwan.human.reference.CloneType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HumanApiApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HumanCloningControllerTest {

    @Autowired
    private HumanCloningController controller;

    @MockBean
    private CloneRepository repository;

    @Test
    public void shouldCreateBean_OK() throws Exception {
        // Given
        Clone input = getClone(null);
        Clone saved = getClone(1L);
        Mockito.when(repository.save(input)).thenReturn(saved);

        // When
        Clone output = controller.createClone(input);

        // Then
        Assert.assertNotNull(output);
        Mockito.verify(repository).save(input);
        Assert.assertEquals(saved, output);
    }

    @Test
    public void shouldFindOneBean_OK () throws Exception {
        // Given
        Long input = 1L;
        Clone found = getClone(input);
        Optional<Clone> inDb = Optional.ofNullable(found);
        Mockito.when(repository.findById(input)).thenReturn(inDb);

        // When
        Clone output = controller.findById(input);

        // Then
        Assert.assertNotNull(output);
        Mockito.verify(repository).findById(input);
        Assert.assertEquals(found, output);
    }

    @Test(expected= BeanNotFound.class)
    public void shouldFindOneBean_KO_NotFound () throws Exception {
        // Given
        Long input = 1L;
        Optional<Clone> inDb = Optional.ofNullable(null);
        Mockito.when(repository.findById(input)).thenReturn(inDb);

        // When
        controller.findById(input);

        // Then
    }

    public static Clone getClone(Long id) {
        Clone clone = new Clone();

        clone.setId(id);
        clone.setPlatoon(501);
        clone.setType(CloneType.gunner);
        clone.setCodeName("CT-7567");

        return clone;
    }
}
