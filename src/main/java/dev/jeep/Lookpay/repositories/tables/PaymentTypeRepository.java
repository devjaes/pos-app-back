package dev.jeep.Lookpay.repositories.tables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.tables.*;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentTypeModel, String> {
    @Query(value = "SELECT * FROM payment_types WHERE type = :type", nativeQuery = true)
    public PaymentTypeModel getPaymentByType(@Param("type") String type);
}
