package api.pojo.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingID {
    @JsonProperty(value = "bookingid")
    private Integer bookingID;
}
