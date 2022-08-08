package com.herokuapp.restfulbooker.autotests.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class GetBookingIdsRequest {

    private String firstname;
    private String lastname;
    private OffsetDateTime checkin;
    private OffsetDateTime checkout;
}
