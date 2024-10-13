package dev.jeep.Lookpay.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String mainCode;
    private String auxCode;
    private String description;
    private String category;
    private Integer stock;
    private Double unitPrice;
    private String ivaVariable;
    private String imageUrl;
    private String ivaType;
    private String iceType;
    private String irbpType;
}
