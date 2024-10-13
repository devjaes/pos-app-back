package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StoreEntranceDTO {
    private String storeName;
    private String tradeName;
    private String ruc;
    private String address;
    private String especialTaxPayer;
    private Boolean forcedAccounting;
}