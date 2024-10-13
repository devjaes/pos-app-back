package dev.jeep.Lookpay.controllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.jeep.Lookpay.dtos.*;
import dev.jeep.Lookpay.services.*;

@RestController
@RequestMapping("/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(branchService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(branchService.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody BranchEntranceDTO branch) {
        try {
            BranchResponseDTO branchCreated = branchService.register(branch);
            if (branchCreated != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(branchCreated);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Branch is already registered\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BranchEntranceDTO branch) {
        try {
            BranchResponseDTO branchUpdated = branchService.update(id, branch);
            if (branchUpdated != null) {
                return ResponseEntity.status(HttpStatus.OK).body(branchUpdated);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Branch is already registered\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error.Please try again later.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (branchService.delete(id)) {
                return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Branch deleted\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Branch not found\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Please try again later.\"}");
        }
    }
}
