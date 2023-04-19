package api;

import api.pojo.requests.BookingDates;
import api.pojo.requests.CreateBookingRequest;
import api.pojo.requests.Token;

public class RequestsData {

    public static CreateBookingRequest getCreatingBookingRequest() {
        return CreateBookingRequest.builder()
                .firstname("Bill")
                .lastname("Gates")
                .totalprice(1000)
                .depositpaid(true)
                .bookingdates(
                        BookingDates.builder()
                                .checkin("2024-11-01")
                                .checkout("2024-02-01")
                                .build())
                .additionalneeds("All inclusive")
                .build();
    }

    public static CreateBookingRequest getFullyUpdatingBookingRequest() {
        return CreateBookingRequest.builder()
                .firstname("Steve")
                .lastname("Jobs")
                .totalprice(500)
                .depositpaid(true)
                .bookingdates(
                        BookingDates.builder()
                                .checkin("2025-11-01")
                                .checkout("2025-02-01")
                                .build())
                .additionalneeds("Cup of tea")
                .build();
    }

    public static Token getTokenRequest() {
        return Token.builder()
                .username("admin")
                .password("password123")
                .build();
    }
}
