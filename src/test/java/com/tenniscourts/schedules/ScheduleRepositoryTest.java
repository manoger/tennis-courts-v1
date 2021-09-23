package com.tenniscourts.schedules;

import com.tenniscourts.TennisCourtApplication;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.reservations.ReservationService;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TennisCourtApplication.class)
public class ScheduleRepositoryTest {
    @Autowired
    ScheduleRepository scheduleRepository;


    ScheduleMapper scheduleMapper= new ScheduleMapperImpl();

    @Test
    public void shouldFindSchedulesByTennisId() {
        var scheduleDTO = ScheduleDTO.builder().tennisCourtId(1L)
                .tennisCourt(TennisCourtDTO.builder().id(1L).name("Roland Garros - Court Philippe-Chatrier").build())
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .build();
        var schedule = scheduleMapper.map(scheduleDTO);
        var savedSchedule= scheduleRepository.save(schedule);
        var foundSchedules = scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(1L);
        Assert.assertNotNull(foundSchedules);
        foundSchedules.forEach(foundSchedule -> {
            Assert.assertEquals(savedSchedule.getTennisCourt().getId(),foundSchedule.getTennisCourt().getId());
            Assert.assertEquals(savedSchedule.getTennisCourt().getName(),foundSchedule.getTennisCourt().getName());
        });
    }
    @Test
    public void shouldFindSchedulesInsideADateRange() {
        var scheduleDTO = ScheduleDTO.builder().tennisCourtId(1L)
                .tennisCourt(TennisCourtDTO.builder().id(1L).name("Roland Garros - Court Philippe-Chatrier").build())
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusHours(1))
                .build();
        var schedule = scheduleMapper.map(scheduleDTO);
        var savedSchedule= scheduleRepository.save(schedule);
        var startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        var endDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));
        var foundSchedules = scheduleRepository.findByStartDateTimeGreaterThanEqualAndEndDateTimeLessThanEqual(startDateTime,endDateTime);
        foundSchedules.forEach(foundSchedule -> {
            if(foundSchedule.getId().equals(savedSchedule.getId())){
                Assert.assertEquals(foundSchedule, savedSchedule);
            }
            Assert.assertTrue(foundSchedule.getStartDateTime().isAfter(startDateTime));
            Assert.assertTrue(foundSchedule.getStartDateTime().isBefore(endDateTime));
        });

    }
}
