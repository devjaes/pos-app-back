package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BoxResponseDTO {
    private Long id;
    private String key;
    private String sequential;
    private String branchName;

}
