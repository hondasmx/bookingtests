package com.herokuapp.restfulbooker.autotests.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResponse {

    private Long bookingid;
    private Booking booking;
}
