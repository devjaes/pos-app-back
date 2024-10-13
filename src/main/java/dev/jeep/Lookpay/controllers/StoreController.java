package dev.jeep.Lookpay.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<?> getStore() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeService.getStore());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> update(@RequestPart("store") String store,
            @RequestPart(value = "electronicSignature", required = false) MultipartFile electronicSignature) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeService.update(store, electronicSignature));
        } catch (Exception e) {
            System.out
                    .println(e.getMessage() + " " + e.getStackTrace() + " " + e.getCause() + "=======================");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @DeleteMapping("/electronicSignature")
    public ResponseEntity<?> deleteElectronicSignature() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(storeService.deleteElectronicSignature());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

}
