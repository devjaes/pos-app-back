package dev.jeep.Lookpay.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.models.BoxModel;
import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    @Autowired
    private BoxService boxService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(boxService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(boxService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<?> getAllByBranchId(@PathVariable Long id) {
        try {
            List<BoxModel> boxes = boxService.getAllByBranchId(id);

            List<BoxResponseDTO> boxResponseDTOs = new ArrayList<>();

            for (BoxModel boxModel : boxes) {
                boxResponseDTOs.add(boxService.convertModeltoResponse(boxModel));
            }

            return ResponseEntity.status(HttpStatus.OK).body(boxResponseDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody BoxEntranceDTO box) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(boxService.register(box));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (boxService.delete(id)) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Box deleted\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Box not found\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }
}
