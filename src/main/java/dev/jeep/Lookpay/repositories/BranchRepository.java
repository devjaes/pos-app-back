package dev.jeep.Lookpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface BranchRepository extends JpaRepository<BranchModel, Long> {
    @Query(value = "SELECT MAX(key) FROM branches", nativeQuery = true)
    public String getLastKey();

    @Query(value = "SELECT * FROM branches WHERE name = :name", nativeQuery = true)
    public BranchModel getBranchByName(@Param("name") String name);
}
