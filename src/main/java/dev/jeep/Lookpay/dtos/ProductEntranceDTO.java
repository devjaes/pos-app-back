package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductEntranceDTO {
    private String name;
    private String mainCode;
    private String auxCode;
    private String description;
    private Integer stock;
    private Double unitPrice;
    private String ivaVariable;
    private String category;
    private String ivaType;
    private String iceType;
    private String irbpType;
}
