package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntranceDTO {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String rol;
}
