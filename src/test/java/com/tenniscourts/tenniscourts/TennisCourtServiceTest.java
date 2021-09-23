package com.tenniscourts.tenniscourts;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {TennisCourtService.class, TennisCourtRepository.class})
public class TennisCourtServiceTest {

    private TennisCourtService tennisCourtService;

    @Mock
    private ScheduleService scheduleService;
    @Mock
    private TennisCourtRepository tennisCourtRepository;
    @Mock
    private TennisCourtMapper tennisCourtMapper;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ScheduleMapper scheduleMapper;

    @Before
    public void setUp() {
        var foundTennisCourt = Optional.of(new TennisCourt("name"));

        var tennisCourtDTOSample = new TennisCourtDTO();
        Mockito.when(tennisCourtRepository.findById(0L)).thenReturn(foundTennisCourt);
        Mockito.when(tennisCourtMapper.map(foundTennisCourt.get())).thenReturn(tennisCourtDTOSample);

        var schedule = new Schedule();
        var scheduleDTOSample = new ScheduleDTO();
        Mockito.when(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(0L)).thenReturn(List.of(schedule));
        Mockito.when(scheduleMapper.map(List.of(schedule))).thenReturn(List.of(scheduleDTOSample));

        tennisCourtService = new TennisCourtService(tennisCourtRepository,scheduleService,tennisCourtMapper);
    }


    @Test
    public void ShouldReturnATennisCourtWhenSearchingWithAnExistentId() {
        var foundGuest = tennisCourtService.findTennisCourtById(0L);
        Assert.assertNotNull(foundGuest);
        Assert.assertEquals(TennisCourtDTO.class,foundGuest.getClass());
    }
    @Test
    public void ShouldReturnATennisCourtWithScheduleWhenSearchingWithAnExistentId() {
        var foundGuest = tennisCourtService.findTennisCourtWithSchedulesById(0L);
        Assert.assertNotNull(foundGuest);
        Assert.assertEquals(TennisCourtDTO.class,foundGuest.getClass());
    }

    @Test(expected = EntityNotFoundException.class)
    public void GivenANotFoundId_WhenSearchingATennisCourt_ShouldThrowEntityNotFoundException() {
        tennisCourtService.findTennisCourtById(1L);
    }
    @Test(expected = EntityNotFoundException.class)
    public void GivenANotFoundId_WhenSearchingATennisCourtWithSchedule_ShouldThrowEntityNotFoundException() {
        tennisCourtService.findTennisCourtWithSchedulesById(1L);
    }
}