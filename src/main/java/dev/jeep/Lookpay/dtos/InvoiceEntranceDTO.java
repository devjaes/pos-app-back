package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class InvoiceEntranceDTO {
    private String environmentType;
    private String emissionType;
    private String receiptType;
    private String customerIdentification;
    private String paymentType;
    private Long boxId;
}
