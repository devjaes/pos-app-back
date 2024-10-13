package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BranchResponseDTO {
    private Long id;
    private String key;
    private String name;
    private String address;
}
