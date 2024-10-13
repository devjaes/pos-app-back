package dev.jeep.Lookpay.repositories.tables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.tables.*;

@Repository
public interface IrbpTypeRepository extends JpaRepository<IrbpTypeModel, String> {
    /*
     * @Query(value = "SELECT * FROM irbp_types WHERE code_sri = :code_sri",
     * nativeQuery = true)
     * public IrbpTypeModel getIrbpByCode(@Param("code_sri") String codeSri);
     */
    @Query(value = "SELECT * FROM irbp_types WHERE type = :type", nativeQuery = true)
    public IrbpTypeModel getIrbpByType(@Param("type") String type);
}
