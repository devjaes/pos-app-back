package dev.jeep.Lookpay.repositories.tables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.tables.*;

@Repository
public interface IvaTypeRepository extends JpaRepository<IvaTypeModel, String> {
    /*
     * @Query(value = "SELECT * FROM iva_types WHERE code_sri = :code_sri",
     * nativeQuery = true)
     * public IvaTypeModel getIvaByCode(@Param("code_sri") String codeSri);
     */
    @Query(value = "SELECT * FROM iva_types WHERE type = :type", nativeQuery = true)
    public IvaTypeModel getIvaByType(@Param("type") String type);
}
