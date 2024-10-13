package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SellingProductEntranceDTO {
    private Double discount;
    private Integer quantity;
    private String mainCode;
    private Long invoiceId;
}
