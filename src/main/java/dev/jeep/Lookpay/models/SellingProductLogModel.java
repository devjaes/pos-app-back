package dev.jeep.Lookpay.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "selling_product_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellingProductLogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "product_unit_price", nullable = false, length = 100)
    private Double productUnitPrice;

    @Column(name = "iva_applied", nullable = false, length = 100)
    private Integer ivaApplied;

    @Column(name = "iva_value", nullable = false)
    private Double ivaValue;

    @Column(name = "ice_applied", nullable = true, length = 100)
    private Integer iceApplied;

    @Column(name = "ice_value", nullable = true)
    private Double iceValue;

    @Column(name = "irbp_applied", nullable = true, length = 100)
    private Integer irbpApplied;

    @Column(name = "irbp_value", nullable = true)
    private Double irbpValue;

    @Column(name = "discount", nullable = true)
    private Double discount;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "invoice_access_key", nullable = false, length = 49)
    private String invoiceAccessKey;

}
