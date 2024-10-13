package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SellingProductResponseDTO {
    private Long id;
    private Long invoiceId;
    private ProductResponseDTO product;
    private Double discount;
    private Double subtotal;
    private Integer iva;
    private Double ivaValue;
    private Integer ice;
    private Double iceValue;
    private Integer irbp;
    private Double irbpValue;
    private Integer quantity;
}
