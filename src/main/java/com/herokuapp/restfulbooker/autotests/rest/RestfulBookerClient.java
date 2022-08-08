package com.herokuapp.restfulbooker.autotests.rest;

import com.herokuapp.restfulbooker.autotests.model.Booking;
import com.herokuapp.restfulbooker.autotests.model.CreateBookingResponse;
import com.herokuapp.restfulbooker.autotests.model.GetBookingIdsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RestfulBookerClient {

    private final RestAssuredHttpClient httpClient;

    public List<GetBookingIdsResponse> getBookingIds(String firstname, String lastname, String checkin, String checkout) {
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("firstname", firstname);
        requestParams.put("lastname", lastname);
        requestParams.put("checkin", checkin);
        requestParams.put("checkout", checkout);
        var endpoint = "/booking";
        return List.of(httpClient.doGetRequest(endpoint, GetBookingIdsResponse[].class, requestParams));
    }

    public List<GetBookingIdsResponse> getAllBookingIds() {
        return getBookingIds(null, null, null, null);
    }

    public String deleteBookingById(Long id) {
        var endpoint = "/booking/" + id;
        var token = httpClient.getToken();
        return httpClient.doErrorRequest(endpoint, null, Map.of(), "DELETE", token, 201);
    }

    public String deleteBookingByIdWithoutTokenAndValidateError(Long id) {
        var endpoint = "/booking/" + id;
        return httpClient.doErrorRequest(endpoint, null, Map.of(), "DELETE", null, 403);
    }

    public String deleteBookingByIdAndValidateError(Long id) {
        var endpoint = "/booking/" + id;
        return httpClient.doErrorRequest(endpoint, null, Map.of(), "DELETE", null, 403);
    }

    public Booking getBookingById(Long id) {
        var endpoint = "/booking/" + id;
        return httpClient.doGetRequest(endpoint, Booking.class, Map.of());
    }

    public String getBookingByIdAndValidateError(Long id) {
        var endpoint = "/booking/" + id;
        return httpClient.doErrorRequest(endpoint, null, Map.of(), "GET", null, 404);
    }

    public Booking partialUpdateBooking(Long id, Booking booking) {
        var endpoint = "/booking/" + id;
        return httpClient.doPatchRequest(endpoint, booking, Booking.class, Map.of());
    }

    public Booking updateBooking(Long id, Booking booking) {
        var endpoint = "/booking/" + id;
        return httpClient.doPutRequest(endpoint, booking, Booking.class, Map.of());
    }

    public String updateBookingWithoutTokenAndValidateError(Long id, Booking booking) {
        var endpoint = "/booking/" + id;
        return httpClient.doErrorRequest(endpoint, booking, Map.of(), "PUT", null, 403);
    }

    public String partialUpdateBookingWithoutTokenAndValidateError(Long id, Booking booking) {
        var endpoint = "/booking/" + id;
        return httpClient.doErrorRequest(endpoint, booking, Map.of(), "PATCH", null, 403);
    }

    public CreateBookingResponse createBooking(Booking booking) {
        var endpoint = "/booking";
        return httpClient.doPostRequest(endpoint, booking, CreateBookingResponse.class, Map.of());
    }

    //на сервере нет проверки на входные параметры, поэтому вместо 4хх, отдаются 500 ошибки
    //так же, ответ должен быть json, а не string
    //негативные тесты все упадут
    public String createBookingAndValidateError(Booking booking) {
        var endpoint = "/booking";
        return httpClient.doErrorRequest(endpoint, booking, Map.of(), "POST", null, 400);
    }
}
