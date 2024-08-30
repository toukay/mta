package pja.mas.youssef.travelagency.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestSessionData implements Serializable {
    public static final Long DEMO_CUSTOMER_ID = 3L;

    private Long customerId;
    private Long tourId;
    private String tourDestination;
    private Integer numberOfPeople;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private Boolean isTravelInsuranceIncluded;
    private Boolean isPrivateBusIncluded;
    private Boolean isMealsIncluded;
    private Double totalPrice;
}
