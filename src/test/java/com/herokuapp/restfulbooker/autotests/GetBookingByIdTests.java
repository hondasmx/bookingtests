package com.herokuapp.restfulbooker.autotests;

import com.herokuapp.restfulbooker.autotests.model.CreateBookingResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class GetBookingByIdTests extends BaseBookerTest {

    @ParameterizedTest(name = "find booking by id {1}")
    @MethodSource("existedBookingIds")
    public void findByExistingIdTest(CreateBookingResponse createdBooking, Long bookingId) {
        var actualBooking = bookerClient.getBookingById(bookingId);
        var expectedBooking = createdBooking.getBooking();
        assertThat(actualBooking).usingRecursiveComparison().isEqualTo(expectedBooking);
    }

    @ParameterizedTest(name = "find booking by non existing id {0}")
    @MethodSource("nonExistedBookingIds")
    public void findByNonExistingIdTest(Long bookingId) {
        bookerClient.getBookingByIdAndValidateError(bookingId);
    }

    private Stream<Arguments> existedBookingIds() {
        return createdBookings
                .stream()
                .map(el -> Arguments.of(el, el.getBookingid()));
    }

    private Stream<Long> nonExistedBookingIds() {
        return createdBookings
                .stream()
                .map(el -> el.getBookingid() + 1000L);
    }
}
