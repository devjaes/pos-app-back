package dev.jeep.Lookpay.repositories.tables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.tables.*;

@Repository
public interface EmissionTypeRepository extends JpaRepository<EmissionTypeModel, String> {
    @Query(value = "SELECT * FROM emission_types WHERE type = :type", nativeQuery = true)
    public EmissionTypeModel getEmissionByType(@Param("type") String type);
}
