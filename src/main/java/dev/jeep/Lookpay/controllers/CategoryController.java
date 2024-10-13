package dev.jeep.Lookpay.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CategoryEntranceDTO user) {
        try {
            CategoryResponseDTO categoryCreated = categoryService.register(user);
            if (categoryCreated != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(categoryCreated);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Category is already registered\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryEntranceDTO category) {
        try {
            CategoryResponseDTO categoryUpdated = categoryService.update(id, category);
            if (categoryUpdated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(categoryUpdated);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Category is already registered\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (categoryService.delete(id)) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Category deleted\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Category not found\"}");
        } catch (Exception e) {
            System.out.println(e.getMessage() + "++++++++++++++++++++++++");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }
}
