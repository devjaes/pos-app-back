package dev.jeep.Lookpay.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "selling_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellingProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private InvoiceModel invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;

    @Column(name = "discount", nullable = true)
    private Double discount;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @Column(name = "iva", nullable = false)
    private Integer iva;

    @Column(name = "iva_value", nullable = false)
    private Double ivaValue = 0.0;

    @Column(name = "ice", nullable = true)
    private Integer ice;

    @Column(name = "ice_value", nullable = true)
    private Double iceValue = 0.0;

    @Column(name = "irbp", nullable = true)
    private Integer irbp;

    @Column(name = "irbp_value", nullable = true)
    private Double irbpValue = 0.0;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
