package api.pojo.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDates {
    @JsonProperty(value = "checkin")
    private String checkIn;

    @JsonProperty(value = "checkout")
    private String checkOut;
}
