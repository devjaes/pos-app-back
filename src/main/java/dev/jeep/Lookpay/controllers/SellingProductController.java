package dev.jeep.Lookpay.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/selling-products")
public class SellingProductController {

    @Autowired
    private SellingProductService sellingProductService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(sellingProductService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(sellingProductService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error.Please try again later.\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody List<SellingProductEntranceDTO> sellingProducts) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(sellingProductService.register(sellingProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    // @GetMapping("/invoice/{id}")
    // public ResponseEntity<?> getAllByInvoiceId(@PathVariable Long id) {
    // try {
    // return
    // ResponseEntity.status(HttpStatus.OK).body(sellingProductService.getAllByInvoiceId(id));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body("{\"error\":\"Error. Please try again later.\"}");
    // }
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<?> update(@PathVariable Long id, @RequestBody
    // BranchEntranceDTO branch) {
    // try {
    // BranchResponseDTO branchUpdated = sellingProductService.update(id, branch);
    // if (branchUpdated != null) {
    // return ResponseEntity.status(HttpStatus.OK).body(branchUpdated);
    // }
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body("{\"error\":\"Branch is already registered\"}");
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error.Please
    // try again later.\"}");
    // }
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (sellingProductService.delete(id)) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Selling Product deleted\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Selling Product not found\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error.Please try again later.\"}");
        }
    }
}
