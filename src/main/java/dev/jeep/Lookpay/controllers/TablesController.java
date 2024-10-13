package dev.jeep.Lookpay.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/tables")
public class TablesController {

    @Autowired
    private TablesService tablesService;

    @GetMapping("/taxes")
    public ResponseEntity<?> getTaxTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getTaxTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/ivas")
    public ResponseEntity<?> getIvaTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getIvaTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/ices")
    public ResponseEntity<?> getIceTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getIceTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/irbps")
    public ResponseEntity<?> getIrbpsTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getIrbpTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/identifications")
    public ResponseEntity<?> getIdentificationTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getIdentificationTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/receipts")
    public ResponseEntity<?> getReceiptTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getReceiptTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/emissions")
    public ResponseEntity<?> getEmissionTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getEmissionTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/environments")
    public ResponseEntity<?> getEnvironmentTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getEnvironmentTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<?> getPaymentTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tablesService.getPaymentTypes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

}
