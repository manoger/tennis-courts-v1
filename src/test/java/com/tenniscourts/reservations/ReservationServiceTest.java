package com.tenniscourts.reservations;

import com.tenniscourts.schedules.Schedule;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusDays(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertThat(
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build())
                , Matchers.comparesEqualTo(BigDecimal.valueOf(10)));
    }

    @Test
    public void shouldReturn75PercentRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(12);

        schedule.setStartDateTime(startDateTime);

        Assert.assertThat(
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build())
                , Matchers.comparesEqualTo(BigDecimal.valueOf(7.5)));
    }
    @Test
    public void shouldReturn50PercentRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusHours(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertThat(
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build())
                , Matchers.comparesEqualTo(BigDecimal.valueOf(5)));
    }
    @Test
    public void shouldReturn25PercentRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now().plusMinutes(2);

        schedule.setStartDateTime(startDateTime);

        Assert.assertThat(
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build())
                , Matchers.comparesEqualTo(BigDecimal.valueOf(2.5)));
    }
    @Test
    public void shouldReturnNoRefund() {
        Schedule schedule = new Schedule();

        LocalDateTime startDateTime = LocalDateTime.now();

        schedule.setStartDateTime(startDateTime);

        Assert.assertThat(
                reservationService.getRefundValue(Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build())
                , Matchers.comparesEqualTo(BigDecimal.valueOf(0)));
    }
}