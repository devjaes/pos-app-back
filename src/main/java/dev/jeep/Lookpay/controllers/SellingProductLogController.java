package dev.jeep.Lookpay.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/selling-products-log")
public class SellingProductLogController {

    @Autowired
    private SellingProductLogService sellingProductLogService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(sellingProductLogService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

}
