package dev.jeep.Lookpay.models;

import java.util.List;

import dev.jeep.Lookpay.models.tables.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "main_code", nullable = false, length = 25)
    private String mainCode;

    @Column(name = "aux_code", nullable = false, length = 25)
    private String auxCode;

    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "iva_variable", nullable = true)
    private String ivaVariable;

    @Column(name = "image_key", nullable = true)
    private String imageKey;

    @Column(name = "image_url", nullable = true)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name = "iva_type_code", nullable = true)
    private IvaTypeModel ivaType;

    @ManyToOne
    @JoinColumn(name = "ice_type_code", nullable = true)
    private IceTypeModel iceType;

    @ManyToOne
    @JoinColumn(name = "irbp_type_code", nullable = true)
    private IrbpTypeModel irbpType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SellingProductModel> sellingProducts;
}
