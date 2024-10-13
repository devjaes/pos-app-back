package dev.jeep.Lookpay.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody InvoiceEntranceDTO invoice) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.register(invoice));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateValues(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceService.updateValues(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/generate/{id}")
    public ResponseEntity<?> invoice(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invoiceService.invoice(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> delete(@PathVariable Long id) {
    // try {
    // if (invoiceService.delete(id)) {
    // return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Customer
    // deleted\"}");
    // }
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Customer not
    // found\"}");
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error.
    // Please try again later.\"}");
    // }
    // }
}
