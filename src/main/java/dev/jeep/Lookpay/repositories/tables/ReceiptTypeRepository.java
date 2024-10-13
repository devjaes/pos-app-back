package dev.jeep.Lookpay.repositories.tables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.tables.*;

@Repository
public interface ReceiptTypeRepository extends JpaRepository<ReceiptTypeModel, String> {
    @Query(value = "SELECT * FROM receipt_types WHERE type = :type", nativeQuery = true)
    public ReceiptTypeModel getReceiptByType(@Param("type") String type);
}
