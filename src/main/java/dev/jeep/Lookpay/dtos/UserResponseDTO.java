package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String rol;
}