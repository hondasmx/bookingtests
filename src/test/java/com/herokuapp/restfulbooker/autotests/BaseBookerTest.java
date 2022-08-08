package com.herokuapp.restfulbooker.autotests;

import com.herokuapp.restfulbooker.autotests.model.Booking;
import com.herokuapp.restfulbooker.autotests.model.CreateBookingResponse;
import com.herokuapp.restfulbooker.autotests.rest.RestfulBookerClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseBookerTest {

    protected List<CreateBookingResponse> createdBookings = new ArrayList<>();

    @Autowired
    protected RestfulBookerClient bookerClient;

    @BeforeAll
    public void populateData() {
        createdBookings.add(bookerClient.createBooking(generateBooking()));
        createdBookings.add(bookerClient.createBooking(generateBooking()));
    }

    protected Booking generateBooking(OffsetDateTime checkingDate, OffsetDateTime checkoutDate) {
        var dates = Booking.BookingDates
                .builder()
                .checkin("2021-12-12")
                .checkout("2022-12-12")
                .build();
        return Booking
                .builder()
                .firstname(RandomStringUtils.randomAlphabetic(5))
                .lastname(RandomStringUtils.randomAlphabetic(5))
                .additionalneeds(RandomStringUtils.randomAlphabetic(5))
                .depositpaid(true)
                .totalprice(RandomUtils.nextLong())
                .bookingdates(dates)
                .build();
    }

    protected Booking generateBooking() {
        var checkingDate = OffsetDateTime.now().minus(1, ChronoUnit.YEARS);
        var checkoutDate = OffsetDateTime.now();
        return generateBooking(checkingDate, checkoutDate);
    }
}
