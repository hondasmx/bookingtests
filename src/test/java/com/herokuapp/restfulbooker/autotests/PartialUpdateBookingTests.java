package com.herokuapp.restfulbooker.autotests;

import com.herokuapp.restfulbooker.autotests.model.Booking;
import com.herokuapp.restfulbooker.autotests.model.CreateBookingResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PartialUpdateBookingTests extends BaseBookerTest {

    @ParameterizedTest(name = "update booking by id {0} without token")
    @MethodSource("existedBookingIds")
    public void cantUpdateBookingWithoutToken(Long bookingId) {
        var booking = generateBooking();
        bookerClient.partialUpdateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    @Test
    public void partialUpdateBookingFirstname() {
        var createdBooking = bookerClient.createBooking(generateBooking());
        var bookingId = createdBooking.getBookingid();
        var firstname = RandomStringUtils.randomAlphabetic(10);
        var newBooking = Booking.builder().firstname(firstname).build();
        var response = bookerClient.partialUpdateBooking(bookingId, newBooking);

        assertThat(response.getFirstname()).isEqualTo(firstname);
        assertThat(response.getLastname()).isEqualTo(createdBooking.getBooking().getLastname());
        assertThat(response.getAdditionalneeds()).isEqualTo(createdBooking.getBooking().getAdditionalneeds());
        assertThat(response.getTotalprice()).isEqualTo(createdBooking.getBooking().getTotalprice());
        assertThat(response.getBookingdates()).isEqualTo(createdBooking.getBooking().getBookingdates());

        var updatedBooking = bookerClient.getBookingById(bookingId);
        assertThat(updatedBooking.getFirstname()).isEqualTo(firstname);
        assertThat(updatedBooking.getLastname()).isEqualTo(createdBooking.getBooking().getLastname());
        assertThat(updatedBooking.getAdditionalneeds()).isEqualTo(createdBooking.getBooking().getAdditionalneeds());
        assertThat(updatedBooking.getTotalprice()).isEqualTo(createdBooking.getBooking().getTotalprice());
        assertThat(updatedBooking.getBookingdates()).isEqualTo(createdBooking.getBooking().getBookingdates());

    }

    @Test
    public void partialUpdateBookingLastname() {
        var createdBooking = bookerClient.createBooking(generateBooking());
        var bookingId = createdBooking.getBookingid();
        var lastName = RandomStringUtils.randomAlphabetic(10);
        var newBooking = Booking.builder().lastname(lastName).build();

        var response = bookerClient.partialUpdateBooking(bookingId, newBooking);
        assertThat(response.getLastname()).isEqualTo(lastName);
        assertThat(response.getFirstname()).isEqualTo(createdBooking.getBooking().getFirstname());
        assertThat(response.getAdditionalneeds()).isEqualTo(createdBooking.getBooking().getAdditionalneeds());
        assertThat(response.getTotalprice()).isEqualTo(createdBooking.getBooking().getTotalprice());
        assertThat(response.getBookingdates()).isEqualTo(createdBooking.getBooking().getBookingdates());

        var updatedBooking = bookerClient.getBookingById(bookingId);
        assertThat(updatedBooking.getLastname()).isEqualTo(lastName);
        assertThat(updatedBooking.getFirstname()).isEqualTo(createdBooking.getBooking().getFirstname());
        assertThat(updatedBooking.getAdditionalneeds()).isEqualTo(createdBooking.getBooking().getAdditionalneeds());
        assertThat(updatedBooking.getTotalprice()).isEqualTo(createdBooking.getBooking().getTotalprice());
        assertThat(updatedBooking.getBookingdates()).isEqualTo(createdBooking.getBooking().getBookingdates());
    }

    @Test
    public void partialUpdateBookingAdditionalneeds() {
        var createdBooking = bookerClient.createBooking(generateBooking());
        var bookingId = createdBooking.getBookingid();
        var additionalNeeds = RandomStringUtils.randomAlphabetic(10);
        var newBooking = Booking.builder().additionalneeds(additionalNeeds).build();

        var response = bookerClient.partialUpdateBooking(bookingId, newBooking);
        assertThat(response.getAdditionalneeds()).isEqualTo(additionalNeeds);
        assertThat(response.getFirstname()).isEqualTo(createdBooking.getBooking().getFirstname());
        assertThat(response.getLastname()).isEqualTo(createdBooking.getBooking().getLastname());
        assertThat(response.getTotalprice()).isEqualTo(createdBooking.getBooking().getTotalprice());
        assertThat(response.getBookingdates()).isEqualTo(createdBooking.getBooking().getBookingdates());

        var updatedBooking = bookerClient.getBookingById(bookingId);
        assertThat(updatedBooking.getAdditionalneeds()).isEqualTo(additionalNeeds);
        assertThat(updatedBooking.getFirstname()).isEqualTo(createdBooking.getBooking().getFirstname());
        assertThat(updatedBooking.getLastname()).isEqualTo(createdBooking.getBooking().getLastname());
        assertThat(updatedBooking.getTotalprice()).isEqualTo(createdBooking.getBooking().getTotalprice());
        assertThat(updatedBooking.getBookingdates()).isEqualTo(createdBooking.getBooking().getBookingdates());
    }

    //тест падает, js number имеет max_value 2^53, а java long 2^64
    @Test
    public void partialUpdateBookingTotalPrice() {
        var createdBooking = bookerClient.createBooking(generateBooking());
        var bookingId = createdBooking.getBookingid();
        var totalPrice = RandomUtils.nextLong();
        var newBooking = Booking.builder().totalprice(totalPrice).build();

        var response = bookerClient.partialUpdateBooking(bookingId, newBooking);
        assertThat(response.getAdditionalneeds()).isEqualTo(createdBooking.getBooking().getAdditionalneeds());
        assertThat(response.getFirstname()).isEqualTo(createdBooking.getBooking().getFirstname());
        assertThat(response.getLastname()).isEqualTo(createdBooking.getBooking().getLastname());
        assertThat(response.getTotalprice()).isEqualTo(totalPrice);
        assertThat(response.getBookingdates()).isEqualTo(createdBooking.getBooking().getBookingdates());

        var updatedBooking = bookerClient.getBookingById(bookingId);
        assertThat(updatedBooking.getAdditionalneeds()).isEqualTo(createdBooking.getBooking().getAdditionalneeds());
        assertThat(updatedBooking.getFirstname()).isEqualTo(createdBooking.getBooking().getFirstname());
        assertThat(updatedBooking.getLastname()).isEqualTo(createdBooking.getBooking().getLastname());
        assertThat(updatedBooking.getTotalprice()).isEqualTo(totalPrice);
        assertThat(updatedBooking.getBookingdates()).isEqualTo(createdBooking.getBooking().getBookingdates());
    }

    @ParameterizedTest(name = "partial update checkin")
    @ValueSource(strings = {"1200-08-31", "2040-01-01", "999-01-01"})
    public void partialUpdateBookingCheckin(String checkin) {
        var createdBooking = bookerClient.createBooking(generateBooking());
        var bookingId = createdBooking.getBookingid();

        var newBooking = Booking
                .builder()
                .bookingdates(Booking.BookingDates
                        .builder()
                        .checkin(checkin)
                        .build())
                .build();

        var response = bookerClient.partialUpdateBooking(bookingId, newBooking);
        assertThat(response.getAdditionalneeds()).isEqualTo(createdBooking.getBooking().getAdditionalneeds());
        assertThat(response.getFirstname()).isEqualTo(createdBooking.getBooking().getFirstname());
        assertThat(response.getLastname()).isEqualTo(createdBooking.getBooking().getLastname());
        assertThat(response.getTotalprice()).isEqualTo(createdBooking.getBooking().getTotalprice());
        assertThat(response.getBookingdates().getCheckin()).isEqualTo(newBooking.getBookingdates().getCheckin());

        var updatedBooking = bookerClient.getBookingById(bookingId);
        assertThat(updatedBooking.getAdditionalneeds()).isEqualTo(createdBooking.getBooking().getAdditionalneeds());
        assertThat(updatedBooking.getFirstname()).isEqualTo(createdBooking.getBooking().getFirstname());
        assertThat(updatedBooking.getLastname()).isEqualTo(createdBooking.getBooking().getLastname());
        assertThat(updatedBooking.getTotalprice()).isEqualTo(createdBooking.getBooking().getTotalprice());
        assertThat(updatedBooking.getBookingdates().getCheckin()).isEqualTo(newBooking.getBookingdates().getCheckin());
    }

    private Stream<Long> existedBookingIds() {
        return createdBookings
                .stream()
                .map(CreateBookingResponse::getBookingid);
    }
}
