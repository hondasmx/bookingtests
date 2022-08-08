package com.herokuapp.restfulbooker.autotests;

import com.herokuapp.restfulbooker.autotests.model.Booking;
import com.herokuapp.restfulbooker.autotests.model.CreateBookingResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateBookingTests extends BaseBookerTest {

    @ParameterizedTest(name = "update booking by id {0} without token")
    @MethodSource("existedBookingIds")
    public void cantUpdateBookingWithoutToken(Long bookingId) {
        var booking = generateBooking();
        bookerClient.updateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    @ParameterizedTest(name = "update booking by id {0} without firstname")
    @MethodSource("existedBookingIds")
    public void updateBookingWithoutFirstname(Long bookingId) {
        var booking = generateBooking();
        booking.setFirstname(null);
        bookerClient.updateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    @ParameterizedTest(name = "update booking by id {0} without lastname")
    @MethodSource("existedBookingIds")
    public void updateBookingWithoutLastname(Long bookingId) {
        var booking = generateBooking();
        booking.setLastname(null);
        bookerClient.updateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    @ParameterizedTest(name = "update booking by id {0} without additionalneeds")
    @MethodSource("existedBookingIds")
    public void updateBookingWithoutAdditionalNeeds(Long bookingId) {
        var booking = generateBooking();
        booking.setAdditionalneeds(null);
        bookerClient.updateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    @ParameterizedTest(name = "update booking by id {0} without totalprice")
    @MethodSource("existedBookingIds")
    public void updateBookingWithoutTotalPrice(Long bookingId) {
        var booking = generateBooking();
        booking.setTotalprice(null);
        bookerClient.updateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    @ParameterizedTest(name = "update booking by id {0} without depositpaid")
    @MethodSource("existedBookingIds")
    public void updateBookingWithoutepositPaid(Long bookingId) {
        var booking = generateBooking();
        booking.setDepositpaid(null);
        bookerClient.updateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    @ParameterizedTest(name = "update booking by id {0} without checkin")
    @MethodSource("existedBookingIds")
    public void updateBookingWithoutCheckin(Long bookingId) {
        var booking = generateBooking();
        booking.setBookingdates(Booking.BookingDates.builder().checkout("2022-01-01").build());
        bookerClient.updateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    @ParameterizedTest(name = "update booking by id {0} without checkout")
    @MethodSource("existedBookingIds")
    public void updateBookingWithoutCheckout(Long bookingId) {
        var booking = generateBooking();
        booking.setBookingdates(Booking.BookingDates.builder().checkin("2022-01-01").build());
        bookerClient.updateBookingWithoutTokenAndValidateError(bookingId, booking);
    }

    //падаает из-за totalprice
    @Test
    public void updateBooking() {
        var createdBooking = bookerClient.createBooking(generateBooking());
        var bookingId = createdBooking.getBookingid();

        var newBooking = generateBooking();

        var response = bookerClient.updateBooking(bookingId, newBooking);
        assertThat(response).usingRecursiveComparison().isEqualTo(newBooking);

        var updatedBooking = bookerClient.getBookingById(bookingId);
        assertThat(updatedBooking).usingRecursiveComparison().isEqualTo(newBooking);
    }

    private Stream<Long> existedBookingIds() {
        return createdBookings
                .stream()
                .map(CreateBookingResponse::getBookingid);
    }
}
