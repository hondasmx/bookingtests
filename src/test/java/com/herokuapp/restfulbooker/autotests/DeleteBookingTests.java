package com.herokuapp.restfulbooker.autotests;

import org.junit.jupiter.api.Test;

public class DeleteBookingTests extends BaseBookerTest {

    @Test
    public void deleteBookingTest() {
        var booking = bookerClient.createBooking(generateBooking());
        bookerClient.deleteBookingById(booking.getBookingid());
        bookerClient.getBookingByIdAndValidateError(booking.getBookingid());
    }

    @Test
    public void deleteUnknownBookingTest() {
        var booking = bookerClient.createBooking(generateBooking());
        bookerClient.deleteBookingByIdAndValidateError(booking.getBookingid() + 999);
    }

    @Test
    public void deleteWithoutTokenTest() {
        var booking = bookerClient.createBooking(generateBooking());
        bookerClient.deleteBookingByIdWithoutTokenAndValidateError(booking.getBookingid());
    }
}
