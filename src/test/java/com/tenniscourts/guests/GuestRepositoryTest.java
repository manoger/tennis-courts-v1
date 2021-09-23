package com.tenniscourts.guests;

import com.tenniscourts.TennisCourtApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TennisCourtApplication.class)
public class GuestRepositoryTest {
    @Autowired
    GuestRepository guestRepository;

    @Test
    public void shouldPersistAndFindByName() throws Exception {
        String guestName = "TestGuestA";
        guestRepository.save(new Guest(guestName));
        var foundGuest = guestRepository.findByNameContainingIgnoreCase(guestName);
        foundGuest.forEach(guest->{
            Assert.assertEquals(guest.getName(),guestName);
        });
    }

    @Test
    public void shouldPersistAndFindById() throws Exception {
        String guestName = "testGuestB";
        var pesistedGuest = guestRepository.save(new Guest(guestName));
        var guestFound = guestRepository.findById(pesistedGuest.getId());
        Assert.assertTrue(guestFound.isPresent());
        Assert.assertNotNull(guestFound.get());
        Assert.assertEquals(guestFound.get().getName(),guestName);
    }
    @Test
    public void shouldTheDatabaseBeInitializedWithSomeGuests() throws Exception {
        var foundGuests = guestRepository.findAll();
        Assert.assertTrue(foundGuests.size()>0);
    }
}
