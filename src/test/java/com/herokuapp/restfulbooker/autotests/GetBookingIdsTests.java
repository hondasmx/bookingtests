package com.herokuapp.restfulbooker.autotests;

import com.herokuapp.restfulbooker.autotests.model.CreateBookingResponse;
import com.herokuapp.restfulbooker.autotests.model.GetBookingIdsResponse;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class GetBookingIdsTests extends BaseBookerTest {

    @Test
    public void getAllBookings() {
        var allBookingIds = bookerClient
                .getAllBookingIds()
                .stream()
                .map(GetBookingIdsResponse::getBookingid)
                .collect(Collectors.toSet());
        var createdBookingIds = createdBookings
                .stream()
                .map(CreateBookingResponse::getBookingid)
                .collect(Collectors.toSet());
        assertThat(allBookingIds).containsAll(createdBookingIds);
    }
}
