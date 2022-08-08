package com.herokuapp.restfulbooker.autotests;

import com.herokuapp.restfulbooker.autotests.model.Booking;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CreateBookingTests extends BaseBookerTest {

    @Test
    public void createBookingWithEmptyFirstname() {
        var booking = generateBooking();
        booking.setFirstname("");
        validateCreatedBooking(booking);
    }

    @Test
    public void createBookingWithNullFirstname() {
        var booking = generateBooking();
        booking.setFirstname(null);
        bookerClient.createBookingAndValidateError(booking);
    }

    @Test
    public void createBookingWithEmptyLastname() {
        var booking = generateBooking();
        booking.setLastname("");
        validateCreatedBooking(booking);
    }

    @Test
    public void createBookingWithNullLastname() {
        var booking = generateBooking();
        booking.setLastname(null);
        bookerClient.createBookingAndValidateError(booking);
    }

    @Test
    public void createBookingWithEmptyTotalPrice() {
        var booking = generateBooking();
        booking.setTotalprice(0L);
        validateCreatedBooking(booking);
    }

    @Test
    public void createBookingWithNullTotalPrice() {
        var booking = generateBooking();
        booking.setTotalprice(null);
        bookerClient.createBookingAndValidateError(booking);
    }

    @Test
    public void createBookingWithEmptyAdditionalNeeds() {
        var booking = generateBooking();
        booking.setAdditionalneeds("");
        validateCreatedBooking(booking);
    }

    @Test
    public void createBookingWithNullAdditionalNeeds() {
        var booking = generateBooking();
        booking.setAdditionalneeds(null);
        bookerClient.createBookingAndValidateError(booking);
    }

    public void createBookingWithFalseDepositPaid() {
        var booking = generateBooking();
        booking.setDepositpaid(false);
        validateCreatedBooking(booking);
    }

    @Test
    public void createBookingWithNullDepositPaid() {
        var booking = generateBooking();
        booking.setDepositpaid(null);
        bookerClient.createBookingAndValidateError(booking);
    }

    @Test
    public void createBookingWithNullCheckin() {
        var booking = generateBooking();
        booking.setBookingdates(Booking.BookingDates.builder().checkout("2022-12-12").build());
        bookerClient.createBookingAndValidateError(booking);
    }

    @Test
    public void createBookingWithNullCheckout() {
        var booking = generateBooking();
        booking.setBookingdates(Booking.BookingDates.builder().checkin("2022-12-12").build());
        bookerClient.createBookingAndValidateError(booking);
    }

    @Test
    public void createBookingWithBadDateCheckout() {
        var booking = generateBooking();
        booking.setBookingdates(Booking.BookingDates
                .builder()
                .checkin("2022-12-12")
                .checkout("2022-2022")
                .build());
        bookerClient.createBookingAndValidateError(booking);
    }

    @Test
    public void createBookingWithBadDateCheckin() {
        var booking = generateBooking();
        booking.setBookingdates(Booking.BookingDates
                .builder()
                .checkout("2022-12-12")
                .checkin("2022-2022")
                .build());
        bookerClient.createBookingAndValidateError(booking);
    }

    //тест упадет. на сервере нет проверки на порядок дат
    @Test
    public void createBookingWithCheckoutBeforeCheckin() {
        var booking = generateBooking();
        booking.setBookingdates(Booking.BookingDates
                .builder()
                .checkout("2021-01-01")
                .checkin("2022-01-01")
                .build());
        bookerClient.createBookingAndValidateError(booking);
    }

    @Test
    public void createBookingWithNullBookingDates() {
        var booking = generateBooking();
        booking.setBookingdates(null);
        bookerClient.createBookingAndValidateError(booking);
    }

    private void validateCreatedBooking(Booking booking) {
        var response = bookerClient.createBooking(booking);
        log.info("проверка booking с id {}", response.getBookingid());
        assertThat(response.getBooking()).usingRecursiveComparison().isEqualTo(booking);
    }

}
