package dev.jeep.Lookpay.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> register(@RequestPart("product") String product,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ProductResponseDTO productCreated = productService.register(product, image);
            if (productCreated != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Product is already registered\"}");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> update(@PathVariable Long id, @RequestPart("product") String product,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ProductResponseDTO productUpdated = productService.update(id, product, image);
            if (productUpdated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(productUpdated);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Product is already registered\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PutMapping("/update-by-category")
    public ResponseEntity<?> updateIvaByCategory(@RequestPart("category") String category,
            @RequestPart("ivaVariable") String ivaVariable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.updateIvaByCategory(category, ivaVariable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (productService.delete(id)) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Product deleted\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Product not found\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }
}
