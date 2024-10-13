package dev.jeep.Lookpay.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface SellingProductRepository extends JpaRepository<SellingProductModel, Long> {
    @Query(value = "SELECT * FROM selling_product WHERE invoice_id = :invoice_id", nativeQuery = true)
    public List<SellingProductModel> getAllByInvoiceId(@Param("invoice_id") Long invoiceId);
}
