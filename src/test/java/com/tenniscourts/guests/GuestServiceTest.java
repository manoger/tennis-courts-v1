package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {GuestService.class,GuestRepository.class})
public class GuestServiceTest {

    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;
    @Mock
    private GuestMapper guestMapper;

    @Before
    public void setUp() {
        var foundGuest = Optional.of(new Guest("name"));
        var guestDTOSample = new GuestDTO(0L,"name");
        Mockito.when(guestRepository.findById(0L)).thenReturn(foundGuest);
        Mockito.when(guestMapper.map(foundGuest.get())).thenReturn(guestDTOSample);
        var foundGuests =List.of(foundGuest.get());
        Mockito.when(guestRepository.findAll()).thenReturn(foundGuests);
        Mockito.when(guestMapper.map(foundGuests)).thenReturn(List.of(guestDTOSample));
        guestService = new GuestService(guestRepository,guestMapper);
    }

    @Test
    public void ShouldReturnArrayWhenListingAllGuests() {
        var foundGuests = guestService.listGuests();
        Assert.assertNotNull(foundGuests);
        Assert.assertTrue(foundGuests.size()>0);
    }

    @Test
    public void ShouldReturnAGuestWhenListingWithAnExistentId() {
        var foundGuest = guestService.findGuestById(0L);
        Assert.assertNotNull(foundGuest);
        Assert.assertEquals(GuestDTO.class,foundGuest.getClass());
    }

    @Test(expected = EntityNotFoundException.class)
    public void ShouldThrowEntityNotFoundExceptionWhenListingWithANotFoundId() {
        guestService.findGuestById(1L);
    }
}