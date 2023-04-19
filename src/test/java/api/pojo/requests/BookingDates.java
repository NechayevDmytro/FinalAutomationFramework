package api.pojo.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookingDates {
    private String checkin;
    private String checkout;
}
