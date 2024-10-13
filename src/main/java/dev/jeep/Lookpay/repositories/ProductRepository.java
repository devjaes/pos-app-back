package dev.jeep.Lookpay.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    @Query(value = "SELECT * FROM products WHERE main_code = :main_code", nativeQuery = true)
    public ProductModel getProductByMainCode(@Param("main_code") String mainCode);

    @Query(value = "SELECT main_code FROM products WHERE id = :id", nativeQuery = true)
    public String getProductMainCodeById(@Param("id") Long id);

    @Query(value = "SELECT * FROM products WHERE category_id = :category_id", nativeQuery = true)
    public List<ProductModel> getProductsByCategory(@Param("category_id") Long categoryId);
}
