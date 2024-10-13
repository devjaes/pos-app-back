package dev.jeep.Lookpay.dtos;

import java.sql.Date;
import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class InvoiceResponseDTO {
    private Long id;
    private String environmentType;
    private String emissionType;
    private String accessKey;
    private String receiptType;
    private Long customerId;
    private Date emissionDate;
    private String remissionGuide;
    private Double totalWithoutTax;
    private Double totalDiscount;
    private Double tip;
    private String paymentType;
    private Double total;
    private String currency;
    private Long boxId;
    private String boxKey;
    private String branchName;
    private List<SellingProductResponseDTO> sellingProducts;
}
