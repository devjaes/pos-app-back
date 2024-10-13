package dev.jeep.Lookpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    @Query(value = "SELECT * FROM categories WHERE category = :category", nativeQuery = true)
    public CategoryModel getCategoryByType(@Param("category") String category);
}
