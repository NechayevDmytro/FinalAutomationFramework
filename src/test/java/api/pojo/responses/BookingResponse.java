package api.pojo.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponse {
    private String bookingid;
    private Booking booking;
}
