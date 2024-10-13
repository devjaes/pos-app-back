package dev.jeep.Lookpay.models;

import java.sql.Date;
import java.util.List;

import dev.jeep.Lookpay.models.tables.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "environment_type_code", nullable = false)
    private EnvironmentTypeModel environmentType;

    @ManyToOne
    @JoinColumn(name = "emission_type_code", nullable = false)
    private EmissionTypeModel emissionType;

    @Column(name = "access_key", nullable = false, length = 49)
    private String accessKey;

    @ManyToOne
    @JoinColumn(name = "receipt_type_code", nullable = false)
    private ReceiptTypeModel receiptType;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @Column(name = "emission_date", nullable = false)
    private Date emissionDate;

    @Column(name = "remission_guide", nullable = false, length = 15)
    private String remissionGuide;

    @Column(name = "total_without_tax", nullable = true)
    private Double totalWithoutTax;

    @Column(name = "total_discount", nullable = true)
    private Double totalDiscount;

    @Column(name = "tip", nullable = true)
    private Double tip;

    @ManyToOne
    @JoinColumn(name = "payment_type_code", nullable = false)
    private PaymentTypeModel paymentType;

    @Column(name = "total", nullable = true)
    private Double total;

    @Column(name = "currency", nullable = false, length = 20)
    private String currency;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SellingProductModel> sellingProducts;

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private BoxModel box;
}
