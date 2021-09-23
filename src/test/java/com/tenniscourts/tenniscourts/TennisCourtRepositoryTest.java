package com.tenniscourts.tenniscourts;

import com.tenniscourts.TennisCourtApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TennisCourtApplication.class)
public class TennisCourtRepositoryTest {
    @Autowired
    TennisCourtRepository tennisCourtRepository;


    @Test
    public void shouldPersistAndFindById() throws Exception {
        String tennisCourtName = "Test court";
        var persistedTennisCourt = tennisCourtRepository.save(new TennisCourt(tennisCourtName));
        var foundTennisCourt = tennisCourtRepository.findById(persistedTennisCourt.getId());
        Assert.assertTrue(foundTennisCourt.isPresent());
        Assert.assertNotNull(foundTennisCourt.get());
        Assert.assertEquals(foundTennisCourt.get().getName(),tennisCourtName);
    }

}
