package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerEntranceDTO {
    private String name;
    private String lastName;
    private String email;
    private String businessName;
    private String identification;
    private String address;
    private String identificationType;
}
